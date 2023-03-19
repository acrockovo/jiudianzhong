package com.itlyc.service.impl;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.AddressBookConfigMapper;
import com.itlyc.service.AddressBookConfigService;
import com.itlyc.sys.entity.AddressBookConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
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

    /**
     * 新增企业通讯录
     * @param addressBookConfig
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAddressBook(AddressBookConfig addressBookConfig) {
        Long companyId = UserHolder.getCompanyId();
        addressBookConfig.setCompanyId(companyId);
        AddressBookConfig addressBookConfig1 = addressBookConfigMapper.queryAddressBookInfoByIdAndName(addressBookConfig);
        if(addressBookConfig1 != null){
            throw new NcException(ResponseEnum.INVALID_PARAM_ERROR);
        }
        AddressBookConfig companyContactConfig = new AddressBookConfig();
        companyContactConfig.setType("dynamic");
        companyContactConfig.setName(addressBookConfig.getName());
        companyContactConfig.setStatus(false);
        companyContactConfig.setFieldType("string");
        companyContactConfig.setCompanyId(companyId);
        boolean b = addressBookConfigMapper.save(companyContactConfig);
        if(!b){
            throw new NcException(ResponseEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 修改通讯录在APP中显示状态
     * @param addressBookConfig 请求对象
     * @return
     */
    @Override
    public void updateContactConfig(AddressBookConfig addressBookConfig) {
        addressBookConfig.setCompanyId(UserHolder.getCompanyId());
        boolean b = addressBookConfigMapper.updateContactConfig(addressBookConfig);
        if(!b){
            throw new NcException(ResponseEnum.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 根据id删除通讯录
     * @param id 通讯录id
     */
    @Override
    public void deleteContactConfig(Long id) {
        Long companyId = UserHolder.getCompanyId();
        addressBookConfigMapper.deleteContactConfig(companyId,id);
    }
}
