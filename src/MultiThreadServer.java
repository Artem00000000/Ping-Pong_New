import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MultiThreadServer {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    ArrayList<ServerGame> rooms = new ArrayList<>();
    int lastID = -1;
    ArrayList<Socket> clients = new ArrayList<>();
    ArrayList<PrintWriter> outS = new ArrayList<>();



    public static void main(String[] args){
        new MultiThreadServer();
    }


    MultiThreadServer(){
        // стартуем сервер на порту 3345 и инициализируем переменную для обработки консольных команд с самого сервера
        try (ServerSocket server = new ServerSocket(8000, 0, InetAddress.getByName("localhost"));
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            // стартуем цикл при условии что серверный сокет не закрыт
            while (!server.isClosed()) {

                // проверяем поступившие комманды из консоли сервера если такие
                // были
                if (br.ready()) {
                    System.out.println("Main Server found any messages in channel, let's look at them.");

                    // если команда - quit то инициализируем закрытие сервера и
                    // выход из цикла раздачии нитей монопоточных серверов
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                // если комманд от сервера нет то становимся в ожидание
                // подключения к сокету общения под именем - "clientDialog" на
                // серверной стороне
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                clients.add(client);
                outS.add(out);
                sendRooms("one");

                // после получения запроса на подключение сервер создаёт сокет
                // для общения с клиентом и отправляет его в отдельную нить
                // в Runnable(при необходимости можно создать Callable)
                // монопоточную нить = сервер - MonoThreadClientHandler и тот
                // продолжает общение от лица сервера
                executeIt.execute(new MonoThreadClientHandler(client, out, in, this));
                System.out.print("Connection accepted.");
            }

            // закрытие пула нитей после завершения работы всех нитей
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRooms(String param){
        String message;
        JSONObject json = new JSONObject();
        json.put("mainTag", "roomList");
        JSONArray array = new JSONArray();
        for (int i = 0; i<rooms.size(); i++){
            JSONObject item = new JSONObject();
            ServerGame room = rooms.get(i);
            item.put("id", room.id);
            item.put("name", room.name);
            item.put("password", room.password);
            item.put("countPlayers", room.countPlayers);
            item.put("maxCountPlayers", room.maxCountPlayers);
            array.put(item);
        }
        json.put("rooms", array);
        message = json.toString();
        if (param.equals("one")){
            PrintWriter out = outS.get(outS.size()-1);
            out.println(message);
        }
        else{
            for (int i=0; i<outS.size(); i++){
                outS.get(i).println(message);
            }
        }
    }
}
