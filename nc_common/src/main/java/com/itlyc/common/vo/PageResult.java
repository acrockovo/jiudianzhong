package com.itlyc.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询得到对象
 *
 * @param <T>
 * @author itheima
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 当前页数据
     */
    private List<T> rows;

}
