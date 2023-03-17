package com.itlyc.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.FunctionMapper;
import com.itlyc.service.FunctionService;
import com.itlyc.sys.entity.Function;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Resource
    private FunctionMapper functionMapper;

    /**
     * 查询权限列表
     * @param functionIdList 权限id集合
     * @return
     */
    @Override
    public List<Function> findFunctionByIds(List<String> functionIdList) {
        return functionMapper.findFunctionByIds(functionIdList);
    }

    /**
     * 根据企业id查询该公司下所有的权限列表
     * @return
     */
    @Override
    public List<Function> findFunctionByCompanyId() {
        return functionMapper.findFunctionByCompanyId(UserHolder.getCompanyId());
    }
}
