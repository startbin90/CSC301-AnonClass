package edu.toronto.csc301.anonclass.util;

public class FileItem extends SerializableInfo {

    private String fileName;
    private int progress = 0;
    private String url;

    public FileItem(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;

    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFileName() {

        return fileName;
    }

    public int getProgress() {
        return progress;
    }

    public String getUrl() {
        return url;
    }
}
