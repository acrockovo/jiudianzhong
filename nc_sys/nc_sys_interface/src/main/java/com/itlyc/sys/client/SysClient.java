package com.itlyc.sys.client;

import com.itlyc.common.vo.Result;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sys-service")
public interface SysClient {

    /**
     * 查询用户
     * @param userName 手机号
     * @return
     */
    @GetMapping("/user/query")
    Result<CompanyUserDTO> querySysUser(@RequestParam("username") String userName);
}
