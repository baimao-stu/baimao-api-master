package com.baimao.bmapicommon.constant;

/**
 * Redis的key值
 *
 * @author xzx
 * @date 2022/11/21
 */
public class RedisConstants {

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login:tokens:";

    public static final String TOKEN_NAME = "token";
    //缓存穿透
    public static final Long CACHE_NULL_TTL = 2L;

}
