package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.RelationRepository;
import cn.brightstorage.service.RelationService;
import cn.brightstorage.service.base.AbstractCrudService;
import cn.brightstorage.service.mapper.RelationMapper;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Service("relationService")
public class RelationServiceImpl extends AbstractCrudService<Relation, Long> implements RelationService {

    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;

    protected RelationServiceImpl(RelationRepository repository,
                                  RelationMapper relationMapper) {
        super(repository);
        this.relationRepository = repository;
        this.relationMapper = relationMapper;
    }

    @Override
    public void update(RelationDTO relationDTO) {
        Relation relation = getNotNullById(relationDTO.getId());
        // 检查更改信息用户是否属于这个关系
        boolean hasAccess = relation.getMembers().stream()
                .anyMatch(user -> user.getId().equals(SecurityUtil.getCurrentUser().getId()));
        Assert.isTrue(hasAccess, "对不起，你没有权限进行此操作");

        relation.setAvatar(relationDTO.getAvatar());
        relation.setName(relationDTO.getName());
        relationRepository.save(relation);
    }

    @Override
    public Relation create(RelationDTO relationDTO) {
        Relation relation = relationMapper.toEntity(relationDTO);

        Set<User> members = new HashSet<>();
        members.add(SecurityUtil.getCurrentUser());
        relation.setMembers(members);

        return relationRepository.save(relation);
    }
}
