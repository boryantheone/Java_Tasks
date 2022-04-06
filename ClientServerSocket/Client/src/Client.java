import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable {
    private static String clientJournal;
    private static int PORT;
    private static String IP;

    public Client(String IP, int PORT)
    {
        this.IP = IP;
        this.PORT = PORT;
    }

    public void run() {
        String messageClient = "";
        String response = "";

        try {
            Socket socket = new Socket(IP, PORT);
            BufferedWriter journalFileWriterClient = new BufferedWriter(new FileWriter(clientJournal));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            System.out.println("Connected to server");
            System.out.println("Введите выражение: ");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                messageClient = scanner.nextLine();
                writer.write(messageClient);
                writer.flush();

                response = reader.readLine();
                journalFileWriterClient.write(messageClient + "\n");
                journalFileWriterClient.write(response + "\n");
//                if (messageClient.contains("=")) {
//
//                }
                Thread.yield();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        Client client = new Client(IP, PORT);
        Thread th = new Thread(client);
        th.start();
    }
}
