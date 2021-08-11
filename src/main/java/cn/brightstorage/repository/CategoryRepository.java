package cn.brightstorage.repository;

import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.base.BaseRepository;
import cn.brightstorage.repository.base.OwnershipRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends OwnershipRepository<Category, Long> {

}
