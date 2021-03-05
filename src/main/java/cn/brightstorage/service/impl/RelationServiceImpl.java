package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.RelationRepository;
import cn.brightstorage.service.RelationService;
import cn.brightstorage.service.UserService;
import cn.brightstorage.service.base.AbstractOwnershipService;
import cn.brightstorage.service.mapper.RelationMapper;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.RedisUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cn.brightstorage.constant.RelationConstant.INVITE_CODE_EXPIRE_TIME;
import static cn.brightstorage.constant.RelationConstant.INVITE_CODE_PREFIX;

@Service("relationService")
public class RelationServiceImpl extends AbstractOwnershipService<Relation, Long> implements RelationService {

    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;
    private final UserService userService;
    private final RedisUtil redisUtil;

    protected RelationServiceImpl(RelationRepository repository,
                                  RelationMapper relationMapper,
                                  UserService userService,
                                  RedisUtil redisUtil) {
        super(repository);
        this.relationRepository = repository;
        this.relationMapper = relationMapper;
        this.userService = userService;
        this.redisUtil = redisUtil;
    }

    @Override
    public List<RelationDTO> listByUserId(String userId) {
        return relationMapper.toDto(userService.getNotNullById(userId).getRelations());
    }

    @Override
    public void update(RelationDTO relationDTO) {
        Relation relation = getNotNullById(relationDTO.getId());

        // 检查更改信息用户是否属于这个关系
        checkMembership(relation);

        // 检查是否变更关系所有者
//        boolean transferOwnership = !relation.getOwner().getId().equals(relationDTO.getOwner().getId());
//        if(transferOwnership){
//            checkOwnership(relation,"只有所有者能够转让所有者权限");
//            relation.setOwner(userService.getNotNullById(relationDTO.getOwner().getId()));
//        }
        relation.setAvatar(relationDTO.getAvatar());
        relation.setName(relationDTO.getName());

        relationRepository.save(relation);
    }

    @Override
    public Relation create(RelationDTO relationDTO) {
        User current = SecurityUtil.getCurrentUser();

        Relation relation = relationMapper.toEntity(relationDTO);
        relation.setOwner(current);

        Set<User> members = new HashSet<>();
        members.add(current);
        relation.setMembers(members);

        return relationRepository.save(relation);
    }

    @Override
    public String newInviteCode(Long id, boolean checkCache) {
        checkMembership(id);

        String uuid = (String) redisUtil.get(INVITE_CODE_PREFIX + id);
        if(!checkCache && uuid != null){
            // 已有未过期的邀请码且强制生成新邀请码则删除原缓存
            redisUtil.del(INVITE_CODE_PREFIX + uuid);
            uuid = null;
        }
        if(uuid == null){
            String key = System.currentTimeMillis() + id.toString();
            uuid = UUID.nameUUIDFromBytes(key.getBytes()).toString();
            // 缓存双向映射
            redisUtil.set(INVITE_CODE_PREFIX + uuid, id, INVITE_CODE_EXPIRE_TIME, TimeUnit.HOURS);
            redisUtil.set(INVITE_CODE_PREFIX + id, uuid, INVITE_CODE_EXPIRE_TIME, TimeUnit.HOURS);
        }
        return uuid;
    }

    @Override
    public void checkMembership(Long id) {
        checkMembership(getNotNullById(id));
    }

    @Override
    public void checkMembership(Relation relation){
        User current = SecurityUtil.getCurrentUser();
        boolean hasAccess = relation.getMembers().stream()
                .anyMatch(user -> user.equals(current));
        AssertUtil.isAuthorized(hasAccess);
    }
}
