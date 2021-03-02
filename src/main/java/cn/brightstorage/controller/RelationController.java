package cn.brightstorage.controller;

import cn.brightstorage.model.dto.RelationDTO;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.model.param.RemoveMemberParam;
import cn.brightstorage.model.support.BaseResponse;
import cn.brightstorage.model.support.VerificationCode;
import cn.brightstorage.service.RelationService;
import cn.brightstorage.service.mapper.RelationMapper;
import cn.brightstorage.service.mapper.UserVOMapper;
import cn.brightstorage.utils.QRCodeUtil;
import cn.brightstorage.utils.RedisUtil;
import cn.brightstorage.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.brightstorage.constant.RelationConstant.*;

@RestController
@RequestMapping("/api/relation")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;
    private final RelationMapper relationMapper;
    private final UserVOMapper userVOMapper;

    @GetMapping
    public BaseResponse<List<RelationDTO>> listByCurrentUser(){
        return BaseResponse.ok("ok", relationService.listByUserId(SecurityUtil.getCurrentUser().getId()));
    }

    @GetMapping("/{id}")
    public BaseResponse<?> getById(@PathVariable Long id){
        Relation relation = relationService.getNotNullById(id);
        relationService.checkMembership(relation);
        return BaseResponse.ok("ok", relationMapper.toDto(relation));
    }

    @GetMapping("/members/{id}")
    public BaseResponse<?> listMembersByRelationId(@PathVariable Long id){
        Set<User> members = relationService.getNotNullById(id).getMembers();
        return BaseResponse.ok("ok", userVOMapper.toDto(members));
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

    @DeleteMapping("/{id}")
    public BaseResponse<?> deleteRelation(@PathVariable Long id){
        relationService.deleteById(id);
        return BaseResponse.ok("已成功解散关系");
    }

    @GetMapping("/inviteCode")
    public BaseResponse<?> getInviteCode(@RequestParam Long id){
        Map<String, Object> result = new HashMap<>(1);
        result.put("uuid", relationService.newInviteCode(id, true));
        return BaseResponse.ok("ok", result);
    }

    @GetMapping("/inviteCode/new")
    public BaseResponse<?> getNewInviteCode(@RequestParam Long id){
        Map<String, Object> result = new HashMap<>(1);
        result.put("uuid", relationService.newInviteCode(id, false));
        return BaseResponse.ok("ok", result);
    }

    @PostMapping("/join")
    public BaseResponse<?> joinRelation(String code){

        return BaseResponse.ok();
    }

    @PostMapping("/kick")
    public BaseResponse<?> kickMember(@RequestBody @Validated RemoveMemberParam param){

        return BaseResponse.ok();
    }
}
