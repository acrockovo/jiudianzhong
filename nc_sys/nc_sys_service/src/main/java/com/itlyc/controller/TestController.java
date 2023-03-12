package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Result<String> hello(){
        return Result.success("hello sys");
    }
}
