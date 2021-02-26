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
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("relationService")
public class RelationServiceImpl extends AbstractOwnershipService<Relation, Long> implements RelationService {

    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;
    private final UserService userService;

    protected RelationServiceImpl(RelationRepository repository,
                                  RelationMapper relationMapper,
                                  UserService userService) {
        super(repository);
        this.relationRepository = repository;
        this.relationMapper = relationMapper;
        this.userService = userService;
    }

    @Override
    public List<RelationDTO> listByUserId(String userId) {
        return relationMapper.toDto(userService.getNotNullById(userId).getRelations());
    }

    @Override
    public void update(RelationDTO relationDTO) {
        Relation relation = getNotNullById(relationDTO.getId());
        String currentUserId = SecurityUtil.getCurrentUser().getId();

        // 检查更改信息用户是否属于这个关系
        boolean hasAccess = relation.getMembers().stream()
                .anyMatch(user -> user.getId().equals(currentUserId));
        AssertUtil.isAuthorized(hasAccess);

        // 检查是否变更关系所有者
        boolean transferOwnership = !relation.getOwner().getId().equals(relationDTO.getOwner().getId());
        if(transferOwnership){
            AssertUtil.isAuthorized(currentUserId.equals(relation.getOwner().getId()),
                    "只有所有者能够转让所有者权限");
            relation.setOwner(userService.getNotNullById(relationDTO.getOwner().getId()));
        }
        relation.setAvatar(relationDTO.getAvatar());
        relation.setName(relationDTO.getName());

        relationRepository.save(relation);
    }

    @Override
    @Transactional
    public Relation create(RelationDTO relationDTO) {
        Relation relation = relationMapper.toEntity(relationDTO);
        relation.setOwner(SecurityUtil.getCurrentUser());
        relationRepository.save(relation);

        User currentUser = SecurityUtil.getCurrentUser();
        currentUser.getRelations().add(relation);
        userService.update(currentUser);

        return relation;
    }
}
