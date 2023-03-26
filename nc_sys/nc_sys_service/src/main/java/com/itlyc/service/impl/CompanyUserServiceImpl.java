package com.itlyc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlyc.common.constants.NotifyMessageConstant;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.vo.PageResult;
import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.notify.clent.NotifyMsgClient;
import com.itlyc.notify.dto.NotifyMessage;
import com.itlyc.service.CompanyService;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.FunctionService;
import com.itlyc.service.RoleService;
import com.itlyc.sys.dto.CompanyUserAdminDTO;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.UserJoinCompanyDTO;
import com.itlyc.sys.entity.Company;
import com.itlyc.sys.entity.CompanyUser;
import com.itlyc.sys.entity.Function;
import com.itlyc.sys.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.itlyc.common.constants.RocketMQConstants.TAGS.USER_APPLY_TAGS;
import static com.itlyc.common.constants.RocketMQConstants.TOPIC.PUSH_TOPIC_NAME;

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
    @Autowired
    private CompanyService companyService;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private NotifyMsgClient notifyMsgClient;
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
    @Transactional(rollbackFor = Exception.class)
    public void applyJoinCompany(UserJoinCompanyDTO userJoinCompanyDTO) {
        CompanyUser companyUser = BeanHelper.copyProperties(userJoinCompanyDTO,CompanyUser.class);
        if(companyUser != null){
            // 封装信息，更新员工信息
            companyUser.setUsername(userJoinCompanyDTO.getUserName());
            companyUser.setId(userJoinCompanyDTO.getUserId());
            companyUser.setEnable(false);

            Long companyId = userJoinCompanyDTO.getCompanyId();
            logger.info("员工申请加入企业时查询公司信息+{}",companyId);
            Company company = companyService.findCompanyByCompanyId(companyId);
            if(company != null){
                companyUser.setCompanyId(companyId);
                companyUser.setCompanyName(company.getName());
            }
            logger.info("员工申请加入企业更新员工信息+{}",companyUser);
            companyUserMapper.updateCompanyUserById(companyUser);
            // 查询当前企业管理员
            CompanyUserDTO currentAdmin = this.getCurrentAdmin(companyId);
            logger.info("当前企业管理员为+{}",currentAdmin);

            NotifyMessage notifyMessage = new NotifyMessage();
            notifyMessage.setApplyUserId(userJoinCompanyDTO.getUserId());
            notifyMessage.setApplyUsername(userJoinCompanyDTO.getUserName());
            //2.2.3 设置接收者信息
            notifyMessage.setApproveUserId(currentAdmin.getId());
            notifyMessage.setApproveUsername(currentAdmin.getUsername());
            //3.2.4 推送消息内容 标题
            notifyMessage.setTitle("有新员工申请加入企业");
            notifyMessage.setContent(userJoinCompanyDTO.getUserName() + " 申请加入：" + currentAdmin.getCompanyName() + ",请及时审批！备注:" + userJoinCompanyDTO.getApplyReason());
            notifyMessage.setTargets(Arrays.asList(currentAdmin.getMobile()));
            notifyMessage.setCreateTime(new Date().getTime());
            notifyMessage.setCompanyId(userJoinCompanyDTO.getCompanyId());
            notifyMessage.setMessageType(NotifyMessageConstant.COMPANY_APPLY);

            //4.调用RocketMQTemplate对象发送消息到MQ
            //参数一：话题名称（一级分类）:标签名称（二级分类）
            logger.info("{}，申请加入企业。{}", userJoinCompanyDTO.getUsername(), notifyMessage.toString());
            rocketMQTemplate.convertAndSend(PUSH_TOPIC_NAME+":"+ USER_APPLY_TAGS, notifyMessage);
        }
    }

    /**
     * 查询当前企业管理员
     * @param companyId 企业id
     * @return
     */
    private CompanyUserDTO getCurrentAdmin(Long companyId) {
        if(companyId == null){
           companyId = UserHolder.getCompanyId();
        }
        String roleNameLike = "ROLE_ADMIN_%";
        List<CompanyUserDTO> companyUserDTOList = companyUserMapper.queryCompanyAdmins(roleNameLike, companyId);
        return companyUserDTOList.get(0);
    }

    /**
     * 是否同意加入企业
     * @param applyUserId 申请用户ID
     * @param approved 审核状态
     * @param notifyMsgId 推送记录ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allowedJonCompany(Long applyUserId, Boolean approved, String remark, String notifyMsgId) {
        //1.更新用户（申请者用户）认证状态
        CompanyUser companyUser = new CompanyUser();
        companyUser.setId(applyUserId);
        companyUser.setEnable(approved);
        companyUserMapper.updateCompanyUserById(companyUser);

        //2.远程调用推送微服务 更新 推送记录状态
        String status = approved ? "1" : "2";
        notifyMsgClient.updateNotifyMsgStatus(notifyMsgId, status);
    }

    /**
     * 根据员工ID集合查询员工集合
     * @param companyUserIds
     * @return
     */
    @Override
    public List<CompanyUserDTO> queryCompanyUserByIds(List<Long> companyUserIds) {
        return companyUserMapper.queryCompanyUserByIds(companyUserIds);
    }

    /**
     * 查询当前企业下所有员工
     * @return
     */
    @Override
    public List<CompanyUserDTO> queryAllCompanyUser() {
        List<CompanyUserDTO> companyUserDTOList = companyUserMapper.queryAllCompanyUser(UserHolder.getCompanyId());
        if(CollectionUtils.isEmpty(companyUserDTOList)){
            throw new NcException(ResponseEnum.ERROR);
        }
        return companyUserDTOList;
    }
}
