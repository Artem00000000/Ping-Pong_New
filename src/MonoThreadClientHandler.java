import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    Socket client;
    PrintWriter out;
    BufferedReader in;
    MultiThreadServer server;
    ServerGame room = null;
    int number = -1;

    public MonoThreadClientHandler(Socket client, PrintWriter out, BufferedReader in, MultiThreadServer server) {
        this.client = client;
        this.out = out;
        this.in = in;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            // инициируем каналы общения в сокете, для сервера

            // канал записи в сокет следует инициализировать сначала канал чтения для избежания блокировки выполнения программы на ожидании заголовка в сокете
            //DataOutputStream out = new DataOutputStream(client.getOutputStream());

// канал чтения из сокета
            //DataInputStream in = new DataInputStream(client.getInputStream());
            //System.out.println("DataInputStream created");

            //System.out.println("DataOutputStream  created");
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не
            // закрыт клиентом
            while (!client.isClosed()) {
                //System.out.println("Server reading from channel");

                // серверная нить ждёт в канале чтения (inputStream) получения
                // данных клиента после получения данных считывает их
                //String entry = in.readUTF();
                String inputLine;
                if ((inputLine = in.readLine()) != null) {

                    // и выводит в консоль
                    System.out.println("READ from clientDialog message - " + inputLine);

                    // инициализация проверки условия продолжения работы с клиентом
                    // по этому сокету по кодовому слову - quit в любом регистре
                    JSONObject js = new JSONObject(inputLine);
                    if (js.getString("mainTag").equals("quit")) {
                        System.out.println("Client initialize connections suicide ...");
                        break;
                    } else if (js.getString("mainTag").equals("newRoom")) {
                        ServerGame t = new ServerGame(server.lastID + 1, js.getString("name"), js.getString("password"), 1, js.getInt("maxCountPlayers"));
                        server.rooms.add(t);
                        server.lastID += 1;
                        server.sendRooms("all");
                        String message;
                        JSONObject json = new JSONObject();
                        json.put("mainTag", "joinRoom");
                        json.put("number", 1);
                        json.put("id", server.lastID);
                        message = json.toString();
                        out.println(message);
                        room = t;
                        room.out[0] = this.out;
                        number = 1;
                    } else if (js.getString("mainTag").equals("joinRoom")) {
                        int id = js.getInt("id");
                        ServerGame t = null;
                        for (int i = 0; i < server.rooms.size(); i++) {
                            t = server.rooms.get(i);
                            if (t.id == id) {
                                break;
                            }
                        }
                        if (t != null) {
                            for (int i = 0; i < 4; i++) {
                                if (t.players[i].equals("none")) {
                                    number = i + 1;
                                    t.players[i] = "not ready";
                                    t.out[i] = this.out;
                                    room = t;
                                    break;
                                }
                            }
                            if (number != -1) {
                                String message;
                                JSONObject json = new JSONObject();
                                json.put("mainTag", "joinRoom");
                                json.put("number", number);
                                json.put("id", id);
                                message = json.toString();
                                out.println(message);
                                room.sendJSON("readiness");
                            }
                        }
                    } else if (js.getString("mainTag").equals("ready")) {
                        boolean t = js.getBoolean("value");
                        if (room != null) {
                            if (t) {
                                room.players[number - 1] = "ready";
                            } else {
                                room.players[number - 1] = "not ready";
                            }
                            room.sendJSON("readiness");
                            t = true;
                            for (int i = 0; i < 4; i++) {
                                if (room.players[i].equals("not ready") || room.players[i].equals("none")) {
                                    t = false;
                                    break;
                                }
                            }
                            if (t) {
                                room.sendJSON("startGame");
                            }
                        }
                    } else if (js.getString("mainTag").equals("leaveRoom")) {
                        if (room != null) {
                            room.players[number - 1] = "none";
                            room.out[number - 1] = null;
                            room = null;
                        }
                    } else if (js.getString("mainTag").equals("keys")) {
                        if (room != null) {
                            room.pressedKeys[number - 1][0] = js.getBoolean("key1");
                            room.pressedKeys[number - 1][1] = js.getBoolean("key2");
                        }
                    }
                }
//                if (entry.equalsIgnoreCase("quit")) {
//
//                    // если кодовое слово получено то инициализируется закрытие
//                    // серверной нити
//                    System.out.println("Client initialize connections suicide ...");
//                    out.writeUTF("Server reply - " + entry + " - OK");
//                    Thread.sleep(3000);
//                    break;
//                }

                // если условие окончания работы не верно - продолжаем работу -
                // отправляем эхо обратно клиенту

                //System.out.println("Server try writing to channel");
                //out.writeUTF("Server reply - " + entry + " - OK");
                //System.out.println("Server Wrote message to clientDialog.");

                // освобождаем буфер сетевых сообщений
                //out.flush();

                // возвращаемся в началло для считывания нового сообщения
            }

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // основная рабочая часть //
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // если условие выхода - верно выключаем соединения
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            client.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}