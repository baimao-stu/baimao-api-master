package com.baimao.bmapisdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baimao.bmapisdk.utils.SignUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共的API-SDK，抽取ak,sk并且封装生成签名的过程
 */
public class CommonApiClient {

    protected final String accessKey;
    protected final String accessSecret;

    protected static final String GATEWAY_HOST ="http://localhost:8090";

    public CommonApiClient(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    /**
     * 负责将数字签名的相关参数填入请求头中
     * nonce 随机数
     * timestamp 时间戳
     * sign 密钥加密后的签名
     * @param body 用户请求参数
     * @return
     */
    protected static Map<String,String> getHeadMap(String body, String accessKey, String accessSecret){
        if(StrUtil.isEmpty(body)) body = "";
        Map<String,String> map = new HashMap<>();
        map.put("body",body);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        map.put("accessKey",accessKey);
        /** 只要是请求都有可能被拦截，因此不传密钥，传递将密钥加密后的签名 */
//        map.put("accessSecret",accessSecret);
        map.put("sign", SignUtil.Sign(body, accessSecret));
        return map;
    }


}
