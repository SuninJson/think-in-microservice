package com.order.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @LoadBalanced
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable("id") Long id) {
        return "Order:" + id;
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-service");
        //获取服务实例
        String serviceUrl = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
        //执行Get请求
        String user = restTemplate.getForEntity(serviceUrl + "/user/" + userId, String.class).getBody();
        String order = getOrder(orderId);
        return String.format("用户%s,对商品%s进行了下单", user, order);
    }

    @PostMapping("/cancelPlace")
    public String cancelPlace(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId) {
        String serviceUrl = "http://user-service";
        String user = loadBalancedRestTemplate.getForEntity(serviceUrl + "/user/" + userId, String.class).getBody();
        String order = getOrder(orderId);
        return String.format("用户%s,对商品%s进行了下单", user, order);
    }


}
