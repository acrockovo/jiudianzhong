package com.itlyc.document.dto;

import com.itlyc.document.entity.File;
import com.itlyc.document.entity.Folder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 某个文件夹下包含 子文件夹 以及 文档DTO
 */
@Data
public class DocumentDTO implements Serializable {

    /**
     * 文件夹集合
     */
    List<Folder> folders = new ArrayList<>();

    /**
     * 文档集合
     */
    List<File> files = new ArrayList<>();

}
