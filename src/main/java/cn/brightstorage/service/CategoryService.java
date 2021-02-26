package cn.brightstorage.service;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.service.base.CrudService;

import java.util.List;

public interface CategoryService extends CrudService<Category, Long> {

    Category create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    List<CategoryDTO> listByOwnerId(String userId);

    List<CategoryDTO> listByOwner(User owner);

}
