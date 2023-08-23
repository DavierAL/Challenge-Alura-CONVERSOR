package View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Data {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese la primera divisa: ");
            String from = scanner.nextLine();
            System.out.print("Ingrese la segunda divisa: ");
            String to = scanner.nextLine();
            System.out.print("Ingrese la cantidad: ");
            String amount = scanner.nextLine();
            String amount1 = "1";


            JSONObject jsonObject = performApiRequest(from, to, Double.parseDouble(amount));
            double rate = jsonObject.getDouble("result");
            System.out.println("Tasa de cambio " + from + " a " + to + ": " + rate);

            JSONObject jsonObject1 = performApiRequest(from, to, Double.parseDouble(amount1));
            double rate1 = jsonObject1.getDouble("result");
            System.out.println("1 Dólar estadounidense Es igual a " + rate1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Solicitar una petición

    }

    public static JSONObject performApiRequest(String from, String to, Double amount) throws Exception {
        String url_str = buildUrl(from, to, amount);
        URL url = new URL(url_str);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Ocurrió un error: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder informationString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                informationString.append(line);
            }
            return new JSONObject(informationString.toString());
        }
    }

    public static String buildUrl(String from, String to, Double amount) {
        return "https://api.exchangerate.host/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&places=2";
    }
}
