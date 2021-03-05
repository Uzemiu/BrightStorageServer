package cn.brightstorage.controller;

import cn.brightstorage.model.dto.CategoryDTO;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.CategoryService;
import cn.brightstorage.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("获取当前用户创建目录")
    @GetMapping
    public BaseResponse<?> listByCurrentUser(){
        return BaseResponse.ok("ok",categoryService.listByOwnerId(SecurityUtil.getCurrentUser().getId()));
    }

    @ApiOperation("创建新目录")
    @PostMapping
    public BaseResponse<?> create(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return BaseResponse.ok();
    }

    @ApiOperation("更新已有目录")
    @PutMapping
    public BaseResponse<?> update(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return BaseResponse.ok();
    }

    @ApiOperation("删除目录")
    @DeleteMapping("{id:\\d+}")
    public BaseResponse<?> deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return BaseResponse.ok();
    }

}
