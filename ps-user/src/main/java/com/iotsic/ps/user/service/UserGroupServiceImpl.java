package com.iotsic.ps.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iotsic.ps.common.exception.BusinessException;
import com.iotsic.ps.common.request.PageRequest;
import com.iotsic.ps.common.response.PageResult;
import com.iotsic.ps.core.entity.User;
import com.iotsic.ps.core.entity.UserGroup;
import com.iotsic.ps.core.entity.UserGroupMember;
import com.iotsic.ps.user.mapper.UserGroupMapper;
import com.iotsic.ps.user.mapper.UserGroupMemberMapper;
import com.iotsic.ps.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupMapper userGroupMapper;
    private final UserGroupMemberMapper userGroupMemberMapper;
    private final UserMapper userMapper;

    @Override
    public UserGroup getGroupById(Long id) {
        UserGroup group = userGroupMapper.selectById(id);
        if (group == null || group.getDeleted() == 1) {
            throw BusinessException.of("GROUP_NOT_FOUND", "分组不存在");
        }
        return group;
    }

    @Override
    public List<UserGroup> getEnterpriseGroups(Long enterpriseId) {
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroup::getEnterpriseId, enterpriseId)
                .orderByAsc(UserGroup::getSort);
        return userGroupMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGroup createGroup(String name, String code, String description, Long enterpriseId) {
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroup::getGroupCode, code)
                .eq(UserGroup::getEnterpriseId, enterpriseId);
        if (userGroupMapper.selectOne(wrapper) != null) {
            throw BusinessException.of("GROUP_EXIST", "分组代码已存在");
        }

        UserGroup group = new UserGroup();
        group.setGroupName(name);
        group.setGroupCode(code);
        group.setDescription(description);
        group.setEnterpriseId(enterpriseId);
        group.setMemberCount(0);
        group.setCreateTime(LocalDateTime.now());
        group.setUpdateTime(LocalDateTime.now());

        userGroupMapper.insert(group);
        return group;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroup(Long id, String name, String description) {
        UserGroup group = getGroupById(id);
        group.setGroupName(name);
        group.setDescription(description);
        group.setUpdateTime(LocalDateTime.now());
        userGroupMapper.updateById(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(Long id) {
        UserGroup group = getGroupById(id);
        group.setDeleted(1);
        group.setUpdateTime(LocalDateTime.now());
        userGroupMapper.updateById(group);

        LambdaQueryWrapper<UserGroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupMember::getGroupId, id);
        userGroupMemberMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(Long groupId, Long userId) {
        getGroupById(groupId);

        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getId, userId);
        if (userMapper.selectOne(userWrapper) == null) {
            throw BusinessException.of("USER_NOT_FOUND", "用户不存在");
        }

        LambdaQueryWrapper<UserGroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupMember::getGroupId, groupId)
                .eq(UserGroupMember::getUserId, userId);
        if (userGroupMemberMapper.selectOne(wrapper) != null) {
            throw BusinessException.of("MEMBER_EXIST", "用户已在分组中");
        }

        UserGroupMember member = new UserGroupMember();
        member.setGroupId(groupId);
        member.setUserId(userId);
        member.setCreateTime(LocalDateTime.now());
        member.setUpdateTime(LocalDateTime.now());
        userGroupMemberMapper.insert(member);

        UserGroup group = getGroupById(groupId);
        group.setMemberCount(group.getMemberCount() + 1);
        group.setUpdateTime(LocalDateTime.now());
        userGroupMapper.updateById(group);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeMember(Long groupId, Long userId) {
        LambdaQueryWrapper<UserGroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupMember::getGroupId, groupId)
                .eq(UserGroupMember::getUserId, userId);
        userGroupMemberMapper.delete(wrapper);

        UserGroup group = getGroupById(groupId);
        group.setMemberCount(Math.max(0, group.getMemberCount() - 1));
        group.setUpdateTime(LocalDateTime.now());
        userGroupMapper.updateById(group);
    }

    @Override
    public List<Long> getGroupMembers(Long groupId) {
        getGroupById(groupId);
        LambdaQueryWrapper<UserGroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupMember::getGroupId, groupId);
        List<UserGroupMember> members = userGroupMemberMapper.selectList(wrapper);
        return members.stream()
                .map(UserGroupMember::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<UserGroup> getGroupList(PageRequest request, Long enterpriseId) {
        Page<UserGroup> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<UserGroup> wrapper = new LambdaQueryWrapper<>();
        if (enterpriseId != null) {
            wrapper.eq(UserGroup::getEnterpriseId, enterpriseId);
        }
        wrapper.orderByAsc(UserGroup::getSort);
        IPage<UserGroup> result = userGroupMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), request.getPageNum(), request.getPageSize());
    }
}
