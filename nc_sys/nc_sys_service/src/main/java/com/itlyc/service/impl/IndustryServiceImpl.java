package com.itlyc.service.impl;

import com.itlyc.mapper.IndustryMapper;
import com.itlyc.service.IndustryService;
import com.itlyc.sys.entity.CommonIndustry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndustryServiceImpl implements IndustryService {

    @Resource
    private IndustryMapper industryMapper;
    /**
     * 查询行业列表
     * @param parentId 上级行业id
     * @return
     */
    @Override
    public List<CommonIndustry> queryIndustryList(String parentId) {
        return industryMapper.queryIndustryList(parentId);
    }
}
