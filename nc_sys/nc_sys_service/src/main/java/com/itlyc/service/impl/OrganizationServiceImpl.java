package com.itlyc.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.OrganizationMapper;
import com.itlyc.service.OrganizationService;
import com.itlyc.sys.dto.DepartmentDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;

    /**
     * 查询当前企业下部门列表
     * @return
     */
    @Override
    public List<DepartmentDTO> queryDepartmentByTree() {
        Long companyId = UserHolder.getCompanyId();
        // 声明顶级部门parentId为0
        Long parentId = 0L;
        List<DepartmentDTO> departmentDTOS = organizationMapper.queryDepartmentByTree(companyId,parentId);
        if(!CollectionUtils.isEmpty(departmentDTOS)){
            queryChildDepartments(departmentDTOS);
            return departmentDTOS;
        }
        return null;
    }

    /**
     * 递归查询子部门
     * @param departmentDTOS
     */
    private void queryChildDepartments(List<DepartmentDTO> departmentDTOS) {
        for (DepartmentDTO departmentDTO : departmentDTOS) {
             Long id = departmentDTO.getId();
            List<DepartmentDTO> departmentDTOList = organizationMapper.queryDepartmentByTreeByParentId(id,UserHolder.getCompanyId());
            if(!CollectionUtils.isEmpty(departmentDTOList)){
                departmentDTO.setChildren(departmentDTOList);
                this.queryChildDepartments(departmentDTOList);
            }
        }
    }
}
