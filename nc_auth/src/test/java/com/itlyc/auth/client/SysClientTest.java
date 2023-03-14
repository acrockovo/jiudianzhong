package com.itlyc.auth.client;

import com.itlyc.common.vo.Result;
import com.itlyc.sys.client.SysClient;
import com.itlyc.sys.dto.CompanyUserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SysClientTest {

    @Autowired
    private SysClient sysClient;

    @Test
    public void test0() {

        Result<CompanyUserDTO> result = sysClient.querySysUser("13111111111");
        System.out.println(result);
    }
}
