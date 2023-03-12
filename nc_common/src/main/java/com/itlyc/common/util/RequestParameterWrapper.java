package com.itlyc.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 包装请求参数
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    /**
     * 存放请求参数
     */
    private Map<String, String[]> params = new HashMap<String, String[]>();

    /**
     * 从请求对象中获取请求参数，放入params中
     *
     * @param request
     */
    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
    }


    /**
     * 添加参数
     *
     * @param extraParams
     */
    public void addParameters(Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 通过参数名称获取参数值
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}
