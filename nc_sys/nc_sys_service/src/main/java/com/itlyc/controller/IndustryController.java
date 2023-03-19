package com.itlyc.controller;


import com.itlyc.common.vo.Result;
import com.itlyc.service.IndustryService;
import com.itlyc.sys.entity.CommonIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    /**
     * 查询行业列表
     * @param parentId 上级行业ID
     * @return
     */
    @GetMapping("/industry/list")
    public Result<List<CommonIndustry>> queryIndustryList(@RequestParam(value = "parentId", required = false) String parentId){
        return Result.success(industryService.queryIndustryList(parentId));
    }

}
