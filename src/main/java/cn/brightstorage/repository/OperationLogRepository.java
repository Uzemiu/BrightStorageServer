package cn.brightstorage.repository;

import cn.brightstorage.model.entity.OperationLog;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationLogRepository extends BaseRepository<OperationLog, Long> {

    List<OperationLog> getByOwnerAndVersionGreaterThan(User owner, Long version);

    @Query("select max(o.version) from OperationLog o where o.owner = :owner")
    Long getLatestVersionByOwner(User owner);
}
