package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.OwnershipRepository;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractOwnershipService<ENTITY extends OwnershipEntity, ID>
        extends AbstractCrudService<ENTITY, ID>
        implements OwnershipService<ENTITY, ID>{

    private final OwnershipRepository<ENTITY, ID> ownershipRepository;

    protected AbstractOwnershipService(OwnershipRepository<ENTITY, ID> repository) {
        super(repository);
        this.ownershipRepository = repository;
    }


//    @Override
//    public ENTITY deleteById(ID id) {
//        Assert.notNull(id, "Id must not be nul");
//        ENTITY entity = getNotNullById(id);
//
//        checkOwnership(entity);
//
//        ownershipRepository.delete(entity);
//        return entity;
//    }

    @Override
    public ENTITY checkOwnership(ID id) {
        return checkOwnership(id, null);
    }

    @Override
    public void checkOwnership(ENTITY entity) {
        checkOwnership(entity, null);
    }

    @Override
    public List<ENTITY> checkOwnership(Iterable<ID> ids) {
        return checkOwnership(ids, null);
    }

    @Override
    public void checkOwnership(Collection<ENTITY> entities) {
        checkOwnership(entities, null);
    }

    @Override
    public ENTITY checkOwnership(ID id, String message) {
        ENTITY entity = null;
        if(id != null){
            Optional<ENTITY> e = getById(id);
            if(e.isPresent()){
                checkOwnership(e.get(), message);
                entity = e.get();
            }
        }
        return entity;
    }

    @Override
    public void checkOwnership(ENTITY entity, String message){
        if(entity != null){
            AssertUtil.isAuthorized(entity.getOwner().equals(SecurityUtil.getCurrentUser()), message);
        }
    }

    @Override
    public List<ENTITY> checkOwnership(Iterable<ID> ids, String message){
        List<ENTITY> entities = ownershipRepository.findAllById(ids);
        checkOwnership(entities, message);
        return entities;
    }

    @Override
    public void checkOwnership(Collection<ENTITY> entities, String message){
        User currentUser = SecurityUtil.getCurrentUser();
        boolean hasAccess = entities.stream()
                .allMatch(category -> category.getOwner().equals(currentUser));
        AssertUtil.isAuthorized(hasAccess, message);
    }

}
