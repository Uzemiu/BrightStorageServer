package cn.brightstorage.service.base;

import cn.brightstorage.exception.BadRequestException;
import cn.brightstorage.exception.ResourceNotFoundException;
import cn.brightstorage.repository.base.BaseRepository;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<ENTITY, ID> implements CrudService<ENTITY, ID>{

    private final String entityName;

    private final BaseRepository<ENTITY, ID> repository;

    @SuppressWarnings("unchecked")
    protected AbstractCrudService(BaseRepository<ENTITY, ID> repository) {
        this.repository = repository;

        Class<ENTITY> actualClass = (Class<ENTITY>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityName =  actualClass.getSimpleName();
    }

    @Override
    public ENTITY create(ENTITY entity) {
        Assert.notNull(entity, entityName + " must not be null");
        return repository.save(entity);
    }

    @Override
    public List<ENTITY> listByIdsIn(Collection<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");
        return repository.findAllById(ids);
    }

    @Override
    public List<ENTITY> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ENTITY> getById(ID id) {
        Assert.notNull(id, "Id must be null");
        return repository.findById(id);
    }

    @Override
    public ENTITY getNotNullById(ID id) {
        Assert.notNull(id, "Id must be null");
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(entityName + "(id: " + id + ") could not be found"));
    }
    @Override
    public ENTITY update(ENTITY entity) {
        Assert.notNull(entity, entityName + " must not be null");
        return repository.save(entity);
    }

    @Override
    public void delete(ENTITY entity) {
        if(entity != null){
            repository.delete(entity);
        }
    }

    @Override
    public long deleteByIdIn(Collection<ID> ids) {
        Assert.notNull(ids, "Ids must not be null");
        return repository.deleteByIdIn(ids);
    }

    @Override
    public ENTITY deleteById(ID id) {
        Assert.notNull(id, "Id must not be nul");
        ENTITY entity = getNotNullById(id);
        repository.delete(entity);
        return entity;
    }

    @Override
    public long count() {
        return repository.count();
    }
}
