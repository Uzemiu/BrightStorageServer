package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.OwnershipRepository;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.util.Collection;

public abstract class AbstractOwnershipService<ENTITY extends OwnershipEntity, ID>
        extends AbstractCrudService<ENTITY, ID>
        implements OwnershipService<ENTITY, ID>{

    private final OwnershipRepository<ENTITY, ID> ownershipRepository;

    protected AbstractOwnershipService(OwnershipRepository<ENTITY, ID> repository) {
        super(repository);
        this.ownershipRepository = repository;
    }


    @Override
    public ENTITY deleteById(ID id) {
        Assert.notNull(id, "Id must not be nul");
        ENTITY entity = getNotNullById(id);

        checkOwnership(entity);

        ownershipRepository.delete(entity);
        return entity;
    }

    @Override
    public void checkOwnership(ID id) {
        checkOwnership(id, null);
    }

    @Override
    public void checkOwnership(ENTITY entity) {
        checkOwnership(entity, null);
    }

    @Override
    public void checkOwnership(Iterable<ID> ids) {
        checkOwnership(ids, null);
    }

    @Override
    public void checkOwnership(Collection<ENTITY> entities) {
        checkOwnership(entities, null);
    }

    @Override
    public void checkOwnership(ID id, String message) {
        if(id != null){
            getById(id).ifPresent(e -> checkOwnership(e, message));
        }
    }

    @Override
    public void checkOwnership(ENTITY entity, String message){
        if(entity != null){
            AssertUtil.isAuthorized(entity.getOwner().equals(SecurityUtil.getCurrentUser()), message);
        }
    }

    @Override
    public void checkOwnership(Iterable<ID> ids, String message){
        checkOwnership(ownershipRepository.findAllById(ids), message);
    }

    @Override
    public void checkOwnership(Collection<ENTITY> entities, String message){
        User currentUser = SecurityUtil.getCurrentUser();
        boolean hasAccess = entities.stream()
                .allMatch(category -> category.getOwner().equals(currentUser));
        AssertUtil.isAuthorized(hasAccess, message);
    }

}
