package com.itlyc.service;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.AddressBookConfigMapper;
import com.itlyc.service.impl.AddressBookConfigService;
import com.itlyc.sys.entity.AddressBookConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AddressBookConfigServiceImpl implements AddressBookConfigService {

    @Autowired
    private AddressBookConfigMapper addressBookConfigMapper;
    /**
     * 查询企业通讯录
     * @return
     */
    @Override
    public List<AddressBookConfig> queryAddressBookInfo() {
        List<AddressBookConfig> addressBookConfigList = addressBookConfigMapper.queryAddressBookInfo(UserHolder.getCompanyId());
        // 如果该企业通讯录为空，则自动生成一个默认的通信录到库中
        if(CollectionUtils.isEmpty(addressBookConfigList)) {
            String[] items = new String[]{"部门", "职位", "姓名", "工号", "手机号", "邮箱", "座机", "办公地点", "备注", "入职时间"};
            for (String name : items) {
                AddressBookConfig companyContactConfig = new AddressBookConfig();
                companyContactConfig.setType("fixed");
                companyContactConfig.setName(name);
                companyContactConfig.setStatus(true);
                companyContactConfig.setFieldType("string");
                companyContactConfig.setCompanyId(UserHolder.getCompanyId());
                addressBookConfigList.add(companyContactConfig);
            }
            addressBookConfigMapper.insert(addressBookConfigList);
        }
        return addressBookConfigList;
    }
}
