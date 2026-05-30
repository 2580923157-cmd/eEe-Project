package utils;

// support/LanguageManager.java

import java.util.ArrayList;
import java.util.List;
import support.Language;
import support.Chinese;
import support.English;
//import support.French;

public class LanguageProcess {
    private static Language currentLanguage = Chinese.INSTANCE;
    private static List<Runnable> listeners = new ArrayList<>();

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void switchLanguage() {
        if (currentLanguage == Chinese.INSTANCE) {
            currentLanguage = English.INSTANCE;
        } /*else if (currentLanguage == English.INSTANCE) {
            currentLanguage = French.INSTANCE;
        } */else {
            currentLanguage = Chinese.INSTANCE;
        }
        notifyListeners();
    }

    public static void addListener(Runnable listener) {
        listeners.add(listener);
    }

    private static void notifyListeners() {
        for (Runnable r : listeners) {
            r.run();
        }
    }
}