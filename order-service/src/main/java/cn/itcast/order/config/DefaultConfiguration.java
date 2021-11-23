package cn.itcast.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author Xiaotian
 * @program cloud-demo
 * @description
 * @create 2021-11-23 19:37
 */
public class DefaultConfiguration {

    @Bean
    public Logger.Level logLevel() {
        // 日志级别: Log only the request method and URL and the response status code and execution time.
        return Logger.Level.BASIC;
    }
}