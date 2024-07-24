package com.baimao.bmapisdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baimao.bmapicommon.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baimao
 * @title UserApiClient
 */
public class UserApiClient extends CommonApiClient{
    public UserApiClient(String accessKey, String accessSecret) {
        super(accessKey, accessSecret);
    }

    public String getNameUsingPostJson(User user) {
        String jsonStr = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/json")
                .body(jsonStr)
                .addHeaders(getHeadMap(jsonStr,accessKey,accessSecret))
                .execute();
        System.out.println(httpResponse.getStatus());
        return httpResponse.body();
    }
}
