package com.itlyc.service;

import com.itlyc.sys.entity.Function;

import java.util.List;

public interface FunctionService {
    // 查询权限列表
    List<Function> findFunctionByIds(List<String> functionIdList);
}
