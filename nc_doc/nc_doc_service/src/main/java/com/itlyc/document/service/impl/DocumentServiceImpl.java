package com.itlyc.document.service.impl;

import com.itlyc.common.threadLocals.UserHolder;
import com.itlyc.common.util.CountUtil;
import com.itlyc.document.entity.File;
import com.itlyc.document.entity.FileHistory;
import com.itlyc.document.entity.Folder;
import com.itlyc.document.enums.DocumentEnum;
import com.itlyc.document.mapper.DocumentMapper;
import com.itlyc.document.service.DocumentService;
import com.itlyc.document.service.FileHistoryService;
import com.itlyc.document.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Resource
    private DocumentMapper documentMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileHistoryService fileHistoryService;

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

    /**
     * 新建文档
     * @param file 文档对象
     */
    @Override
    public void saveFile(File file) {
//1.设置文档相关属性
        Long userId = UserHolder.getUserId();
        file.setCompanyUserId(userId);
        file.setCompanyId(UserHolder.getCompanyId());
        file.setFileSize(CountUtil.getSize(file.getContent().length()));
        file.setUpdateUserId(userId);
        file.setStatus(DocumentEnum.FILE_STATUS_WAIT_APPROVE.getVal());
        fileService.save(file);

        //2.保存文档操作记录
        FileHistory fileHistory = new FileHistory();
        fileHistory.setFileId(file.getId());
        fileHistory.setFileName(file.getName());
        fileHistory.setCompanyUserId(file.getCompanyUserId());
        fileHistory.setIfCreate(true);
        fileHistory.setContent(file.getContent());
        fileHistoryService.save(fileHistory);
    }
}
