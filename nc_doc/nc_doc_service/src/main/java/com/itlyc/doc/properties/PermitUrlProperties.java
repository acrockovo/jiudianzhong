package com.itlyc.doc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("nc.security")
public class PermitUrlProperties {

    private List<String> permitUrlList;

}
