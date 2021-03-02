package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;

import java.util.Collection;
import java.util.List;

public interface OwnershipService<ENTITY extends OwnershipEntity, ID> {

//    List<ENTITY> listByCurrentUser();

    void checkOwnership(ENTITY entity);

    void checkOwnership(Iterable<ID> ids);

    void checkOwnership(Collection<ENTITY> entities);

    void checkOwnership(ENTITY entity, String message);

    void checkOwnership(Iterable<ID> ids, String message);

    void checkOwnership(Collection<ENTITY> entities, String message);


}
