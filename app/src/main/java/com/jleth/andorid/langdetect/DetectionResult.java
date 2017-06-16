package com.jleth.andorid.langdetect;

import java.util.List;

class DetectionResult {
    public List<Language> list;
    public long executionTime;
    public Exception e;

    public boolean hasError() {
        return e != null;
    }

    public boolean isSuccess() {
        return list != null;
    }
}