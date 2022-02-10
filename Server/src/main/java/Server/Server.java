package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket server;
    private static Socket socket;
    private static final int PORT = 8189;
    private static Scanner sc;
    private static PrintWriter out;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started!");
            socket = server.accept();
            System.out.println("Client connected!");

            sc = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread thread = new Thread(() -> {
                while (true){
                    out.println("Server: "+ scanner.nextLine());
                }
            });
            thread.setDaemon(true);
            thread.start();

            while (true){
                String str = sc.nextLine();
                if (str.equals("Client: /end")) {
                    break;
                }
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("Client disconnect");
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}