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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/storage")
public class StorageUnitController {

    private final StorageUnitService storageUnitService;

    @GetMapping
    public BaseResponse<?> listTopsByCurrentUser(){
        List<StorageUnitDTO> topDTOs = storageUnitService.listByParentIdAndOwner(0L, SecurityUtil.getCurrentUser());
        return BaseResponse.ok("ok", topDTOs);
    }

    @GetMapping("/{id:\\d+}")
    public BaseResponse<?> listByIds(@PathVariable Long id){
        StorageUnitDTO dto = storageUnitService.findById(id);

        storageUnitService.checkOwnership(dto);

        return BaseResponse.ok("ok", dto);
    }

    @GetMapping("/query")
    public BaseResponse<?> query(StorageUnitQuery query,
                                 @PageableDefault(sort = {"updateTime"},
                                         direction = Sort.Direction.DESC) Pageable pageable){

        // TODO checkownership
        return BaseResponse.ok("ok", storageUnitService.query(query,pageable));
    }

    /**
     * 获取关系共享物品
     * @param id 关系ID
     * @return /
     */
    @GetMapping("/sharedRelation/{id:\\d+}")
    public BaseResponse<List<StorageUnitDTO>> listByRelationId(@PathVariable Long id){
        return BaseResponse.ok("ok", storageUnitService.listByRelationId(id));
    }

    @PostMapping("/share")
    public BaseResponse<?> shareStorageUnit(@RequestBody Map<String, Long> param){
        Long relationId = param.get("relationId");
        Long storageId = param.get("storageId");
        storageUnitService.shareStorageUnit(relationId, storageId);
        return BaseResponse.ok();
    }

    @PostMapping
    public BaseResponse<?> create(@RequestBody @Validated StorageUnitDTO storageUnitDTO){
        storageUnitService.checkCategoriesOwnership(storageUnitDTO);

        storageUnitService.create(storageUnitDTO);
        return BaseResponse.ok();
    }

    @PutMapping
    public BaseResponse<?> update(@RequestBody @Validated StorageUnitDTO storageUnitDTO){
        storageUnitService.checkOwnership(storageUnitDTO.getId());
        storageUnitService.checkCategoriesOwnership(storageUnitDTO);

        storageUnitService.update(storageUnitDTO);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id:\\d+}")
    public BaseResponse<?> deleteById(@PathVariable Long id){
        StorageUnit storageUnit = storageUnitService.checkOwnership(id);

        storageUnitService.delete(storageUnit);
        return BaseResponse.ok();
    }

}
