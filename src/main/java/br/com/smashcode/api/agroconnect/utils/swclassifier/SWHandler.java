package br.com.smashcode.api.agroconnect.utils.swclassifier;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SWHandler {

    public static void inputGenerator(String input) {

        String inputTxtPath = "./src/main/java/br/com/smashcode/api/agroconnect/utils/swclassifier/python/input.txt";

        try {
            FileWriter arq = new FileWriter(inputTxtPath);
            PrintWriter gravador = new PrintWriter(arq);
            gravador.println(input);
            gravador.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String wordClassifier() throws IOException {

        String pythonScriptPath = "./src/main/java/br/com/smashcode/api/agroconnect/utils/swclassifier/python/ia.py";
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath);
        Process process = processBuilder.start();

        // Captura a saída do processo Python
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder output = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            output.append(linha);
        }

        // Aguarda o término do processo Python
        try {
           process.waitFor();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output.toString();
    }
}
