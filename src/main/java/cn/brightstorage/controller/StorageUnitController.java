package cn.brightstorage.controller;

import cn.brightstorage.model.dto.StorageUnitDTO;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.StorageUnitService;
import cn.brightstorage.service.mapper.StorageUnitMapper;
import cn.brightstorage.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageUnitController {

    private final StorageUnitService storageUnitService;
    private final StorageUnitMapper storageUnitMapper;

    @GetMapping
    public BaseResponse<?> listByCurrentUser(){
        List<StorageUnit> tops = storageUnitService.listByParentIdAndOwner(0L, SecurityUtil.getCurrentUser());
        List<StorageUnitDTO> topDTOs = storageUnitMapper.toDto(tops);
        // 只显示一层子物品
        topDTOs.forEach(storageUnitDTO -> {
            List<StorageUnit> children = storageUnitService.listByParentId(storageUnitDTO.getId());
            storageUnitDTO.setChildren(storageUnitMapper.toDto(children));
        });
        return BaseResponse.ok("ok", topDTOs);
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

    @DeleteMapping
    public BaseResponse<?> deleteById(String id){

        return BaseResponse.ok();
    }
}
