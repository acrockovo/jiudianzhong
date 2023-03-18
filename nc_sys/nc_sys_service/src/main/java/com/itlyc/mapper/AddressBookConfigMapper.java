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
    // 根据企业id和通讯录名称查询
    AddressBookConfig queryAddressBookInfoByIdAndName(AddressBookConfig addressBookConfig);
    // 新增企业通讯录
    boolean save(AddressBookConfig companyContactConfig);
    // 修改通讯录在APP中显示状态
    boolean updateContactConfig(AddressBookConfig addressBookConfig);
    // 根据通讯录id删除
    void deleteContactConfig(Long companyId, Long id);
}
