package cn.brightstorage.service;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.service.base.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StorageUnitService extends CrudService<StorageUnit, Long> {

    StorageUnit create(StorageUnitDTO storageUnitDTO);

    void update(StorageUnitDTO storageUnitDTO);

    List<StorageUnit> listByParentId(Long parentId);

    List<StorageUnit> listByParentIdAndOwner(Long parentId, User owner);

}
