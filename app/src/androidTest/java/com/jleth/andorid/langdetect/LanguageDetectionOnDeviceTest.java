package com.jleth.andorid.langdetect;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

public class LanguageDetectionOnDeviceTest {

    @Test
    public void langDetect() throws Exception {
        compareResults("d r ligemeget");
        compareResults("Oh nee die is best slecht");
        compareResults("Hello there");
        compareResults("jeg r på vej");
        compareResults("Min computer virker ikke!!!");
        compareResults("Why did you do that");
        compareResults("Jabra");
        compareResults("Go away");
        compareResults("Wie geths");
        compareResults("Come 2 u or me");
        compareResults("new invoice");
        compareResults("har du gået med hunden");
        compareResults("har købt en ny laptop");
        compareResults("\uD83D\uDE0A");
        compareResults("Vielen dank für die Blumen");
        compareResults("U R the 1 4 me U R the 1 + only");
    }

    private void compareResults(String text) throws Exception {
        System.out.println("Detection using lib vs native text classification:");
        System.out.println("TEXT: " + text);
        detectLangExt(text);
        detectLangNative(text);
    }

    private void detectLangExt(String text) throws Exception {
        DetectionResult detectionResult = new DetectionExtLib().execute(text).get();
        if (detectionResult.isSuccess()) {
            System.out.println(detectionResult.list);
        } else {
            System.out.println("ERROR: " + detectionResult.e.getMessage());
        }
    }

    private void detectLangNative(String text) throws Exception {
        DetectionResult detectionResult = new DetectionTextClassifier(InstrumentationRegistry.getTargetContext()).execute(text).get();
        if (detectionResult.isSuccess()) {
            System.out.println(detectionResult.list);
        } else {
            System.out.println("ERROR: " + detectionResult.e.getMessage());
        }
    }
}
