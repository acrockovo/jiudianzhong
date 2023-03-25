package com.itlyc.controller;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.vo.Result;
import com.itlyc.service.CompanyUserService;
import com.itlyc.sys.dto.CompanyUserAdminDTO;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.UserJoinCompanyDTO;
import com.itlyc.sys.entity.CompanyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyUserController {

    @Autowired
    private CompanyUserService companyUserService;

    /**
     * 查询用户
     * @param userName 手机号
     * @return
     */
    @GetMapping("/user/query")
    public Result<CompanyUserDTO> querySysUser(@RequestParam("username") String userName){
        return Result.success(companyUserService.querySysUser(userName));
    }

    /**
     * 查询当前企业下所有的管理员用户
     * @return
     */
    @GetMapping("/company/subAdmin")
    public Result<List<CompanyUserDTO>> queryCompanyAdmins(){
        return Result.success(companyUserService.queryCompanyAdmins());
    }

    /**
     * 新增子管理员
     * @param companyUserAdminDTO 参数对象
     * @return
     */
    @PostMapping("/company/subAdmin")
    public Result addSubAdmin(@RequestBody CompanyUserAdminDTO companyUserAdminDTO){
        return Result.success("新增成功",companyUserService.addSubAdmin(companyUserAdminDTO));
    }

    @GetMapping("/sms/code")
    public Result sendSmsCode(@RequestParam("mobile") String mobile){
        return Result.success("验证码发送成功");
    }

    /**
     * 移动端：用户注册
     * @return
     */
    @PostMapping("/user/register")
    public Result<Long> register(CompanyUser companyUser, @RequestParam("checkcode") String checkcode) throws Exception {
        return Result.success(companyUserService.register(companyUser, checkcode));
    }

    /**
     * 给管理员推送消息，申请加入企业
     * @param userJoinCompanyDTO
     * @return
     */
    @PostMapping("/company/applyJoinCompany")
    public Result applyJoinCompany(@RequestBody UserJoinCompanyDTO userJoinCompanyDTO){
        companyUserService.applyJoinCompany(userJoinCompanyDTO);
        return Result.successMessage("申请已提交，请等待审核结果！");
    }

    /**
     * 是否同意加入企业
     * @param applyUserId 申请加入企业用户ID
     * @param approved 是否同意   同意：true    拒绝：false
     * @param notifyMsgId 推送记录ID
     * @return
     */
    @PostMapping("/company/allowedJoinCompany")
    public Result allowedJonCompany(@RequestParam("applyUserId") Long applyUserId, @RequestParam("approved") Boolean approved, @RequestParam(value = "remark", required = false) String remark, @RequestParam("notifyMsgId") String notifyMsgId){
        companyUserService.allowedJonCompany(applyUserId, approved, remark, notifyMsgId);
        return Result.success();
    }
}
