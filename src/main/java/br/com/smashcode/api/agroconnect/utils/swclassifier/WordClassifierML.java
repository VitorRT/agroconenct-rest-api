package br.com.smashcode.api.agroconnect.utils.swclassifier;

public class WordClassifierML {

    public static Boolean sentenceWithSwearWord(String phrase) {
        // gerar arquivo com o input(phrase)
        SWHandler.inputGenerator(phrase);

        try {
            String result = SWHandler.wordClassifier();
            if(result.equals("0")) {
                return true;
            }
            
            return false;
        } catch (Exception e) {
            // lançar uma exception depois..
            return false;
        }
    }
}

