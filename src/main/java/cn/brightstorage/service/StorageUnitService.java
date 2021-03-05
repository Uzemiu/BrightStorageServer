package cn.brightstorage.service;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.query.StorageUnitQuery;
import cn.brightstorage.service.base.CrudService;
import cn.brightstorage.service.base.OwnershipService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface StorageUnitService extends CrudService<StorageUnit, Long>, OwnershipService<StorageUnit, Long> {

    StorageUnit create(StorageUnitDTO storageUnitDTO);

    void update(StorageUnitDTO storageUnitDTO);

    List<StorageUnitDTO> listByParentId(Long parentId);

    List<StorageUnitDTO> listByParentIdAndOwner(Long parentId, User owner);

    List<StorageUnitDTO> merge(List<StorageUnitDTO> storageUnitDTOs);

    Object query(StorageUnitQuery query, Pageable pageable);
}
