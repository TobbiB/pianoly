package grg.music.pianoly.model.web;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Enumeration;

public final class Web {

    private static HttpServer server;
    public static final HttpContext[] context = new HttpContext[40];

    public static void initialize() {
        try {
            server = HttpServer.create(new InetSocketAddress(getIP(), 8000), 0);
            for (int i = 0; i < context.length; i++) {
                context[i] = server.createContext("/" + i);
                context[i].setHandler(new HttpHandler() {
                    @Override
                    public void handle(HttpExchange exchange) throws IOException {
                        exchange.sendResponseHeaders(200, 0);
                        OutputStream os = exchange.getResponseBody();
                        String s = "Hallo";
                        os.write(s.getBytes(), 0, s.length());
                        os.close();
                    }
                });
            }
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setContext(int i, HttpHandler handler) {
        server.removeContext("/" + i);
        context[i] = server.createContext("/" + i, handler);
    }

    private static String getIP() {
        String ip = "";
        try {
        String localhost = InetAddress.getLocalHost().getHostAddress();
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            NetworkInterface ni = e.nextElement();
            if (ni.isLoopback())
                continue;
            if (ni.isPointToPoint())
                continue;
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address) {
                    ip = address.getHostAddress();
                }
            }
        }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
