package com.itlyc.mapper;

import com.github.pagehelper.Page;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.entity.CompanyUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyUserMapper {
    // 根据手机号查询用户
    CompanyUser findSysUser(String userName);
    // 连接查询管理员用户
    List<CompanyUserDTO> queryCompanyAdmins(@Param("roleNameLike") String roleNameLike, @Param("companyId") Long companyId);
    // 根据企业id查询员工信息
    Page<CompanyUser> queryCompanyMembersByPage(Long companyId);
    // 更新角色列表
    int updateRoleById(CompanyUser companyUser);
    // 分页获取部门成员列表
    Page<CompanyUser> queryCompanyMembers(@Param("companyId") Long companyId, @Param("departmentId") Long departmentId, @Param("keyword") String keyword);
    // 批量插入用户
    void saveBatch(List<CompanyUser> companyUsers);
    // 员工申请加入企业时更新员工信息
    void updateCompanyUserById(CompanyUser companyUser);
}
