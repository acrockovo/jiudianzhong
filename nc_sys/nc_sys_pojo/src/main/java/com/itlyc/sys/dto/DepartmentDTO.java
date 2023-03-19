package com.itlyc.sys.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itlyc.sys.entity.CompanyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO implements Serializable {

    //部门id
    private Long id;

    //部门名称
    private String name;

    //主管
    private CompanyUser manager;

    //子部门列表
    List<DepartmentDTO> children;

     //是否为企业
    private Boolean isCompany;

    //父部门
    private DepartmentDTO parent;

    //tree组件 节点名称取值 从 部门名称获取
    public String getLabel() {
        return name;
    }

}
