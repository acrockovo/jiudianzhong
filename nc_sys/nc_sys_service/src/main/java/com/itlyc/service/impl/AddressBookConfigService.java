package com.itlyc.service.impl;

import com.itlyc.sys.entity.AddressBookConfig;

import java.util.List;

public interface AddressBookConfigService {
    // 查询企业通讯录
    List<AddressBookConfig> queryAddressBookInfo();
    // 新增企业通讯录
    void saveAddressBook(AddressBookConfig addressBookConfig);
}
