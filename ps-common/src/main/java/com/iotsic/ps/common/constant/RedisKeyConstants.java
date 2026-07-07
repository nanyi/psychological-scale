package com.iotsic.ps.common.constant;

/**
 * Redis Key 常量
 *
 * @author Ryan
 * @since 2026-04-27 22:30
 */
public interface RedisKeyConstants {

    /**
     * 访问令牌 Redis Key
     */
    String OAUTH2_ACCESS_TOKEN = "oauth2_access_token:%s";

    /**
     * 角色的缓存
     * <p>
     * KEY 格式：role:{id}
     * VALUE 数据类型：String 角色信息
     * </p>
     */
    String ROLE = "role";

    /**
     * 用户拥有的角色编号的缓存
     * <p>
     * KEY 格式：user_role_ids:{userId}
     * VALUE 数据类型：String 角色编号集合
     */
    String USER_ROLE_ID_LIST = "user_role_ids";

    /**
     * 拥有指定菜单的角色编号的缓存
     * <p>
     * KEY 格式：user_role_ids:{menuId}
     * VALUE 数据类型：String 角色编号集合
     */
    String MENU_ROLE_ID_LIST = "menu_role_ids";

    /**
     * 拥有权限对应的菜单编号数组的缓存
     * <p>
     * KEY 格式：permission_menu_ids:{permission}
     * VALUE 数据类型：String 菜单编号数组
     */
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";

    /**
     * OAuth2 客户端的缓存
     * <p>
     * KEY 格式：oauth_client:{id}
     * VALUE 数据类型：String 客户端信息
     */
    String OAUTH_CLIENT = "oauth_client";

    /**
     * 会话缓存 Key
     * <p>
     * KEY 格式：security:session:{userId}:{deviceType}:{deviceId}
     * VALUE 数据类型：JSON LoginUser对象
     */
    String SESSION = "security:session:%s:%s:%s";

    /**
     * 用户所有会话 Hash
     * <p>
     * KEY 格式：security:user:sessions:{userId}
     * VALUE 数据类型：Hash {deviceType:deviceId → sessionJson}
     */
    String USER_SESSIONS = "security:user:sessions:%s";

    /**
     * 设备类型下所有会话 Hash
     * <p>
     * KEY 格式：security:device:sessions:{deviceType}
     * VALUE 数据类型：Hash {userId:deviceId → sessionJson}
     */
    String DEVICE_SESSIONS = "security:device:sessions:%s";
}
