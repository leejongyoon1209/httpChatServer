import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class WebServer {

    static void main(String[] args) throws IOException {
        String serverName = "localhost";
        int serverPort = 8080;

        HttpServer webServer = HttpServer.create(new InetSocketAddress(serverName, serverPort), 0);
        webServer.createContext("/", new MyHttpHandler());
        webServer.setExecutor(null);

        System.out.println("## HTTP server started at http://" + serverName + ":" + serverPort + ".");

        webServer.start();
    }

    static class MyHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange ex) throws IOException {
            String method = ex.getRequestMethod();

            if ("GET".equalsIgnoreCase(method)) {
                doGet(ex);
            }
            if ("POST".equalsIgnoreCase(method)) {
                doPost(ex);
            }
        }

        private void printHttpRequestDetail(HttpExchange ex) {
            System.out.println("::Client address : " + ex.getRemoteAddress().getAddress().getHostAddress());
            System.out.println("::Client port    : " + ex.getRemoteAddress().getPort());
            System.out.println("::Request command: " + ex.getRequestMethod());
            System.out.println("::Request line   : " + ex.getRequestMethod() + " " + ex.getRequestURI().toString() + " " + ex.getProtocol());
            System.out.println("::Request path   : " + ex.getRequestURI().toString());
            System.out.println("::Request version: " + ex.getProtocol());
        }

        private void sendHttpResponseHeader(HttpExchange ex) throws IOException {
            ex.getResponseHeaders().set("Content-Type", "text/html");
            ex.sendResponseHeaders(200, 0);
        }

        private int simpleCalc(int para1, int para2) {
            return para1 * para2;
        }

        private int[] parameterRetrieval(String msg) {
            int[] result = new int[2];
            String[] fields = msg.split("&");
            for (int i = 0; i < 2; i++) {
                String[] numbers = fields[i].split("=");
                result[i] = Integer.parseInt(numbers[1]);
            }
            return result;
        }

        private void doGet(HttpExchange ex) throws IOException {
            System.out.println("## do_GET() activated.");

            printHttpRequestDetail(ex);
            sendHttpResponseHeader(ex);

            if (ex.getRequestURI().toString().contains("?")) {
                String path = ex.getRequestURI().toString();
                String routine = path.split("\\?")[1];
                int[] parameter = parameterRetrieval(routine);
                int result = simpleCalc(parameter[0], parameter[1]);

                try (OutputStream os = ex.getResponseBody()) {
                    os.write("<html>".getBytes(StandardCharsets.UTF_8));
                    String getResponse = "GET request for calculation => " + parameter[0] + " x " + parameter[1]
                            + " = " + result + ".";
                    os.write(getResponse.getBytes(StandardCharsets.UTF_8));
                    os.write("</html>".getBytes(StandardCharsets.UTF_8));
                }


                System.out.println("## GET request for calculation => " + parameter[0] + " x " + parameter[1]
                        + " = " + result + ".");
            } else {
                try (OutputStream os = ex.getResponseBody()) {
                    os.write("<html>".getBytes(StandardCharsets.UTF_8));
                    os.write(("<p>HTTP Request GET for Path: " + ex.getRequestURI().toString() + "</p>").getBytes(StandardCharsets.UTF_8));
                    os.write("</html>".getBytes(StandardCharsets.UTF_8));
                }

                System.out.println("## GET request for directory => " + ex.getRequestURI().toString() + ".");
            }
        }

        private void doPost(HttpExchange ex) throws IOException {
            System.out.println("## do_POST() activated.");

            printHttpRequestDetail(ex);
            sendHttpResponseHeader(ex);

            int contentLength = Integer.parseInt(ex.getRequestHeaders().getFirst("Content-Length"));

            String postData;
            try (InputStream is = ex.getRequestBody()) {
                byte[] data = is.readNBytes(contentLength);
                postData = new String(data, StandardCharsets.UTF_8);
            }

            int[] parameter = parameterRetrieval(postData);
            int result = simpleCalc(parameter[0], parameter[1]);

            try (OutputStream os = ex.getResponseBody()) {
                String postResponse = "## POST request for calculation => " + parameter[0] + " x "
                        + parameter[1] + " = " + result;
                os.write(postResponse.getBytes(StandardCharsets.UTF_8));
                System.out.println("## POST request data => " + postData + ".");
                System.out.println("## POST request for calculation => " + parameter[0] + " x "
                        + parameter[1] + " = " + result + ".");
            }
        }
    }
}
