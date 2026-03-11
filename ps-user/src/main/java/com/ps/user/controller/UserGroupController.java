package com.ps.user.controller;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.common.result.RestResult;
import com.ps.core.entity.UserGroup;
import com.ps.user.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user-group")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping("/{id}")
    public RestResult<UserGroup> getGroupById(@PathVariable Long id) {
        return RestResult.success(userGroupService.getGroupById(id));
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public RestResult<List<UserGroup>> getEnterpriseGroups(@PathVariable Long enterpriseId) {
        return RestResult.success(userGroupService.getEnterpriseGroups(enterpriseId));
    }

    @PostMapping("/create")
    public RestResult<UserGroup> createGroup(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String code = (String) request.get("code");
        String description = (String) request.get("description");
        Long enterpriseId = Long.valueOf(request.get("enterpriseId").toString());

        UserGroup group = userGroupService.createGroup(name, code, description, enterpriseId);
        return RestResult.success("分组创建成功", group);
    }

    @PutMapping("/{id}")
    public RestResult<Void> updateGroup(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        userGroupService.updateGroup(id, name, description);
        return RestResult.success();
    }

    @DeleteMapping("/{id}")
    public RestResult<Void> deleteGroup(@PathVariable Long id) {
        userGroupService.deleteGroup(id);
        return RestResult.success();
    }

    @PostMapping("/member/add")
    public RestResult<Void> addMember(@RequestBody Map<String, Object> request) {
        Long groupId = Long.valueOf(request.get("groupId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        userGroupService.addMember(groupId, userId);
        return RestResult.success();
    }

    @PostMapping("/member/remove")
    public RestResult<Void> removeMember(@RequestBody Map<String, Object> request) {
        Long groupId = Long.valueOf(request.get("groupId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());
        userGroupService.removeMember(groupId, userId);
        return RestResult.success();
    }

    @GetMapping("/{id}/members")
    public RestResult<Map<String, Object>> getGroupMembers(@PathVariable Long id) {
        List<Long> members = userGroupService.getGroupMembers(id);
        Map<String, Object> result = new HashMap<>();
        result.put("members", members);
        return RestResult.success(result);
    }

    @GetMapping("/list")
    public RestResult<PageResult<UserGroup>> getGroupList(PageRequest request,
                                                          @RequestParam(required = false) Long enterpriseId) {
        return RestResult.success(userGroupService.getGroupList(request, enterpriseId));
    }
}
