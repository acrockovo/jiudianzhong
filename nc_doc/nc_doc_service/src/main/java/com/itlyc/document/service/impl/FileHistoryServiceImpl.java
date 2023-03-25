package com.itlyc.document.service.impl;

import com.itlyc.document.entity.FileHistory;
import com.itlyc.document.mapper.FileHistoryMapper;
import com.itlyc.document.service.FileHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class FileHistoryServiceImpl implements FileHistoryService {

    private final Logger logger = LoggerFactory.getLogger(FileHistoryServiceImpl.class);

    @Resource
    private FileHistoryMapper fileHistoryMapper;
    /**
     * 新建文档操作记录
     * @param fileHistory 文档操作记录对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(FileHistory fileHistory) {
        logger.info("文档中心保存文档操作记录+{}", fileHistory);
        fileHistoryMapper.save(fileHistory);
    }
}
