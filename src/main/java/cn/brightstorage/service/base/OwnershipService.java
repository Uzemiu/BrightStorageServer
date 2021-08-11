package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;

import java.util.Collection;
import java.util.List;

public interface OwnershipService<ENTITY extends OwnershipEntity, ID> {

//    List<ENTITY> listByCurrentUser();
    ENTITY checkOwnership(ID id);

    void checkOwnership(ENTITY entity);

    List<ENTITY> checkOwnership(Iterable<ID> ids);

    void checkOwnership(Collection<ENTITY> entities);

    ENTITY checkOwnership(ID id, String message);

    void checkOwnership(ENTITY entity, String message);

    List<ENTITY> checkOwnership(Iterable<ID> ids, String message);

    void checkOwnership(Collection<ENTITY> entities, String message);


}
