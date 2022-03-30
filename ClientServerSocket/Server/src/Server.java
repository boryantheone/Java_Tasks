import com.sun.xml.internal.xsom.XSUnionSimpleType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.FileSystemException;

//        1. Реализовать приложения клиент и сервер (0 – TCP протокол).
//        2. Реализовать в клиенте указание адреса и порта сервера, так: 2 – из командной строки.
//        3. Реализовать указание порта для сервера, так: 3 – из файла настроек.
//        4. Сообщения, получаемые клиентом с сервера должны записываться в файл
//        «Журнала клиента» путь к которому определяется следующим образом: 2 – из командной строки.
//        5. Сообщения, получаемые сервером от клиента должны записываться в файл
//        «Журнала сервера» путь к которому определяется следующим образом:2 – из командной строки.

public class Server {
    private static final int TIME_SEND_SLEEP = 100;
    private static final int COUNT_TO_SEND = 10;
    public static final int READ_BUFFER_SIZE = 10;
    static String fileSettingsPath = "/Users/jcollin/IdeaProjects/ClientServerSocket/Server/fileSettings";
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            int PORT = getPortFromFileSettings(fileSettingsPath);
            if (PORT != -1) {
                ServerSocket server = new ServerSocket(PORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public Server(String fileSettingsPath) {
//            while (true) {
//                try (
//                        Socket socket = server.accept();
//                        BufferedWriter writer = new BufferedWriter(
//                                new OutputStreamWriter(
//                                        socket.getOutputStream()));
//                        BufferedReader reader = new BufferedReader(
//                                new InputStreamReader(
//                                        socket.getInputStream()));
//                ) {
//                    String request = reader.readLine();
//                    System.out.println("request = " + request);
//                    String response = (int)(Math.random() * 30 - 10) + "";
//                    System.out.println("response = " + response);
//                    writer.write(response);
//                    writer.newLine();
//                    writer.flush();
//                }
//                catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        } catch (IOException e) {
//            System.err.println("Не удается открыть сокет для сервера: " + e.toString());
//        }
//        this.fileSettingsPath = fileSettingsPath;
//    }
    public void go() {
        class Listener implements Runnable {
            Socket socket;
            public Listener(Socket aSocket) {
                socket = aSocket;
            }
            public void run() {
                char[] readed = new char[READ_BUFFER_SIZE];
                StringBuffer strBuff = new StringBuffer();
                try {
                    System.out.println("Слушатель запущен");
                    BufferedWriter writer = new BufferedWriter(
                                                new OutputStreamWriter(
                                                    socket.getOutputStream()));
                    BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(
                                                     socket.getInputStream()));
                    while (true) {
                        int count = reader.read(readed, 0, READ_BUFFER_SIZE);
                        if(count == -1) { break;}
                        strBuff.append(readed, 0, count);
                    }
                }
                catch (IOException e) {
                    System.err.println("Исключение: " + e.toString());
                }
            }
        }
    }

    private static int getPortFromFileSettings(String fileSettingsPath) throws IOException {
        File file = new File(fileSettingsPath);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        if (line.isEmpty())
            throw new FileSystemException("Empty file");
        int port = 0;
        try {
            port = Integer.parseInt(line);
        } catch (Exception e) {
            System.out.println(e.toString());
            return (-1);
        }
        return port;
    }
}


