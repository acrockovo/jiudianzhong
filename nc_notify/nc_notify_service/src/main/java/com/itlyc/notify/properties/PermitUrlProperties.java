package com.itlyc.notify.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: itlyc
 * @create: 2021-07-25 09:47
 */
@Data
@Component
@ConfigurationProperties("nc.security")
public class PermitUrlProperties {

    private List<String> permitUrlList;

}
