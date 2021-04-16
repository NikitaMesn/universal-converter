package org;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main
{
    public static void main( String[] args ) throws IOException {

        if (args.length == 0 || !args[0].contains(".csv")) {
            System.out.println("Please specify valid CSV file path");
        } else {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/convert", new EchoHandler(args[0]));

            final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);

            server.start();

            Scanner input = new Scanner(System.in);
            if ("exit".equalsIgnoreCase(input.nextLine())) {
                server.stop(1);
            }
        }
    }
}

