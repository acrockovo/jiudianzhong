package com.itlyc.document.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.mapper.DocumentMapper;
import com.itlyc.document.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Resource
    private DocumentMapper documentMapper;

    /**
     * 保存文件夹
     * @param folder 文件夹对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFolder(Folder folder) {
        folder.setCompanyId(UserHolder.getCompanyId());
        folder.setOrder(0);
        folder.setCompanyUserId(UserHolder.getUserId());
        logger.info("文档中心保存文件夹+{}",folder);
        documentMapper.saveFolder(folder);
    }
}
