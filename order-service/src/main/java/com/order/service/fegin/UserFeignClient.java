package com.order.service.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
@RequestMapping("/user")
public interface UserFeignClient {

    @GetMapping("/{id}")
    String getUser(@PathVariable("id") Long id);
}
