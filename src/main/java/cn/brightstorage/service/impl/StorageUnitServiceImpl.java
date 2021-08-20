package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.PageDTO;
import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.query.StorageUnitQuery;
import cn.brightstorage.repository.StorageUnitRepository;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.service.RelationService;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.base.AbstractOwnershipService;
import cn.brightstorage.service.mapper.StorageUnitMapper;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("storageUnitService")
public class StorageUnitServiceImpl extends AbstractOwnershipService<StorageUnit, Long> implements StorageUnitService {

    private final StorageUnitRepository storageUnitRepository;
    private final RelationService relationService;
    private final StorageUnitMapper storageUnitMapper;
    private final CategoryService categoryService;

    protected StorageUnitServiceImpl(StorageUnitRepository repository,
                                     RelationService relationService,
                                     StorageUnitMapper storageUnitMapper,
                                     CategoryService categoryService) {
        super(repository);
        this.storageUnitRepository = repository;
        this.relationService = relationService;
        this.storageUnitMapper = storageUnitMapper;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public StorageUnit create(StorageUnitDTO storageUnitDTO) {
        StorageUnit storageUnit = storageUnitMapper.toEntity(storageUnitDTO);
        storageUnit.setOwner(SecurityUtil.getCurrentUser());
        storageUnit.setId(null);
        // 未检查category外键
        StorageUnit saved = storageUnitRepository.save(storageUnit);
        return saved;
    }

    @Override
    @Transactional
    public void update(StorageUnitDTO storageUnitDTO) {
        StorageUnit old = getNotNullById(storageUnitDTO.getId());
        StorageUnit newUnit = storageUnitMapper.toEntity(storageUnitDTO);
        // 未检查category外键
        storageUnitRepository.save(newUnit);
    }

    @Override
    public PageDTO<StorageUnitDTO> query(StorageUnitQuery query, Pageable pageable) {
        Page<StorageUnit> storageUnits = storageUnitRepository.findAll(query.toSpecification(), pageable);
        Page<StorageUnitDTO> dtos = storageUnits.map(storageUnitMapper::toDto);
        return new PageDTO<>(dtos);
    }

    @Override
    public StorageUnitDTO findById(Long id) {
        StorageUnitDTO dto = storageUnitMapper.toDto(getNotNullById(id));
        fillChildren(dto);
        return dto;
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

    @Override
    public void checkOwnership(StorageUnitDTO storageUnit) {
        if(storageUnit != null){
            AssertUtil.isAuthorized(storageUnit.getOwner().getId().equals(SecurityUtil.getCurrentUser().getId()));
        }
    }

    @Override
    public void checkCategoriesOwnership(StorageUnitDTO storageUnit){
        Set<Long> ids = new HashSet<>();
        storageUnit.getCategories().forEach(categoryDTO -> {
            if(categoryDTO.getId() != null){
                ids.add(categoryDTO.getId());
            }
        });
        categoryService.checkOwnership(ids);
    }

    @Override
    public List<StorageUnitDTO> listByRelationId(Long id) {
        Relation relation = relationService.getNotNullById(id);
        relationService.checkMembership(relation);
        return storageUnitMapper.toDto(storageUnitRepository.getBySharedRelation(relation));
    }

    @Override
    public void shareStorageUnit(Long relationId, Long storageUnitId) {
        Assert.notNull(relationId, "Relation Id must not be null");
        Assert.notNull(storageUnitId, "StorageUnit Id must not be null");

        StorageUnit storageUnit = getNotNullById(storageUnitId);
        AssertUtil.isAuthorized(storageUnit.getOwner().equals(SecurityUtil.getCurrentUser()));

        Relation relation = relationService.getNotNullById(relationId);
        storageUnit.setSharedRelation(relation);
        storageUnitRepository.save(storageUnit);
    }

    @Override
    @Transactional
    public void delete(StorageUnit storageUnit) {
        List<StorageUnit> children = storageUnitRepository.getByParentId(storageUnit.getId());
        if(children.size() > 0){
            children.forEach(this::delete);
        }
        storageUnitRepository.delete(storageUnit);
    }

    @Override
    public StorageUnit deleteById(Long aLong) {
        StorageUnit storageUnit = getNotNullById(aLong);
        delete(storageUnit);
        return storageUnit;
    }

    @Override
    public long deleteByIdIn(Collection<Long> longs) {
        List<StorageUnit> storageUnits = storageUnitRepository.findAllById(longs);
        storageUnits.forEach(this::delete);
        return longs.size();
    }

    private void fillChildren(List<StorageUnitDTO> topDTOs){
        // 只显示一层子物品
        topDTOs.forEach(this::fillChildren);
    }

    private void fillChildren(StorageUnitDTO storageUnitDTO){
        List<StorageUnitDTO> children = storageUnitMapper.toDto(
                storageUnitRepository.getByParentId(storageUnitDTO.getId()));
        storageUnitDTO.setChildren(children);
    }
}
