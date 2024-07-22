package com.baimao.bmapigateway;

import cn.hutool.core.util.StrUtil;
import com.baimao.bmapicommon.model.entity.InterfaceInfo;
import com.baimao.bmapicommon.model.entity.User;
import com.baimao.bmapicommon.service.InnerInterfaceInfoService;
import com.baimao.bmapicommon.service.InnerUserInterfaceInfoService;
import com.baimao.bmapicommon.service.InnerUserService;
import com.baimao.bmapisdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤器：用户请求API网关
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    //ip白名单
    private static final List<String> WHITE_IP = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8111";

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;


    // todo 流量染色：打个请求头
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String url = INTERFACE_HOST + request.getPath();
        String method = request.getMethodValue();
        //1.请求日志
        log.info("请求id:" + request.getId());
        log.info("请求路径:" + url);
        log.info("请求方法:" + method);
        log.info("请求参数:" + request.getQueryParams());
        String hostString = request.getRemoteAddress().getHostString();
        log.info("请求来源:" + hostString);
        //2.黑白名单
        if(!WHITE_IP.contains(hostString)) {
            return handleNoAuth(response);
        }
        //3.用户鉴权（校验ak、sk）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        //校验签名参数
        if(StrUtil.isEmpty(accessKey)) {
            return handleNoAuth(response);
        }
        /** 根据accessKey从数据库查询的 accessSecret */
        User userInfo = null;
        try {
            userInfo = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("<== == getInvokeUser error");
        }
        if(userInfo == null) {
            return handleNoAuth(response);
        }
        String accessSecret = userInfo.getAccessSecret();
        String valiad_sign = SignUtil.Sign(body, accessSecret);
        if(!valiad_sign.equals(sign)) {
            return handleNoAuth(response);
        }
        /** 校验随机数 */
        if(nonce.length() > 4) {
            return handleNoAuth(response);
        }
        /** 时间和当前时间差不超过5分钟 */
        if((System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) / 60 > 5) {
            return handleNoAuth(response);
        }
        /** 4.请求的接口是否存在 */
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(url, method);
        } catch (Exception e) {
            log.error("<== == getInterfaceInfo error");
        }
        if(interfaceInfo == null) {
            return handleNoAuth(response);
        }
//        /** 5.用户对该接口是否还有调用次数 */
//        Integer invokeNum = 0;
//        try {
//            invokeNum = innerUserInterfaceInfoService.getInvokeNum(interfaceInfo.getId(), userInfo.getId());
//        } catch (Exception e) {
//            log.error("<== == getInvokeNum error");
//        }
//        if(invokeNum <= 0) {
//            return handleNoAuth(response);
//        }
        /** 6.转发请求、调用接口、响应日志 */
        return handleResponse(exchange,chain,interfaceInfo.getId(),userInfo.getId());
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @param interfaceInfoId 接口 id
     * @param userId 用户 id
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        /** 7. 调用成功，接口调用次数 + 1 invokeCount */
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("<== == invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        /** 8. 打印响应日志 */
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    public static Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static Mono<Void> handleError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    //越小则优先级越高,-1表示最高优先级
    @Override
    public int getOrder() {//也可以用接口指定优先级
        return -1;
    }
}
