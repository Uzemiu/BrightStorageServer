package cn.brightstorage.repository;

import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.BaseRepository;
import cn.brightstorage.repository.base.OwnershipRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends OwnershipRepository<Category, Long> {

}
