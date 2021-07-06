package HW_3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private String nickname;
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public String getNickname() {
        return nickname;
    }

    public Client(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/auth ")) {
                            String[] tokens = msg.split("\\s");
                            String nickname = server.getAuthService().getNickname(tokens[1], tokens[2]);
                            if (nickname != null && !server.isNickBusy(nickname)) {
                                sendMsg("/auth:succeeded " + nickname);
                                this.nickname = nickname;
                                server.subscribe(this);
                                break;
                            }
                        }
                    }
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/")) {
                            if (msg.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }
                            if (msg.startsWith("/w ")) {
                                String[] tokens = msg.split("\\s", 3);
                                server.privateMsg(this, tokens[1], tokens[2]);
                            }
                            if (msg.startsWith("/changenick ")) {
                                String newNickname = msg.split("\\s", 2)[1];
                                if (!newNickname.matches("([a-zA-Z]+[0-9]*)|([а-яА-Я]+[0-9]*)")) {
                                    sendMsg("/changenick:error Nickname can contain only letters and numbers");
                                    continue;
                                }
                                if (server.getAuthService().changeNickname(this.nickname, newNickname)) {
                                    this.nickname = newNickname;
                                    sendMsg("/changenick:succeeded " + nickname);
                                    server.broadcastClientsList();
                                } else {
                                    sendMsg("/changenick:error Nickname is already taken");
                                }
                            }
                        } else {
                            server.broadcastMsg(this.nickname, msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Client.this.disconnect();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
