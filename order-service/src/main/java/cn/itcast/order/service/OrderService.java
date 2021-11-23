package cn.itcast.order.service;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class OrderService {

    private OrderMapper orderMapper;

//    private RestTemplate restTemplate;

    private UserClient userClient;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2. 利用RestTemplate发送http请求,查询用户
        // 使用了Eureka,域名就用微服务名
//        String url = "http://userservice/user/" + order.getUserId();

        // 3. 远程调用(通过http)
//        User user = restTemplate.getForObject(url, User.class);

        // 3. Feign RPC
        User user = userClient.findById(order.getUserId());

        order.setUser(user);
        // 4.返回
        return order;
    }
}
