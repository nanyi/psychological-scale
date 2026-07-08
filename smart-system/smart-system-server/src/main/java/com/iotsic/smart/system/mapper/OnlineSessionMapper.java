package com.iotsic.smart.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iotsic.smart.system.entity.OnlineSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 在线会话 Mapper
 *
 * @author Ryan
 * @since 2026-07-07
 */
@Mapper
public interface OnlineSessionMapper extends BaseMapper<OnlineSession> {

    /**
     * 踢出指定会话
     */
    @Update("UPDATE sys_online_session SET status = 4, update_time = NOW() " +
            "WHERE user_id = #{userId} AND device_type = #{deviceType} AND device_id = #{deviceId} AND deleted = 0")
    int kickSession(@Param("userId") Long userId, @Param("deviceType") String deviceType,
                    @Param("deviceId") String deviceId);

    /**
     * 踢出用户所有会话
     */
    @Update("UPDATE sys_online_session SET status = 4, update_time = NOW() " +
            "WHERE user_id = #{userId} AND deleted = 0")
    int kickUserAllSessions(@Param("userId") Long userId);

    /**
     * 踢出设备类型的所有会话
     */
    @Update("UPDATE sys_online_session SET status = 4, update_time = NOW() " +
            "WHERE device_type = #{deviceType} AND deleted = 0")
    int kickDeviceTypeAllSessions(@Param("deviceType") String deviceType);

    /**
     * 更新最后访问时间
     */
    @Update("UPDATE sys_online_session SET last_access_time = NOW() " +
            "WHERE user_id = #{userId} AND device_type = #{deviceType} AND device_id = #{deviceId} AND deleted = 0")
    int updateLastAccessTime(@Param("userId") Long userId, @Param("deviceType") String deviceType,
                            @Param("deviceId") String deviceId);
}
