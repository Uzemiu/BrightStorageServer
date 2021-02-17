package cn.brightstorage.controller;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relation")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    public BaseResponse<?> getCurrentUserRelations(){

        return BaseResponse.ok();
    }

    @PostMapping
    public BaseResponse<?> createRelation(@Validated RelationDTO relationDTO){
        relationService.create(relationDTO);
        return BaseResponse.ok();
    }

    @PutMapping
    public BaseResponse<?> updateRelation(@Validated RelationDTO relationDTO){
        relationService.update(relationDTO);
        return BaseResponse.ok();
    }

    @DeleteMapping
    public BaseResponse<?> deleteRelation(){

        return BaseResponse.ok();
    }
}
