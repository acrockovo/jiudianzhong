package com.itlyc.document.service;

import com.itlyc.document.entity.FileHistory;

public interface FileHistoryService {
    // 新建文档操作记录
    void save(FileHistory fileHistory);
}
