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
}
