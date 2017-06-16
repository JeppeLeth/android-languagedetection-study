package com.jleth.andorid.langdetect;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.Language;
import com.cybozu.labs.langdetect.DetectorFactory;

import java.util.ArrayList;

public class DetectionExtLib extends AsyncTask<String, Void, DetectionResult> {

    @Override
    protected DetectionResult doInBackground(String... params) {
        long startTime = SystemClock.elapsedRealtime();
        DetectionResult result = new DetectionResult();
        try {
            Detector detector = DetectorFactory.create();
            detector.append(params[0]);
//            detector.setVerbose(); // Will add a significant overhead of ~500ms
            ArrayList<Language> tre = detector.getProbabilities();
            result.list = new ArrayList<>(tre.size());
            for (Language language : tre) {
                result.list.add(new com.jleth.andorid.langdetect.Language(language.lang, language.prob));
            }
            result.executionTime = SystemClock.elapsedRealtime() - startTime;
        } catch (Exception e) {
            Log.d("MAIN", "Error", e);
            result.e = e;
        }
        return result;
    }
}