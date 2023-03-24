package com.itlyc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.BeanHelper;
import com.itlyc.common.util.DateTimeUtil;
import com.itlyc.common.vo.PageResult;
import com.itlyc.mapper.CompanyUserMapper;
import com.itlyc.mapper.OrganizationMapper;
import com.itlyc.service.CompanyUserService;
import com.itlyc.service.OrganizationService;
import com.itlyc.sys.dto.CompanyUserDTO;
import com.itlyc.sys.dto.DepartmentDTO;
import com.itlyc.sys.dto.MemberExcelDTO;
import com.itlyc.sys.dto.MemberExcelRequest;
import com.itlyc.sys.entity.CompanyUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.poi.ss.usermodel.Cell.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyUserServiceImpl.class);

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
            logger.info("分页获取部门成员列表+{}",companyUserDTOList);
            return new PageResult<>(page.getTotal(), (long)page.getPages(), companyUserDTOList);
        }
        return null;
    }

    /**
     * PC:通讯录Json导入导入
     * @param memberExcelChineseDTOs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void excelImport(MemberExcelRequest memberExcelChineseDTOs) {
        if (memberExcelChineseDTOs != null && memberExcelChineseDTOs.getXlsxJson() != null && memberExcelChineseDTOs.getXlsxJson().length > 0) {
            List<MemberExcelDTO> memberExcelList = Arrays.stream(memberExcelChineseDTOs.getXlsxJson()).map(memberExcelChinese -> {
                MemberExcelDTO memberExcel = new MemberExcelDTO();
                memberExcel.setAddress(memberExcelChinese.get办公地点());
                memberExcel.setDepartmentName(memberExcelChinese.get部门());
                memberExcel.setEmail(memberExcelChinese.get邮箱());
                memberExcel.setEnable(memberExcelChinese.get激活状态());
                memberExcel.setIfManager(memberExcelChinese.get是否部门主管());
                memberExcel.setMobile(memberExcelChinese.get手机号());
                memberExcel.setPost(memberExcelChinese.get职位());
                memberExcel.setRemark(memberExcelChinese.get备注());
                memberExcel.setRoleDesc(memberExcelChinese.get角色());
                memberExcel.setTimeEntry(memberExcelChinese.get入职时间());
                memberExcel.setWorkNumber(memberExcelChinese.get工号());
                memberExcel.setUsername(memberExcelChinese.get姓名());
                return memberExcel;
            }).collect(Collectors.toList());
            List<CompanyUser> companyUsers = new ArrayList<>();
            for (MemberExcelDTO memberExcel : memberExcelList) {
                //判断用户手机号是否重复  邮箱  工号
                CompanyUser result = companyUserMapper.findSysUser(memberExcel.getMobile());
                if (result != null) {
                    continue;
                }
                CompanyUser companyUser = new CompanyUser();
                companyUser.setTimeEntry(LocalDateTime.ofInstant((DateTimeUtil.strToDate(memberExcel.getTimeEntry(),
                        DateTimeUtil.TIME_FORMAT_2)).toInstant(), ZoneId.systemDefault()));
                companyUser.setCompanyId(UserHolder.getCompanyId());
                companyUser.setCompanyName(UserHolder.getUserName());
                companyUser.setPost(memberExcel.getPost());
                companyUser.setMobile(memberExcel.getMobile());
                companyUser.setWorkNumber(memberExcel.getWorkNumber());
                companyUser.setRemark(memberExcel.getRemark());
                companyUser.setEmail(memberExcel.getEmail());
                companyUser.setOfficeAddress(memberExcel.getAddress());
                companyUser.setUsername(memberExcel.getUsername());
                companyUser.setCreateTime(LocalDateTime.now());
                companyUsers.add(companyUser);
            }
            if(!CollectionUtils.isEmpty(companyUsers)){
                logger.info("保存成功{}",companyUsers);
                companyUserMapper.saveBatch(companyUsers);
            }
        } else {
            throw new NcException(ResponseEnum.INVALID_PARAM_ERROR);
        }
    }

    /**
     * 服务端解析上传的Excel
     * @param file
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadExcel(MultipartFile file) throws IOException {
        //1.通过Workbook对象装载Excel文件 03版本：HSSFWorkbook     07：XSSFWorkbook
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        //2.通过Workbook获取标签页Sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        //3.通过标签页 遍历 获取每行记录 Row对象  --目的封装一个个员工CompanyUser对象
        ArrayList<CompanyUser> companyUsers = new ArrayList<>();
        for (Row row : sheet) {
            //忽略表头行
            if(row.getRowNum()==0){
                continue;
            }
            //4.通过Row行对象获取每个单元格值
            String mobile = getCellValue(row.getCell(1));
            if(StringUtils.isNotBlank(mobile)){
                CompanyUser result = companyUserMapper.findSysUser(mobile);
                if (result != null) {
                    continue;
                }
            }
            String userName = getCellValue(row.getCell(0));
            String post = getCellValue(row.getCell(2));
            String workNum = getCellValue(row.getCell(3));
            String email = getCellValue(row.getCell(4));
            String address = getCellValue(row.getCell(5));
            String hireDate = getCellValue(row.getCell(6));

            CompanyUser companyUser = new CompanyUser();
            companyUser.setOfficeAddress(address);
            companyUser.setEmail(email);
            companyUser.setEnable(true);
            companyUser.setMobile(mobile);
            companyUser.setPost(post);
            companyUser.setTimeEntry(LocalDateTime.now());
            companyUser.setWorkNumber(workNum);
            companyUser.setUsername(userName);

            companyUsers.add(companyUser);
        }
        if(!CollectionUtils.isEmpty(companyUsers)){
            companyUserMapper.saveBatch(companyUsers);
        }
    }

    //用于格式化十进制数字-#阿拉伯数字如果不存在就显示为空
    private static final DecimalFormat df = new DecimalFormat("#");


    /**
     * 获取单元格各类型值，返回字符串类型
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        String value = null;
        switch (cell.getCellType()) {
            case CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else{
                    //日期类型
                    value = DateTimeUtil.dateToStr(cell.getDateCellValue(), "yyyy-MM-dd");
                }
                break;
            case CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                value = cell.toString();
                break;
        }
        return value;
    }
}
