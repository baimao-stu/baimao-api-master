package com.baimao.bmapigateway.filter;//package com.baimao.bmapigateway.filter;
//
//import com.baimao.bmapicommon.model.entity.User;
//import com.baimao.bmapicommon.utils.JwtUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//
//import static com.baimao.bmapicommon.constant.RedisConstants.LOGIN_TOKEN_KEY;
//import static com.baimao.bmapicommon.constant.RedisConstants.TOKEN_NAME;
//
///**
// *  网关不再校验登录
// *  设想：其他开发者引入了你的sdk，而你的sdk是通过网关来转发到模拟接口项目的，
// *       此时网关如果还要校验身份，开发者无法知道如何校验
// *
// * 全局登录校验过滤器
// * @author baimao
// * @title LoginGlobalFilter
// */
////@Component
//@Slf4j
//@Deprecated
//public class LoginGlobalFilter implements GlobalFilter, Ordered {
//
//    @Resource
//    private RedisCache redisCache;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        HttpHeaders headers = request.getHeaders();
//
//        //1、从cookie中获取token
//        String cookies = headers.getFirst("Cookie");
//        if (StringUtils.isBlank(cookies)){
//            return handleNoAuth(response);
//        }
//        String token = getTokenByCookie(cookies);
//        if (StringUtils.isBlank(cookies)){
//            return handleNoAuth(response);
//        }
//        //2、解析token 获取 userId，根据userId获取从redis获取用户信息
//        String userId = null;
//        try {
//            userId = JwtUtil.parseJWT(token).getSubject();
//        } catch (Exception e) {
//            return handleNoAuth(response);
//        }
//        User loginUser = redisCache.getCacheObject(LOGIN_TOKEN_KEY + userId);
//        if(loginUser == null) {
//            return handleNoAuth(response);
//        }
//        //4、认证通过，放行
//        return chain.filter(exchange);
//    }
//
//    private String getTokenByCookie(String cookie) {
//        String[] split = cookie.split(";");
//        for (String cookeKey : split) {
//            String[] split1 = cookeKey.split("=");
//            String cookieName = split1[0];
//            if (TOKEN_NAME.equals(cookieName.trim())){
//                return split1[1];
//            }
//        }
//
//        return null;
//    }
//
//    public static Mono<Void> handleNoAuth(ServerHttpResponse response) {
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//
//    @Override
//    public int getOrder() {
//        return -2;
//    }
//}
