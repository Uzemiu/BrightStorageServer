package cn.brightstorage.repository.base;

import cn.brightstorage.model.entity.OwnershipEntity;
import cn.brightstorage.model.entity.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OwnershipRepository<ENTITY extends OwnershipEntity, ID> extends BaseRepository<ENTITY, ID>{

    List<ENTITY> getByOwner(User owner);
}
