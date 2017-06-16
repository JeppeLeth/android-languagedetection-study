package com.jleth.andorid.langdetect;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import org.junit.Test;

import java.util.ArrayList;

public class LanguageDetectionTest {

//    @Test
//    public void printLangs() {
//        for (Map.Entry<String, String> set : Messages.getAllStrings().entrySet()) {
//            System.out.println(set);
//        }
//    }


    @Test
    public void langDetect() throws LangDetectException {
        detectLang("d r ligemeget");
        detectLang("Oh nee die is best slecht");
        detectLang("Hello there");
        detectLang("jeg r på vej");
        detectLang("Min computer virker ikke!!!");
        detectLang("Why did you do that");
        detectLang("Jabra");
        detectLang("Go away");
        detectLang("Wie geths");
        detectLang("Come 2 u or me");
        detectLang("new invoice");
        detectLang("har du gået med hunden");
        detectLang("har købt en ny laptop");
        detectLang("\uD83D\uDE0A");
        detectLang("Vielen dank für die Blumen");
        detectLang("U R the 1 4 me U R the 1 + only");
    }

    private void detectLang(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        ArrayList<Language> tre = detector.getProbabilities();
        System.out.println("TEXT: " + text);
        System.out.println("PROB: " + tre);
    }
}
