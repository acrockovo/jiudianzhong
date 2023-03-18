package com.itlyc.controller;

import com.itlyc.common.vo.Result;
import com.itlyc.service.impl.AddressBookConfigService;
import com.itlyc.sys.entity.AddressBookConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param addressBookConfig 请求对象
     * @return
     */
    @PostMapping("/company/config/contact")
    public Result saveAddressBook(@RequestBody AddressBookConfig addressBookConfig){
        addressBookConfigService.saveAddressBook(addressBookConfig);
        return Result.success("保存成功");
    }

    /**
     * 修改通讯录在APP中显示状态
     * @param addressBookConfig 请求对象
     * @return
     */
    @PutMapping("/company/config/contact/{id}/{type}")
    public Result updateContactConfig(@RequestBody AddressBookConfig addressBookConfig){
        addressBookConfigService.updateContactConfig(addressBookConfig);
        return Result.success();
    }
    /**
     * 根据id删除通讯录
     * @param id 通讯录id
     * @return
     */
    @DeleteMapping("/company/config/contact/{id}")
    public Result deleteContactConfig(@PathVariable Long id){
        addressBookConfigService.deleteContactConfig(id);
        return Result.success();
    }

}
