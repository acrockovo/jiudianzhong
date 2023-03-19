package com.itlyc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.vo.PageResult;
import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.mapper.OrganizationMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.OrganizationService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;
import com.itlyc.sys.entity.CompanyUser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private CompanyUserMapper companyUserMapper;

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

    /**
     * 分页获取部门成员列表
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param departmentId 部门ID 0：未分配部门员工  其他值：查询指定部门员工
     * @param keyword 查询关键字
     * @throws Exception
     */
    @Override
    public PageResult<CompanyUserDTO> queryCompanyMembers(Integer pageNum, Integer pageSize, Long departmentId, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        Long companyId = UserHolder.getCompanyId();
        Page<CompanyUser> page = companyUserMapper.queryCompanyMembers(companyId, departmentId, keyword);
        if(!CollectionUtils.isEmpty(page.getResult())){
            List<CompanyUserDTO> companyUserDTOList = BeanHelper.copyWithCollection(page.getResult(), CompanyUserDTO.class);
            return new PageResult<>(page.getTotal(), (long)page.getPages(), companyUserDTOList);
        }
        return null;
    }
}
