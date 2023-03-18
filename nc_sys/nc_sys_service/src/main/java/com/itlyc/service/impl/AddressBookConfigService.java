package com.itlyc.service.impl;

import com.itlyc.sys.entity.AddressBookConfig;

import java.util.List;

public interface AddressBookConfigService {
    // 查询企业通讯录
    List<AddressBookConfig> queryAddressBookInfo();
    // 新增企业通讯录
    void saveAddressBook(AddressBookConfig addressBookConfig);
    // 修改通讯录在APP中显示状态
    void updateContactConfig(AddressBookConfig addressBookConfig);
    // 根据id删除通讯录
    void deleteContactConfig(Long id);
}
