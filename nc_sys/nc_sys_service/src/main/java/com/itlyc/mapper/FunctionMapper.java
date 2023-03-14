package com.itlyc.mapper;

import com.itlyc.sys.entity.Function;

import java.util.List;

public interface FunctionMapper {
    // 查询权限列表
    List<Function> findFunctionByIds(List<String> ids);
}
