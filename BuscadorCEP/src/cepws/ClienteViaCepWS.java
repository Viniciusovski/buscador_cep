package cepws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteViaCepWS {

    public static String buscarCep(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream(); // l� o conteudo da que a url retorna em byte
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // �rea de mem�ria que � utilizada para armazenamento tempor�rio dos elementos que foram produzidos mas ainda n�o foram consumidos.

            StringBuilder jsonSb = new StringBuilder(); //Essa classe permite criar e manipular dados de Strings dinamicamente, ou seja, podem criar vari�veis de String modific�veis.

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    public static void main(String[] args) throws IOException {
        String json = buscarCep("03823060");
        System.out.println(json);

        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        }

        System.out.println(mapa);
    }
}