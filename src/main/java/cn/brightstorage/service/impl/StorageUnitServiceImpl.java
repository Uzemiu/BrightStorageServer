package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.StorageUnitRepository;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.base.AbstractOwnershipService;
import cn.brightstorage.service.mapper.StorageUnitMapper;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("storageUnitService")
public class StorageUnitServiceImpl extends AbstractOwnershipService<StorageUnit, Long> implements StorageUnitService {

    private final StorageUnitRepository storageUnitRepository;
    private final StorageUnitMapper storageUnitMapper;
    private final CategoryService categoryService;

    protected StorageUnitServiceImpl(StorageUnitRepository repository,
                                     StorageUnitMapper storageUnitMapper,
                                     CategoryService categoryService) {
        super(repository);
        this.storageUnitRepository = repository;
        this.storageUnitMapper = storageUnitMapper;
        this.categoryService = categoryService;
    }

    @Override
    public StorageUnit create(StorageUnitDTO storageUnitDTO) {
        checkCategoriesOwnership(storageUnitDTO);

        StorageUnit storageUnit = storageUnitMapper.toEntity(storageUnitDTO);
        return storageUnitRepository.save(storageUnit);
    }

    @Override
    public List<StorageUnit> listByParentIdAndOwner(Long parentId, User owner) {
        return storageUnitRepository.getByParentIdAndOwner(parentId, owner);
    }

    @Override
    public List<StorageUnit> listByParentId(Long parentId) {
        return storageUnitRepository.getByParentId(parentId);
    }

    @Override
    public void update(StorageUnitDTO storageUnitDTO) {
        StorageUnit oldUnit = getNotNullById(storageUnitDTO.getId());

        checkUnitOwnership(oldUnit);
        checkCategoriesOwnership(storageUnitDTO);

        StorageUnit newUnit = storageUnitMapper.toEntity(storageUnitDTO);
        storageUnitRepository.save(newUnit);
    }

    /**
     * 检查分类是否属于用户创建
     * @param storageUnit /
     */
    private void checkCategoriesOwnership(StorageUnitDTO storageUnit){
        Set<Long> ids = new HashSet<>();
        storageUnit.getCategories().forEach(categoryDTO -> {
            if(categoryDTO.getId() != null){
                ids.add(categoryDTO.getId());
            }
        });
        User currentUser = SecurityUtil.getCurrentUser();
        boolean hasAccess = categoryService.listByIdsIn(ids)
                .stream()
                .allMatch(category -> category.getOwner().equals(currentUser));
        AssertUtil.isAuthorized(hasAccess);
    }

    private void checkUnitOwnership(StorageUnit storageUnit){
        AssertUtil.isAuthorized(storageUnit.getOwner().equals(SecurityUtil.getCurrentUser()));
    }

}
