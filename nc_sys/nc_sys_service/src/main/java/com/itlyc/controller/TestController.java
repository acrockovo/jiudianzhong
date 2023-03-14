package com.itlyc.controller;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.vo.Result;
import com.itlyc.service.UserService;
import com.itlyc.sys.entity.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public Result<String> hello(){
        return Result.success("hello sys");
    }

    @PreAuthorize("hasAnyAuthority('p1')")
    @PostMapping("/test/user")
    public Result saveUser(@RequestBody UserDomain user){

        if (user.getAge() == null){
            throw new NcException(ResponseEnum.ERROR);
        }
        return Result.success("保存成功",userService.saveUser(user));
    }
}
