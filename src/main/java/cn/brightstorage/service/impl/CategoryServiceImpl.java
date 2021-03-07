package cn.brightstorage.service.impl;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.CategoryRepository;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.service.base.AbstractOwnershipService;
import cn.brightstorage.service.mapper.CategoryMapper;
import cn.brightstorage.utils.AssertUtil;
import cn.brightstorage.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl extends AbstractOwnershipService<Category, Long> implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    protected CategoryServiceImpl(CategoryRepository repository,
                                  CategoryMapper categoryMapper) {
        super(repository);
        this.categoryRepository = repository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryMapper.toEntity(categoryDTO));
    }

    @Override
    public Category create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setOwner(SecurityUtil.getCurrentUser());
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> listByOwner(User owner) {
        return categoryMapper.toDto(categoryRepository.getByOwner(owner));
    }

    @Override
    public List<CategoryDTO> listByOwnerId(String userId) {
        User user = new User();
        user.setId(userId);
        return listByOwner(user);
    }
}
