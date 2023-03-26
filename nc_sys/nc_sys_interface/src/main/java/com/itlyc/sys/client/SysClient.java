package com.itlyc.sys.client;

import com.itlyc.common.vo.Result;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "sys-service")
public interface SysClient {

    /**
     * 查询用户
     * @param userName 手机号
     * @return
     */
    @GetMapping("/user/query")
    Result<CompanyUserDTO> querySysUser(@RequestParam("username") String userName);

    /**
     * 根据员工ID集合查询员工集合
     * @param userIds
     * @return
     */
    @GetMapping("/companyUser/queryByIds")
    Result<List<CompanyUserDTO>> queryCompanyUserByIds(@RequestParam("userIds") List<Long> userIds);

    /**
     * 查询当前企业下所有员工
     * @return
     */
    @GetMapping("/companyUser/list")
    public Result<List<CompanyUserDTO>> queryAllCompanyUser();
}
