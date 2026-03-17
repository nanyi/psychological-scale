package com.iotsic.ps.user.controller;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.common.result.RestResult;
import com.iotsic.ps.user.dto.GroupMembersResponse;
import com.iotsic.ps.user.dto.UserGroupCreateRequest;
import com.iotsic.ps.user.dto.UserGroupMemberRequest;
import com.iotsic.ps.user.dto.UserGroupUpdateRequest;
import com.iotsic.ps.core.entity.UserGroup;
import com.iotsic.ps.user.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户组控制器
 * 负责用户组的创建、查询、成员管理等请求
 * 
 * @author Ryan
 * @since 2026-03-12
 */
@Slf4j
@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    /**
     * 根据ID获取用户组
     * 
     * @param id 用户组ID
     * @return 用户组信息
     */
    @GetMapping("/detail/{id}")
    public RestResult<UserGroup> getGroupById(@PathVariable Long id) {
        return RestResult.success(userGroupService.getGroupById(id));
    }

    /**
     * 获取企业下的所有用户组
     * 
     * @param enterpriseId 企业ID
     * @return 用户组列表
     */
    @GetMapping("/by-enterprise/{enterpriseId}")
    public RestResult<List<UserGroup>> getEnterpriseGroups(@PathVariable Long enterpriseId) {
        return RestResult.success(userGroupService.getEnterpriseGroups(enterpriseId));
    }

    /**
     * 创建用户组
     * 
     * @param request 用户组创建请求
     * @return 用户组信息
     */
    @PostMapping("/create")
    public RestResult<UserGroup> createGroup(@RequestBody UserGroupCreateRequest request) {
        UserGroup group = userGroupService.createGroup(
                request.getName(),
                request.getCode(),
                request.getDescription(),
                request.getEnterpriseId()
        );
        return RestResult.success("分组创建成功", group);
    }

    /**
     * 更新用户组
     * 
     * @param id 用户组ID
     * @param request 用户组更新请求
     * @return 操作结果
     */
    @PutMapping("/update/{id}")
    public RestResult<Void> updateGroup(@PathVariable Long id, @RequestBody UserGroupUpdateRequest request) {
        userGroupService.updateGroup(id, request.getName(), request.getDescription());
        return RestResult.success();
    }

    /**
     * 删除用户组
     * 
     * @param id 用户组ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public RestResult<Void> deleteGroup(@PathVariable Long id) {
        userGroupService.deleteGroup(id);
        return RestResult.success();
    }

    /**
     * 添加成员
     * 
     * @param request 成员请求
     * @return 操作结果
     */
    @PostMapping("/member/add")
    public RestResult<Void> addMember(@RequestBody UserGroupMemberRequest request) {
        for (Long userId : request.getUserIds()) {
            userGroupService.addMember(request.getGroupId(), userId);
        }
        return RestResult.success();
    }

    /**
     * 移除成员
     * 
     * @param request 成员请求
     * @return 操作结果
     */
    @PostMapping("/member/remove")
    public RestResult<Void> removeMember(@RequestBody UserGroupMemberRequest request) {
        for (Long userId : request.getUserIds()) {
            userGroupService.removeMember(request.getGroupId(), userId);
        }
        return RestResult.success();
    }

    /**
     * 获取用户组成员
     * 
     * @param id 用户组ID
     * @return 成员列表
     */
    @GetMapping("/members/{id}")
    public RestResult<GroupMembersResponse> getGroupMembers(@PathVariable Long id) {
        List<Long> members = userGroupService.getGroupMembers(id);
        
        GroupMembersResponse response = new GroupMembersResponse();
        response.setGroupId(id);
        response.setMemberIds(members);
        response.setMemberCount(members.size());
        
        return RestResult.success(response);
    }

    /**
     * 获取用户组列表
     * 
     * @param request 分页请求
     * @param enterpriseId 企业ID
     * @return 用户组分页列表
     */
    @GetMapping("/list")
    public RestResult<PageResult<UserGroup>> getGroupList(PageRequest request,
                                                          @RequestParam(value = "enterpriseId", required = false) Long enterpriseId) {
        return RestResult.success(userGroupService.getGroupList(request, enterpriseId));
    }
}
