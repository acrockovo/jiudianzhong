package com.itlyc.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @desc 用于获取前端解析excel文件中单元格中的数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberExcelChineseDTO {

    private String 姓名;

    private String 手机号;

    private String 部门;

    private String 职位;

    private String 工号;

    private String 是否部门主管;

    private String 邮箱;

    private String 办公地点;

    private String 备注;

    private String 入职时间;

    private String 激活状态;

    private String 角色;

}
