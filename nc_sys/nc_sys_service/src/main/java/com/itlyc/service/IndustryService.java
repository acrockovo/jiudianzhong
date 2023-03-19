package com.itlyc.service;

import com.itlyc.sys.entity.CommonIndustry;

import java.util.List;

public interface IndustryService {
    // 查询行业列表
    List<CommonIndustry> queryIndustryList(String parentId);
}
