package com.hrm.hrm.Bean;

/**
 * Created by Ishan Puranik on 15/11/2017.
 */

public class DocBean {
    String documentName;
    String remarks;
    String filePath;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public DocBean(String documentName, String remarks, String filePath) {
        this.documentName = documentName;
        this.remarks = remarks;
        this.filePath = filePath;
    }
}
