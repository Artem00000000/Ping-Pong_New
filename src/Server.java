import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Server {

    ArrayList<ServerGame> rooms = new ArrayList<>();
    int lastID = -1;

    public static void main(String[] args) throws IOException {
        new Server();
    }

    Server() throws IOException{
        ServerSocket serverSocket = new ServerSocket(8000);
        try (
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("new connection from: " + clientSocket.getRemoteSocketAddress().toString());

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
            out.println(message);

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject js = new JSONObject(inputLine);
                if (js.getString("mainTag").equals("newRoom")){
                    rooms.add(new ServerGame(lastID+1, js.getString("name"), js.getString("password"), 1, js.getInt("maxCountPlayers")));
                    lastID += 1;
                }
                System.out.println("have read from client : " + inputLine);
                //out.println(Integer.valueOf(inputLine) + 1);
                //System.out.println("have wrote to client: " + (Integer.valueOf(inputLine) + 1));
            }
            System.out.println("client has disconnected");
        }
        catch (Throwable cause) {
            System.out.println("connection error: " + cause.getMessage());
        }
    }
}
