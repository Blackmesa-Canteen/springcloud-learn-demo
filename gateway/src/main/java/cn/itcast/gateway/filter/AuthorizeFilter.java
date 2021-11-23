package cn.itcast.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Xiaotian
 * @program cloud-demo
 * @description
 * @create 2021-11-24 01:12
 */

// 越小优先级越高
@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();

        // 2.获取参数中的所需attribute
        String auth = params.getFirst("authorization");

        if ("admin".equals(auth)) {
            // 放行 (这里是从chain里找到下一个过滤器了)
            return chain.filter(exchange);
        }

        // 状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        // 拦截
        return exchange.getResponse().setComplete();
    }
}