package com.iotsic.ps.core.dal.redis.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.iotsic.ps.common.constant.RedisKeyConstants;
import com.iotsic.ps.core.entity.OAuth2AccessToken;
import com.iotsic.smart.framework.cache.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * 访问令牌 Redis DAO
 *
 * @author Ryan
 * @since 2026-04-27 22:24
 */
@Slf4j
@Repository
public class OAuth2AccessTokenRedisDAO {

    private static final String DEVICE_TOKEN_INDEX = "oauth2:device:index:%s:%s";

    public OAuth2AccessToken get(String accessToken) {
        String redisKey = formatKey(accessToken);
        return RedisUtils.get(redisKey, OAuth2AccessToken.class);
    }

    public void set(OAuth2AccessToken accessToken) {
        long time = LocalDateTimeUtil.between(LocalDateTime.now(), accessToken.getExpiresTime(), ChronoUnit.SECONDS);
        String redisKey = formatKey(accessToken.getAccessToken());
        if (time > 0) {
            accessToken
                    .setUpdateBy( null)
                    .setUpdateTime(null)
                    .setCreateBy(null)
                    .setCreateTime(null)
                    .setDeleted(null);

            RedisUtils.set(redisKey, accessToken, Duration.ofSeconds(time));

            if (accessToken.getDeviceType() != null && accessToken.getDeviceId() != null) {
                String deviceIndexKey = String.format(DEVICE_TOKEN_INDEX, accessToken.getDeviceType(), accessToken.getDeviceId());
                RedisUtils.set(deviceIndexKey, accessToken.getAccessToken(), Duration.ofSeconds(time));
            }
        } else {
            RedisUtils.delete(redisKey);
            if (accessToken.getDeviceType() != null && accessToken.getDeviceId() != null) {
                String deviceIndexKey = String.format(DEVICE_TOKEN_INDEX, accessToken.getDeviceType(), accessToken.getDeviceId());
                RedisUtils.delete(deviceIndexKey);
            }
        }
    }

    /**
     * 根据设备类型和设备ID获取访问令牌
     *
     * @param deviceType 设备类型: web, app, miniprogram, pc
     * @param deviceId 设备唯一标识
     * @return 访问令牌信息
     */
    public OAuth2AccessToken getByDevice(String deviceType, String deviceId) {
        String deviceIndexKey = String.format(DEVICE_TOKEN_INDEX, deviceType, deviceId);
        String accessToken = RedisUtils.get(deviceIndexKey, String.class);
        if (accessToken != null) {
            return get(accessToken);
        }
        return null;
    }

    /**
     * 根据设备类型和设备ID删除访问令牌
     *
     * @param deviceType 设备类型
     * @param deviceId 设备唯一标识
     */
    public void deleteByDevice(String deviceType, String deviceId) {
        OAuth2AccessToken token = getByDevice(deviceType, deviceId);
        if (token != null) {
            String redisKey = formatKey(token.getAccessToken());
            RedisUtils.delete(redisKey);
        }
        String deviceIndexKey = String.format(DEVICE_TOKEN_INDEX, deviceType, deviceId);
        RedisUtils.delete(deviceIndexKey);
    }

    private static String formatKey(String accessToken) {
        return String.format(RedisKeyConstants.OAUTH2_ACCESS_TOKEN, accessToken);
    }
}
