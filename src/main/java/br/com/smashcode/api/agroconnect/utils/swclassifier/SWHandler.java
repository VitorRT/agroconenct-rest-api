package br.com.smashcode.api.agroconnect.utils.swclassifier;

import java.io.FileWriter;
import java.io.PrintWriter;

public class SWHandler {
    
    public static void main(String[] args) {
        inputGenerator("titulo do post e o conteudo dele em uma só frase");
    }

    public static void inputGenerator(String input) {

        try {
            FileWriter arq = new FileWriter("./src/main/java/br/com/smashcode/api/agroconnect/utils/swclassifier/python/input.txt");
            PrintWriter gravador = new PrintWriter(arq);
            gravador.println(input);
            gravador.close();
            System.out.println("Mensagem gravada com sucesso!");            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
