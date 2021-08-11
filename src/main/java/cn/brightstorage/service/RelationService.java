package cn.brightstorage.service;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.service.base.CrudService;
import cn.brightstorage.service.base.OwnershipService;

import java.util.List;
import java.util.Set;

public interface RelationService extends CrudService<Relation, Long>, OwnershipService<Relation, Long> {

    Relation create(RelationDTO relationDTO);

    void update(RelationDTO relationDTO);

    List<RelationDTO> listByUserId(String userId);

    /**
     * 检查当前用户是否在{@code relation}中
     * @param relation /
     */
    void checkMembership(Relation relation);

    /**
     * 检查当前用户是否在{@code id}对应的relation中
     * @param id /
     */
    void checkMembership(Long id);

    /**
     * 生成并缓存id对应relation的邀请码对应的uuid
     * @param id /
     * @param checkCache 是否检查缓存已存在未过期二维码
     * @return uuid
     */
    String newInviteCode(Long id, boolean checkCache);
}
