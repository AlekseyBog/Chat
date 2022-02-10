package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static final int PORT = 8189;
    private static final String ADDRESS = "localhost";
    private static Scanner sc;
    private static PrintWriter out;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket(ADDRESS, PORT);
            System.out.println("Connected!");

            sc = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread thread = new Thread(() -> {
                while (true) {
                    String str = scanner.nextLine();
                    out.println("Client: " + str);
                }
            });
            thread.setDaemon(true);
            thread.start();

            while (true) {
                String str = sc.nextLine();

                if (str.equals("Server: /end")) {
                    break;
                }
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Disconnect");
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}