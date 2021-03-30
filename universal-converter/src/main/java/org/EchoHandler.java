package org;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;

class EchoHandler implements HttpHandler {
    String pathToCSV;
    ConvertJson handleJson;
    Converter conv;

    public EchoHandler(String pathToCSV) {
        this.pathToCSV = pathToCSV;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            try (OutputStream response = exchange.getResponseBody();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {

                handleJson = new ConvertJson(reader);
                conv = new Converter(pathToCSV, handleJson.getMapPost());

                try {
                    String res = conv.makeConvert();
                    byte[] resByte = res.getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, resByte.length);
                    response.write(resByte);
                    response.close();

                } catch (Exception404 ex404) {
                    //ex404.printStackTrace();
                    exchange.sendResponseHeaders(404, 0);
                } catch (Exception400 ex400) {
                    //ex400.printStackTrace();
                    exchange.sendResponseHeaders(400, 0);
                }

            } catch (IOException e) {
                //e.printStackTrace();
                exchange.sendResponseHeaders(400, 0);
            }
        }
        exchange.close();
    }
}