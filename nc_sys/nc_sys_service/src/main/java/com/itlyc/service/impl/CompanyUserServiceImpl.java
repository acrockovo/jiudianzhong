package com.itlyc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.vo.PageResult;
import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.FunctionService;
import com.itlyc.service.RoleService;
import com.itlyc.sys.dto.CompanyUserAdminDTO;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.UserJoinCompanyDTO;
import com.itlyc.sys.entity.CompanyUser;
import com.itlyc.sys.entity.Function;
import com.itlyc.sys.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

    @Resource
    private CompanyUserMapper companyUserMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FunctionService functionService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 根据手机号查询用户
     * @param userName 手机号
     * @return
     */
    @Override
    public CompanyUserDTO querySysUser(String userName) {

        CompanyUser companyUser = companyUserMapper.findSysUser(userName);
        CompanyUserDTO companyUserDTO = new CompanyUserDTO();
        try {
            BeanUtils.copyProperties(companyUser,companyUserDTO);
        } catch (BeansException e) {
            throw new NcException(ResponseEnum.ERROR);
        }
        // 获取角色列表
        String roleIds = companyUserDTO.getRoleIds();
        if(StringUtils.isNotBlank(roleIds)){
            String[] roleIdArr = StringUtils.split(roleIds, ",");
            List<String> roleIdList = Arrays.asList(roleIdArr);
            // 查询角色列表
            List<Role> roleList = roleService.findRoleByIds(roleIdList);
            companyUserDTO.setSysRoles(roleList);

            List<Function> allFunctionList = new ArrayList<>();
            for (Role role : roleList) {
                String functionIds = role.getFunctionIds();
                if(StringUtils.isNotBlank(functionIds)){
                    List<String> functionIdList = Arrays.asList(StringUtils.split(functionIds, ","));
                    // 查询权限列表
                    List<Function> functionList = functionService.findFunctionByIds(functionIdList);
                    allFunctionList.addAll(functionList);
                }
            }
            // 对权限列表尽心去重
            allFunctionList = allFunctionList.stream().distinct().collect(Collectors.toList());
            companyUserDTO.setSysFunctions(allFunctionList);
        }

        return companyUserDTO;
    }

    /**
     * 查询当前企业下所有的管理员用户
     * @return
     */
    @Override
    public List<CompanyUserDTO> queryCompanyAdmins() {
        // 管理员
        String roleNameLike = "ROLE_ADMIN_%";
        Long companyId = UserHolder.getCompanyId();
        List<CompanyUserDTO> companyUserDTOList = companyUserMapper.queryCompanyAdmins(roleNameLike, companyId);
        if(!CollectionUtils.isEmpty(companyUserDTOList)){
            for (CompanyUserDTO companyUserDTO : companyUserDTOList) {
                List<Role> roleList = roleService.findRoleByIds(Arrays.asList(StringUtils.split(companyUserDTO.getRoleIds(),",")));
                companyUserDTO.setSysRoles(roleList);
            }
        }
        return companyUserDTOList;
    }

    /**
     * 分页获取部门成员列表
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return
     */
    @Override
    public PageResult<CompanyUserDTO> queryCompanyMembersByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Long companyId = UserHolder.getCompanyId();
        Page<CompanyUser> page = companyUserMapper.queryCompanyMembersByPage(companyId);
        List<CompanyUser> companyUserList = page.getResult();
        if(!CollectionUtils.isEmpty(companyUserList)){
            List<CompanyUserDTO> companyUserDTOList = BeanHelper.copyWithCollection(page.getResult(), CompanyUserDTO.class);
            return new PageResult<>(page.getTotal(), (long) page.getPages(),companyUserDTOList);
        }
        return null;
    }

    /**
     * 新增子管理员
     * @param companyUserAdminDTO 参数对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addSubAdmin(CompanyUserAdminDTO companyUserAdminDTO) {
        // 对角色进行去重操作
        List<String> roleIds = companyUserAdminDTO.getRoleIds();
        List<Role> roleList = roleService.findRoleByIds(roleIds);
        // 将集合中的角色名称进行拼接
        String roleDesc = roleList.stream().map(Role::getRoleDesc).collect(Collectors.joining(","));

        CompanyUser companyUser = new CompanyUser();
        String roleIdStr = roleIds.stream().collect(Collectors.joining(","));
        companyUser.setRoleIds(roleIdStr);
        companyUser.setRoleDesc(roleDesc);
        companyUser.setId(companyUserAdminDTO.getUserId());
        int i = companyUserMapper.updateRoleById(companyUser);
        return i;
    }

    /**
     * 移动端用户注册
     * @param companyUser 用户对象
     * @param checkcode 验证码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(CompanyUser companyUser, String checkcode) {
        CompanyUser sysUser = companyUserMapper.findSysUser(companyUser.getMobile());
        if(sysUser != null){
            throw new NcException(ResponseEnum.USER_MOBILE_EXISTS);
        }
        if(!StringUtils.equals(checkcode,"123456")){
            throw new NcException(ResponseEnum.INVALID_VERIFY_CODE);
        }
        companyUser.setPassword(passwordEncoder.encode(companyUser.getPassword()));
        List<CompanyUser> companyUserList = new ArrayList<>();
        companyUserList.add(companyUser);
        companyUserMapper.saveBatch(companyUserList);
        return companyUser.getId();
    }

    /**
     * 给管理员推送消息，申请加入企业
     * @param userJoinCompanyDTO
     * @return
     */
    @Override
    public void applyJoinCompany(UserJoinCompanyDTO userJoinCompanyDTO) {

    }
}
