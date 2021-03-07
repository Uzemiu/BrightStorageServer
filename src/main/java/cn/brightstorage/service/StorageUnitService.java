package cn.brightstorage.service;

import cn.brightstorage.model.dto.PageDTO;
import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.query.StorageUnitQuery;
import cn.brightstorage.service.base.CrudService;
import cn.brightstorage.service.base.OwnershipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StorageUnitService extends CrudService<StorageUnit, Long>, OwnershipService<StorageUnit, Long> {

    StorageUnit create(StorageUnitDTO storageUnitDTO);

    void update(StorageUnitDTO storageUnitDTO);

    StorageUnitDTO findById(Long id);

    List<StorageUnitDTO> listByParentId(Long parentId);

    List<StorageUnitDTO> listByParentIdAndOwner(Long parentId, User owner);

    List<StorageUnitDTO> sync(List<StorageUnitDTO> storageUnitDTOs);

    PageDTO<StorageUnitDTO> query(StorageUnitQuery query, Pageable pageable);

    /**
     * 检查分类是否属于用户创建
     * @param storageUnit /
     */
    void checkCategoriesOwnership(StorageUnitDTO storageUnit);

    void checkOwnership(StorageUnitDTO storageUnit);
}
