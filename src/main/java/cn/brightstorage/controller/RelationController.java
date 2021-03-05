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
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
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
    private final RedisUtil redisUtil;

    @ApiOperation("获取当前用户加入的关系")
    @GetMapping
    public BaseResponse<List<RelationDTO>> listByCurrentUser(){
        return BaseResponse.ok("ok", relationService.listByUserId(SecurityUtil.getCurrentUser().getId()));
    }

    @ApiOperation("获取关系基础信息")
    @GetMapping("/{id:\\d+}")
    public BaseResponse<?> getById(@PathVariable Long id){
        Relation relation = relationService.getNotNullById(id);
        relationService.checkMembership(relation);
        return BaseResponse.ok("ok", relationMapper.toDto(relation));
    }

    @ApiOperation("获取关系成员信息")
    @GetMapping("/members/{id:\\d+}")
    public BaseResponse<?> listMembersByRelationId(@PathVariable Long id){
        Set<User> members = relationService.getNotNullById(id).getMembers();
        return BaseResponse.ok("ok", userVOMapper.toDto(members));
    }

    @ApiOperation("创建关系")
    @PostMapping
    public BaseResponse<?> createRelation(@RequestBody @Validated RelationDTO relationDTO){
        relationService.create(relationDTO);
        return BaseResponse.ok();
    }

    @ApiOperation("更新关系")
    @PutMapping
    public BaseResponse<?> updateRelation(@RequestBody @Validated RelationDTO relationDTO){
        relationService.update(relationDTO);
        return BaseResponse.ok();
    }

    @ApiOperation("解散关系")
    @DeleteMapping("/{id:\\d+}")
    public BaseResponse<?> deleteRelation(@PathVariable Long id){
        relationService.deleteById(id);
        return BaseResponse.ok("已成功解散关系");
    }

    @ApiOperation("获取关系邀请码")
    @GetMapping("/inviteCode")
    public BaseResponse<?> getInviteCode(@RequestParam Long id){
        Map<String, Object> result = new HashMap<>(1);
        result.put("uuid", relationService.newInviteCode(id, true));
        return BaseResponse.ok("ok", result);
    }

    @ApiOperation("更新并获取关系邀请码")
    @GetMapping("/inviteCode/new")
    public BaseResponse<?> getNewInviteCode(@RequestParam Long id){
        Map<String, Object> result = new HashMap<>(1);
        result.put("uuid", relationService.newInviteCode(id, false));
        return BaseResponse.ok("ok", result);
    }

    @ApiOperation("通过邀请码加入关系")
    @PostMapping("/join/{uuid}")
    public BaseResponse<?> joinRelation(@PathVariable String uuid){
        String relationId = (String)redisUtil.get(uuid);
        Assert.notNull(relationId, "二维码已过期");

        Relation relation = relationService.getNotNullById(Long.parseLong(relationId));
        relation.getMembers().add(SecurityUtil.getCurrentUser());
        relationService.update(relation);

        return BaseResponse.ok("加入关系成功");
    }

    @PostMapping("/kick")
    public BaseResponse<?> kickMember(@RequestBody @Validated RemoveMemberParam param){

        return BaseResponse.ok();
    }
}
