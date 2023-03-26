package com.itlyc.document.service.impl;

import com.itlyc.common.exception.advice.NcException;
import com.itlyc.common.exception.enums.ResponseEnum;
import com.itlyc.document.entity.File;
import com.itlyc.document.mapper.FileMapper;
import com.itlyc.document.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Resource
    private FileMapper fileMapper;
    /**
     * 新建文档
     * @param file 文档对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(File file) {
        logger.info("文档中心保存文档+{}", file);
        fileMapper.save(file);
    }

    /**
     * 当前企业中 当前文件夹下 公共读 公共读写  作者 协作者 文档  状态为审核通过
     * @param companyId 企业id
     * @param all_read 公共读
     * @param all_read_write 公共读写
     * @param parentFolderId 文件夹id
     * @param keyword 关键字
     * @return
     */
    @Override
    public List<File> queryCompanyFiles(Long companyId, Long all_read, Long all_read_write, Long parentFolderId, Long userId, String keyword) {
        return fileMapper.queryCompanyFiles(companyId, all_read, all_read_write, parentFolderId, userId, keyword);
    }
}
