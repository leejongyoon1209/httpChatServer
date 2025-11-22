import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {

    static void main(String[] args) throws IOException {
        System.out.println("## HTTP client started.");

        System.out.println("## GET request for http://localhost:8080/temp/");
        String getResponse1 = get("http://localhost:8080/temp/");
        System.out.println("## GET response [start]");
        System.out.print(getResponse1);
        System.out.println("## GET response [end]");

        System.out.println("## GET request for http://localhost:8080/?var1=9&var2=9");
        String getResponse2 = get("http://localhost:8080/?var1=9&var2=9");
        System.out.println("## GET response [start]");
        System.out.print(getResponse2);
        System.out.println("## GET response [end]");

        System.out.println("## POST request for http://localhost:8080/ with var1 is 9 and var2 is 9");
        String postResponse1 = post("http://localhost:8080/", "var1=9&var2=9");
        System.out.println("## POST response [start]");
        System.out.print(postResponse1);
        System.out.println("## POST response [end]");

        System.out.println("## HTTP client completed.");
    }

    private static String get(String str) throws IOException {
        URL url = new URL(str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null ) {
                sb.append(line).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    private static String post(String str, String body) throws IOException {
        URL url = new URL(str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null ) {
                sb.append(line).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}


