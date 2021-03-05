package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.query.StorageUnitQuery;
import cn.brightstorage.repository.StorageUnitRepository;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.base.AbstractOwnershipService;
import cn.brightstorage.service.mapper.StorageUnitMapper;
import cn.brightstorage.utils.PageUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        storageUnit.setOwner(SecurityUtil.getCurrentUser());
        storageUnit.setId(null);
        // 未检查外键
        return storageUnitRepository.save(storageUnit);
    }

    @Override
    public void update(StorageUnitDTO storageUnitDTO) {
        checkOwnership(storageUnitDTO.getId()); // 忽略找不到id对应物品情况
        checkCategoriesOwnership(storageUnitDTO);

        StorageUnit newUnit = storageUnitMapper.toEntity(storageUnitDTO);
        // 未检查外键
        storageUnitRepository.save(newUnit);
    }

    @Override
    public Object query(StorageUnitQuery query, Pageable pageable) {
        Page<StorageUnit> storageUnits = storageUnitRepository.findAll(query.toSpecification(), pageable);
        Page<StorageUnitDTO> dtos = storageUnits.map(storageUnitMapper::toDto);
        return PageUtil.toPage(dtos);
    }

    @Override
    public List<StorageUnitDTO> merge(List<StorageUnitDTO> storageUnitDTOs) {
        // if dto.id.isPresent -> check ownership ignore null
        return null;
    }

    @Override
    public List<StorageUnitDTO> listByParentIdAndOwner(Long parentId, User owner) {
        List<StorageUnitDTO> topDTOs = storageUnitMapper.toDto(storageUnitRepository.getByParentIdAndOwner(parentId, owner));
        fillChildren(topDTOs);
        return topDTOs;
    }

    @Override
    public List<StorageUnitDTO> listByParentId(Long parentId) {
        List<StorageUnitDTO> topDTOs = storageUnitMapper.toDto(storageUnitRepository.getByParentId(parentId));
        fillChildren(topDTOs);
        return topDTOs;
    }

    private void fillChildren(List<StorageUnitDTO> topDTOs){
        // 只显示一层子物品
        topDTOs.forEach(storageUnitDTO -> {
            List<StorageUnitDTO> children = storageUnitMapper.toDto(
                    storageUnitRepository.getByParentId(storageUnitDTO.getId()));
            storageUnitDTO.setChildren(children);
        });
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
        categoryService.checkOwnership(ids);
    }

}
