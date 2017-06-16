package com.jleth.andorid.langdetect;

import android.support.annotation.NonNull;

public class Language implements Comparable<Language> {
    public String lang;
    public double prob;

    public Language(String lang, double prob) {
        this.lang = lang;
        this.prob = prob;
    }

    public String toString() {
        if (lang == null) return "";
        return lang + ":" + prob;
    }

    @Override
    public int compareTo(@NonNull Language o) {
        return (int) ((o.prob - this.prob) * 1000f);
    }
}