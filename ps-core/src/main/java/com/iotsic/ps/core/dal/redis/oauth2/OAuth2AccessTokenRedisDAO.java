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

/**
 * 访问令牌 Redis DAO
 * 
 * @author Ryan
 * @since 2026-04-27 22:24
 */
@Slf4j
@Repository
public class OAuth2AccessTokenRedisDAO {

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
        } else {
            RedisUtils.delete(redisKey);
        }
    }
    private static String formatKey(String accessToken) {
        return String.format(RedisKeyConstants.OAUTH2_ACCESS_TOKEN, accessToken);
    }
}
