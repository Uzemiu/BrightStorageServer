package cn.brightstorage.controller;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.service.RelationService;
import cn.brightstorage.service.mapper.RelationMapper;
import cn.brightstorage.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/relation")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;
    private final RelationMapper relationMapper;

    @GetMapping
    public BaseResponse<?> listByCurrentUser(){
        Set<Relation> relations = SecurityUtil.getCurrentUser().getRelations();
        relations.forEach(relation -> relation.setMembers(null)); // 懒加载成员信息
        return BaseResponse.ok("ok", relationMapper.toDto(relations));
    }

    @GetMapping("/{id}")
    public BaseResponse<?> listByRelationId(@PathVariable String id){
        return BaseResponse.ok();
    }

    @PostMapping
    public BaseResponse<?> createRelation(@RequestBody @Validated RelationDTO relationDTO){
        relationService.create(relationDTO);
        return BaseResponse.ok();
    }

    @PutMapping
    public BaseResponse<?> updateRelation(@RequestBody @Validated RelationDTO relationDTO){
        relationService.update(relationDTO);
        return BaseResponse.ok();
    }

    @DeleteMapping
    public BaseResponse<?> deleteRelation(Long id){
        relationService.deleteById(id);
        return BaseResponse.ok("已成功解散关系");
    }
}
