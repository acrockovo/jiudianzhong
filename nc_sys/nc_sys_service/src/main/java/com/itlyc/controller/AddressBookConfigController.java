package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.impl.AddressBookConfigService;
import com.itlyc.sys.entity.AddressBookConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressBookConfigController {

    @Autowired
    private AddressBookConfigService addressBookConfigService;

    /**
     * 查询企业通讯录
     * @return
     */
    @GetMapping("/company/config/contact")
    public Result<List<AddressBookConfig>> queryAddressBookInfo(){
        return Result.success("查询企业通讯录成功",addressBookConfigService.queryAddressBookInfo());

    }

}
