package com.itlyc.service.impl;

import com.itlyc.aliyun.template.AliyunOssTemplate;
import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.mapper.CompanyMapper;
import com.itlyc.service.CompanyService;
import com.itlyc.sys.entity.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private AliyunOssTemplate ossTemplate;


    /**
     * 允许上传的图片类型  application/octet-stream：app端提交文件类型
     */
    private static final List<String> allowTypeList = Arrays.asList("image/jpeg", "image/bmp", "image/png", "application/octet-stream");

    /**
     * 查询当前登录用户企业
     * @return
     */
    @Override
    public Company queryCurrentCompany() {
        Long companyId = UserHolder.getCompanyId();
        return companyMapper.queryCurrentCompany(companyId);
    }

    /**
     * uploadLogo
     * @param file 文件对象
     * @return
     */
    @Override
    public String uploadLogo(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        // 判断上传文件类型是否匹配，根据后缀名获取到文件MIME类型
        if(!allowTypeList.contains(contentType)){
            log.error("企业上传logo失败,上传文件类型不匹配");
            throw new NcException(ResponseEnum.INVALID_FILE_TYPE);
        }
        // 判断文件是否是图片
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage == null){
                log.error("企业上传logo失败,上传文件类型不匹配");
                throw new NcException(ResponseEnum.INVALID_FILE_TYPE);
            }
        } catch (IOException e) {
            log.error("企业上传logo失败" + e.getMessage(), e);
            throw new NcException(ResponseEnum.INVALID_FILE_TYPE);
        }
        String url = null;
        try {
            url = ossTemplate.uploadImage(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            log.error("oss上传失败" + e.getMessage(), e);
            throw new NcException(ResponseEnum.ERROR);
        }

        return url;
    }

    /**
     * 修改企业信息
     * @param company 企业信息对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCompany(Company company) {
        Long companyId = UserHolder.getCompanyId();
        company.setId(companyId);
        log.info("更新企业信息传入mapper对象-{}", company);
        companyMapper.updateCompany(company);
    }

    /**
     * 查询企业列表
     * @param keyword 关键字
     * @param industryId 行业id
     * @return
     */
    @Override
    public List<Company> queryCompanyList(String keyword, String industryId) {

        if(StringUtils.isBlank(keyword) && StringUtils.isBlank(industryId)){
            throw new NcException(ResponseEnum.INVALID_PARAM_ERROR);
        }
        List<Company> companyList = companyMapper.queryCompanyList(keyword, industryId);
        if(CollectionUtils.isEmpty(companyList)){
            throw new NcException(ResponseEnum.COMPANY_NOT_FOUND);
        }
        return companyList;
    }

    /**
     * 根据id查询当前企业
     * @param companyId 企业id
     * @return
     */
    @Override
    public Company findCompanyByCompanyId(Long companyId) {
        return companyMapper.queryCurrentCompany(companyId);
    }
}
