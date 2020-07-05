package com.order.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable("id") Long id) {
        return "Order:" + id;
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId) {
        String user = restTemplate.getForEntity("http://127.0.0.1:8080/user/" + userId, String.class).getBody();
        String order = getOrder(orderId);
        return String.format("用户%s,对商品%s进行了下单", user, order);
    }
}
