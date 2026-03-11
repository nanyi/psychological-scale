package com.iotsic.ps.user.service;

import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.UserGroup;

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
