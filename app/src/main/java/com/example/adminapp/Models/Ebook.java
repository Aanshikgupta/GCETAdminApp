package com.example.adminapp.Models;

public class Ebook {
    String key,pdfUrl,pdfTitle;

    public Ebook() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public Ebook(String key, String pdfUrl, String pdfTitle) {
        this.key = key;
        this.pdfUrl = pdfUrl;
        this.pdfTitle = pdfTitle;
    }
}
