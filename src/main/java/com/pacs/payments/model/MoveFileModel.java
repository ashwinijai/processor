package com.pacs.payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoveFileModel {
    private String fileContent;
    private String filePath;
    private String fileName;
}
