package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;

import java.util.List;

public interface OwnershipService<ENTITY extends OwnershipEntity, ID> {

    public List<ENTITY> listByCurrentUser();
}
