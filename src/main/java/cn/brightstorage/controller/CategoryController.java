package cn.brightstorage.controller;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public BaseResponse<?> listByCurrentUser(){
        return BaseResponse.ok("ok",categoryService.listByOwnerId(SecurityUtil.getCurrentUser().getId()));
    }

    @PostMapping
    public BaseResponse<?> create(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return BaseResponse.ok();
    }

    @PutMapping
    public BaseResponse<?> update(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return BaseResponse.ok();
    }

    @DeleteMapping("{id}")
    public BaseResponse<?> deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return BaseResponse.ok();
    }

}
