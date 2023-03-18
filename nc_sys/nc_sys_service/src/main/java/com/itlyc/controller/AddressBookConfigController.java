package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.impl.AddressBookConfigService;
import com.itlyc.sys.entity.AddressBookConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 新增企业通讯录
     * @param addressBookConfig
     * @return
     */
    @PostMapping("/company/config/contact")
    public Result saveAddressBook(@RequestBody AddressBookConfig addressBookConfig){
        addressBookConfigService.saveAddressBook(addressBookConfig);
        return Result.success("保存成功");
    }

}
