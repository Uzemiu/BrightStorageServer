package cn.brightstorage.repository;

import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.BaseRepository;
import cn.brightstorage.repository.base.OwnershipRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StorageUnitRepository
        extends OwnershipRepository<StorageUnit, Long>, JpaSpecificationExecutor<StorageUnit> {

    List<StorageUnit> getByParentId(Long parentId);

    List<StorageUnit> getByParentIdAndOwner(Long parentId, User owner);
}
