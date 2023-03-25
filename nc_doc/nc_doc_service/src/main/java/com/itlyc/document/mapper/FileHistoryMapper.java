package com.itlyc.document.mapper;

import com.itlyc.document.entity.FileHistory;

public interface FileHistoryMapper {
    // 新建文档操作记录
    void save(FileHistory fileHistory);
}
