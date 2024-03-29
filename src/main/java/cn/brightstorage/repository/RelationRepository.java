package cn.brightstorage.repository;

import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.repository.base.BaseRepository;
import cn.brightstorage.repository.base.OwnershipRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends OwnershipRepository<Relation, Long> {

}
