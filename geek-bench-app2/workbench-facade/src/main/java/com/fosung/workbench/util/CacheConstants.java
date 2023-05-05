package com.fosung.workbench.util;

/**
 *
 * 缓存标识类
 *
 * @author liuke
 * @date  2021/10/18 9:18
 * @version
*/
public final class CacheConstants {

    /**
     * 我的应用redis标识
     */
    public static final  String APP_CACHE_PREFIX="app:";

    /**
     * 授权范围
     */
    public static final String APP_SHELVES_USER_PREFIX="shelves:";

    /**
     * 授权范围
     */
    public static final String APP_SHELVES_IDENDITY_PREFIX="shelves:";

    /**
     * 授权范围
     */
    public static final String APP_SHELVES_ORG_PREFIX="shelves:";

    /**
     * 授权范围
     */
    public static final String APP_SHELVES_AREA_PREFIX="shelves:";

    /**
     * 授权 key 前缀
     */
    public static final String APP_SHELVES_PREFIX="shelves:";

    /**
     * 过期
     */
    public static final int EXPIRES_HOURS = 8;

    /**
     * 过期
     */
    public static final int EXPIRES_MINUTE = 5;

    public static final String CACHE_SPACER = "@*@";
}
