package com.edutech.courses.client;

import com.edutech.courses.controller.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users", url = "http://localhost:8080/edutech/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id);
}
