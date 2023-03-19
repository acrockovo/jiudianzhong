package com.itlyc.mapper;

import com.itlyc.sys.entity.CommonIndustry;

import java.util.List;

public interface IndustryMapper {
    // 查询行业列表
    List<CommonIndustry> queryIndustryList(String parentId);
}
