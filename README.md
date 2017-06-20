# Language detection for Android

This sample shows a proof-of-concept application, that can detect language/locale from text snippets.
There are several online services ([1](https://azure.microsoft.com/en-us/services/cognitive-services/translator-text-api/?v=17.23h) [2](https://cloud.google.com/translate/docs/) [3](https://detectlanguage.com/)), but on-device detection is preferred on most cases because of cost and privacy.

### Library solution

Java library that builds on Wiki or Twitter for language detection.
Result will be the most probable of the added/known language profiles.

It is possible to expand/train the library with new data/languages with no big hassle.

To work on Android there needs to be some customization done to reduce memory footprint. I have done some smaller customization as a fork of [shuyos repo](https://github.com/shuyo/language-detection), but the memory footprint is still 40-90 MB of memory.  
This means that some lower spec phones will not be able to run the code, and depending on phone it will take 10-60 seconds for the library to initialize with all 40 languages supported.  
Limiting the number of languages will of course reduce the loading time and memory footprint.

### Android O TextClassificationManager

Only Android 8+  
For the Android O preview there was a feature announced to improve TTS (Text-To-Speech) with language detection via [TextClassificationManager](https://developer.android.com/reference/android/view/textclassifier/TextClassificationManager.html).  
**But** in developer preview 3, this feature was [removed](https://developer.android.com/sdk/api_diff/26-incr/changes/android.view.textclassifier.TextClassificationManager.html) from the official API, but still accessible unofficially through reflection.
 
It seems that using the TextClassificationManager has no overhead, though it needs at least 5 words in a string to be able to detect any language probability.

The original feature [description](https://developer.android.com/preview/api-overview.html?hl=ja#a11y) was

    Accessibility function
    
    Language Detection
    To identify the language of your choice within the text range specified by the text-to-speech (TTS) tool,
    TextClassificationManager.detectLanguages()use. This method is TextClassificationManagerincluded in the class introduced in
    Android O android.view.textclassifier.TextLanguageYou can use the object's result list to identify the range 
    of text assigned to a particular language and how TTS assigned the language to a particular subset of text.

### Sample code

Usage of the modified Java library is shown in [DetectionExtLib](/app/src/main/java/com/jleth/andorid/langdetect/DetectionExtLib.java) and access to the TextClassificationManager on Android O is shown in  [DetectionTextClassifier](/app/src/main/java/com/jleth/andorid/langdetect/DetectionTextClassifier.java).
 
The code is slow to build (2-10 minutes) because of the large files in the lib module. You can check out the sample debug application in [the sample folder](/samples).

### Difficult situations

* Short text have a higher probability to be “guessed” wrong
* Mix language messages – “out of office indtil i morgen” (English + Danish)
* Emojis
* Code will always match _at least one_ of the available languages. Hard to build reliable rules like “If not detected, use default” 
* Could limit the “detection” to be only messages with more than 2-5 words
* Will match 90% of sentences. More accuracy is unlikely

### Examples

Output from JUnit test of DetectionExtLib.java

| Desc | println | Desc | println |
| --- | --- | --- | --- |
| TEXT | `d r ligemeget` | PROB | `[tl:0.9999951075554466]` |
| PROB | `[da:0.9999965798529784]` | TEXT | `Wie geths` |
| TEXT | `Oh nee die is best slecht` | PROB | `[de:0.9999964124956877]` |
| PROB | `[nl:0.8571375332115239, de:0.1428613482712434]` | TEXT | `Come 2 u or me` |
| TEXT | `Hello there` | PROB | `[pt:0.9999936502079427]` |
| PROB | `[en:0.9999978942007692]` | TEXT | `new invoice` |
| TEXT | `jeg r på vej` | PROB | `[en:0.9999955920839266]` |
| PROB | `[no:0.7142821787613173, da:0.28571782123868267]` | TEXT | `har du gået med hunden` |
| TEXT | `Min computer virker ikke!!!` | PROB | `[da:0.999993160438744]` |
| PROB | `[da:0.716716473458616, no:0.283283526541384]` | TEXT | `har købt en ny laptop` |
| TEXT | `Why did you do that` | PROB | `[da:0.9999934411218343]` |
| PROB | `[en:0.9999979280002544]` | TEXT | `😊` |
| TEXT | `Jabra` | PROB | `[he:0.9999951107521816]` |
| PROB | `[lv:0.9999958637019545]` | TEXT | `Vielen dank für die Blumen` |
| TEXT | `Go away` | PROB | `[de:0.9999959193169594]` |

## References

* [rmtheis/language-detection](https://github.com/rmtheis/language-detection)
* [shuyo/language-detection](https://github.com/shuyo/language-detection)
* [kgusarov/text-processing-utils](https://github.com/kgusarov/text-processing-utils)
* [optimaize/language-detector](https://github.com/optimaize/language-detector):
* [eclectice/language-detector](https://github.com/eclectice/language-detector)


    Github: JeppeLeth