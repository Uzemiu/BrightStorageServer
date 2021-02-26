package cn.brightstorage.service.base;

import cn.brightstorage.model.entity.OwnershipEntity;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.BaseRepository;
import cn.brightstorage.repository.base.OwnershipRepository;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.util.List;

public abstract class AbstractOwnershipService<ENTITY extends OwnershipEntity, ID> extends AbstractCrudService<ENTITY, ID>{

    private OwnershipRepository<ENTITY, ID> ownershipRepository;

    protected AbstractOwnershipService(OwnershipRepository<ENTITY, ID> repository) {
        super(repository);
        this.ownershipRepository = repository;
    }

    @Override
    public ENTITY deleteById(ID id) {
        Assert.notNull(id, "Id must not be nul");
        ENTITY entity = getNotNullById(id);

        AssertUtil.isAuthorized(entity.getOwner().equals(SecurityUtil.getCurrentUser()));

        ownershipRepository.delete(entity);
        return entity;
    }

}
