package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.FunctionService;
import com.itlyc.sys.entity.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    /**
     * 根据企业id查询该公司下所有的权限列表
     * @return
     */
    @GetMapping("/company/config/permissions")
    public Result<List<Function>> queryFunctionsByCompanyId(){
        return Result.success(functionService.findFunctionByCompanyId());
    }
}
