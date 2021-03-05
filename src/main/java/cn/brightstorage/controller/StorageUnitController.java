package cn.brightstorage.controller;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.query.StorageUnitQuery;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.mapper.StorageUnitMapper;
import cn.brightstorage.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageUnitController {

    private final StorageUnitService storageUnitService;

    @GetMapping
    public BaseResponse<?> listByCurrentUser(){
        List<StorageUnitDTO> topDTOs = storageUnitService.listByParentIdAndOwner(0L, SecurityUtil.getCurrentUser());
        return BaseResponse.ok("ok", topDTOs);
    }

    @GetMapping("/list")
    public BaseResponse<?> listByIds(@PathVariable Long id){

        return BaseResponse.ok();
    }

    @GetMapping("/query")
    public BaseResponse<?> query(StorageUnitQuery query,
                                 @PageableDefault(sort = {"updateTime"},
                                         direction = Sort.Direction.DESC) Pageable pageable){

        return BaseResponse.ok();
    }

    @PostMapping
    public BaseResponse<?> create(@RequestBody @Validated StorageUnitDTO storageUnitDTO){
        storageUnitService.create(storageUnitDTO);
        return BaseResponse.ok();
    }

    @PutMapping
    public BaseResponse<?> update(@RequestBody @Validated StorageUnitDTO storageUnitDTO){
        storageUnitService.update(storageUnitDTO);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id:\\d+}")
    public BaseResponse<?> deleteById(@PathVariable Long id){
        storageUnitService.deleteById(id);
        return BaseResponse.ok();
    }

    public BaseResponse<?> merge(@RequestBody List<StorageUnitDTO> storageUnitDTOS){

        return BaseResponse.ok();
    }
}
