package cn.brightstorage.repository;

import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.OwnershipRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StorageUnitRepository
        extends OwnershipRepository<StorageUnit, Long>, JpaSpecificationExecutor<StorageUnit> {

    List<StorageUnit> getByParentId(Long parentId);

    List<StorageUnit> getByParentIdAndOwner(Long parentId, User owner);

    @Modifying
    @Transactional
    @Query("update StorageUnit u set u.childCount = :count where u.id = :id")
    void setChildCount(@Param("id") Long id, @Param("count") Long count);

    @Modifying
    @Transactional
    @Query("update StorageUnit u set u.childCount = u.childCount + :count where u.id = :id")
    void increaseChildCount(@Param("id") Long id, @Param("count") Long count);
}
