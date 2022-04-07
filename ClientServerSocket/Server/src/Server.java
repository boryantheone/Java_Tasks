
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.FileSystemException;
import java.util.Scanner;

//        1. Реализовать приложения клиент и сервер (0 – TCP протокол).
//        2. Реализовать в клиенте указание адреса и порта сервера, так: 2 – из командной строки.
//        3. Реализовать указание порта для сервера, так: 3 – из файла настроек.
//        4. Сообщения, получаемые клиентом с сервера должны записываться в файл
//        «Журнала клиента» путь к которому определяется следующим образом: 2 – из командной строки.
//        5. Сообщения, получаемые сервером от клиента должны записываться в файл
//        «Журнала сервера» путь к которому определяется следующим образом:2 – из командной строки.

//        Сервер возвращает клиенту результат выражения (допустимые операции «+», «-»).
//        Операнды и операции передаются за раз по одному (например, выражение «3.4+1.6-
//        5=» нужно передавать с помощью трёх сообщений: «3.4+», «1.6-» и «5=», где «=» -
//        признак конца выражения). В случае не возможности разобрать сервером полученную
//        строку или при переполнении, возникшем при вычислении полученного выражения,
//        сервер присылает клиенту соответствующее уведомление.

public class Server {
    private static final int COUNT_TO_SEND = 10;
    public static final int READ_BUFFER_SIZE = 10;
    static String fileSettingsPath = "/Users/boryantheone/IdeaProjects/Java_Labs/ClientServerSocket/Server/src/fileSettings";
    private ServerSocket serverSocket;
    private String serverJournal;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int PORT = getPortFromFileSettings(fileSettingsPath);
            System.out.println("Введите путь к журналу сервера");
            String serverJournal = scanner.nextLine();
            File serverJournalFile = new File(serverJournal);
            try{
                if (!serverJournalFile.exists())
                    serverJournalFile.createNewFile();
            } catch (IOException e){
                System.out.println(e);
                return;
            }
            if (PORT != -1) {
                Server tcpServer = new Server(PORT, serverJournal);
                tcpServer.go();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(int PORT, String serverJournal) {
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен на порту " + PORT);
        }catch(IOException e){
            System.err.println("Не удаётся открыть сокет для сервера: " + e.toString());
        }
        this.serverJournal = serverJournal;
    }

    public void go() {
        class Listener implements Runnable {
            Socket socket;

            public Listener(Socket aSocket) {
                socket = aSocket;
            }

            public void run() {
                String response = "";
                String expression = "";
                Boolean sign = null;
                Double intermediate = null;
                Double result = null;
                boolean isError = false;
//                String response = "";
//                String message = "";
//                double summa = 0;
//                boolean bool=true;
                try {
                    System.out.println("Слушатель запущен");

                    InputStream in = socket.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(streamReader);

//                    OutputStream out = socket.getOutputStream();
//                    OutputStreamWriter writer = new OutputStreamWriter(out);
//                    PrintWriter pWriter = new PrintWriter(writer);

                    FileWriter fileWriter = new FileWriter(serverJournal, true);
                    BufferedWriter bw = new BufferedWriter(fileWriter);

                    while (!expression.contains("=")){
                        expression = reader.readLine();
                        System.out.println(expression);

                        bw.write(expression + "\n");

                        char current;

                        for (int i = 0; i < expression.length(); i++){
                            current = expression.charAt(i);
                            if (Character.isDigit(current) || current == '.')
                                continue;
                            else if (current == '+' || current == '-' || current == '='){
                                String considered = expression.substring(0, i);

                                try {
                                    intermediate = Double.parseDouble(considered);
                                } catch (Exception e){
                                    isError = true;
                                    break;
                                }

                                if (sign != null){
                                    if (sign)
                                        result += intermediate;
                                    else
                                        result -= intermediate;
                                }
                                else result = intermediate;

                                switch (current) {
                                    case '+' -> sign = true;
                                    case '-' -> sign = false;
                                }

                                if (current == '=') {
                                    break;
                                }
                            }
                            else{
                                isError = true;
                                break;
                            }
                        }
                        if (isError) {
                            response = "Ошибка в выражении!\n";
                            break;
                        }
                        response = result.toString();
                    }
                    bw.close();

                    OutputStream out = socket.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(out);
                    PrintWriter pWriter = new PrintWriter(writer);
                    pWriter.println(response);
                    pWriter.flush();


                }catch(IOException e){
                    System.err.println("Exception: " + e.toString());
                }
//                        message = reader.readLine();
////                        if (message.contains("="))
////                            break;
//
//                        bw.write(message + "\n");
//
//                        if (!message.isEmpty()) {
//                            if(message.contains("!")){
//                                break;
//                            }
//                            if (message.matches("\\d(\\d+)?(\\.\\d+)?[-+=]")){
//                                if (message.contains("=")){
//                                    String[] num = message.split("=");
//                                    if (num.length>1) {
//                                        throw new IOException();
//                                    }else{
//                                        if (bool) {
//                                            summa+=Double.parseDouble(num[0]);
//                                        } else {
//                                            summa-=Double.parseDouble(num[0]);
//                                        }
//                                        bool=true;
//                                        pWriter.write(String.valueOf(summa));
//                                        pWriter.flush();
//                                        System.out.println(summa);
//                                        summa = 0;
//                                    }
//                                }
//                                else if (message.charAt(message.length()-1)=='+') {
//                                    String[] num = message.split("[+]");
//                                    if (num.length>1) {
//                                        throw new IOException();
//                                    }else{
//                                        if (bool) {
//                                            summa+=Double.parseDouble(num[0]);
//                                        } else {
//                                            summa-=Double.parseDouble(num[0]);
//                                        }
//                                        bool=true;
//                                    }
//                                }
//                                else  if (message.charAt(message.length()-1)=='-') {
//                                    String[] num = message.split("-");
//                                    if (num.length>1) {
//                                        throw new IOException();
//                                    }else{
//                                        if (bool) {
//                                            summa+=Double.parseDouble(num[0]);
//                                        } else {
//                                            summa-=Double.parseDouble(num[0]);
//                                        }
//                                        bool=false;
//                                    }
//                                }
//                            }
//                            else{
//                                pWriter.write("Введен неверный знак!");
//                                pWriter.flush();
//                            }
//                        }
//                    }
//                    bw.close();
//
//                } catch (IOException e) {
//                    System.err.println("Исключение: " + e.toString());
//                }
            }
        }
        while (true)
        {
            try {
                Socket socket = serverSocket.accept();
                Listener listener = new Listener(socket);
                Thread thread = new Thread(listener);
                thread.start();
            } catch (IOException e) {
                System.err.println("Исключение: " + e.toString());
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



