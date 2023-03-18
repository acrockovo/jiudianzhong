package com.itlyc.mapper;

import com.itlyc.sys.entity.AddressBookConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressBookConfigMapper {
    // 查询企业通讯录
    List<AddressBookConfig> queryAddressBookInfo(Long companyId);
    // 保存企业通讯录
    void insert(List<AddressBookConfig> addressBookConfigList);
}
