package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class OrderService {


    private OrderMapper orderMapper;

    private RestTemplate restTemplate;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        // 2. 利用RestTemplate发送http请求,查询用户
        String url = "http://localhost:8081/user/" + order.getUserId();

        // 3. 远程调用(通过http)
        User user = restTemplate.getForObject(url, User.class);

        order.setUser(user);
        // 4.返回
        return order;
    }
}
