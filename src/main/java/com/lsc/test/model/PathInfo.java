package com.lsc.test.model;

public class PathInfo {
    private boolean isExist;

    private boolean isDir;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public PathInfo(){

    }

    public PathInfo(boolean isExist, boolean isDir) {
        this.isExist = isExist;
        this.isDir = isDir;
    }
}
