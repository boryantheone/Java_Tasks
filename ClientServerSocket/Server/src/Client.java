import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable{
    private static final int TIME_SEND_SLEEP = 100;
    private static String clientJournal;
    private static int PORT;
    private static String IP;
//    private String message = null;
    private String clientJournalFile = null;

    public Client(String IP, int PORT, String clientJournalFile)
    {
        this.IP = IP;
        this.PORT = PORT;
//        this.message = message;
        this.clientJournalFile = clientJournalFile;
    }

    public void run() {
        String response = "";
//        String message = "";
        try{
            Socket socket = new Socket(IP, PORT);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            PrintWriter pWriter = new PrintWriter(streamWriter);

            System.out.println("Введите выражение: ");

            while(true) {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                pWriter.write(message);
                pWriter.flush();

                if (message.contains("=")) {
                    break;
                }
            }

            InputStream in = socket.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);
            response = reader.readLine();

            FileWriter writerJournal = new FileWriter(clientJournalFile, true);
            BufferedWriter bw = new BufferedWriter(writerJournal);

            try{
                bw.write("Клиент: " + response + "\n");
            } finally {
                bw.close();
            }
            pWriter.close();
            reader.close();
//            Thread.yield();
        } catch (IOException  e) {
            System.err.println("Исключение: " + e.toString());
        }

        System.out.println("Клиент получил результат: " + response);
    }

    public static void main(String[] args) {

        BufferedReader readerIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Введите путь к журналу клиента");
            clientJournal = readerIn.readLine();
            File clientJournalFile = new File(clientJournal);
            try{
                if (!clientJournalFile.exists())
                    clientJournalFile.createNewFile();
            } catch (IOException e){
                System.out.println(e);
                return;
            }
            System.out.println("Введите порт");
            PORT = Integer.parseInt(readerIn.readLine());
            System.out.println("Введите адрес");
            IP = readerIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Client client = new Client(IP, PORT, clientJournal);
        Thread th = new Thread(client);
        th.start();
    }
}
