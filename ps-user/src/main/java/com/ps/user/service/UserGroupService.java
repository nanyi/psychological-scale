package com.ps.user.service;

import com.ps.common.request.PageRequest;
import com.ps.common.response.PageResult;
import com.ps.core.entity.UserGroup;
import com.ps.core.entity.UserGroupMember;

import java.util.List;

public interface UserGroupService {

    UserGroup getGroupById(Long id);

    List<UserGroup> getEnterpriseGroups(Long enterpriseId);

    UserGroup createGroup(String name, String code, String description, Long enterpriseId);

    void updateGroup(Long id, String name, String description);

    void deleteGroup(Long id);

    void addMember(Long groupId, Long userId);

    void removeMember(Long groupId, Long userId);

    List<Long> getGroupMembers(Long groupId);

    PageResult<UserGroup> getGroupList(PageRequest request, Long enterpriseId);
}
