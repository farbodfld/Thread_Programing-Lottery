import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static long num;
    public static int round_counter = 1;

    static Map<String, ClientHandler> users;
    static List<ClientHandler> sl = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(3050);

        num = ((long) (Math.random() * 90000)) + 10000;
        System.out.println(num);

        users = new ConcurrentHashMap<>();

        while (true) {
            Socket socket = serverSocket.accept();      // wait for a client to connect
            ClientHandler temp = new ClientHandler(socket);   // create a thread for handling new client
            (new Thread(temp)).start();     // start the thread
        }
    }
}

class ClientHandler implements Runnable {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean gamer = true;

    ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {

        // Get a username from client. Check if it is unique. if not, send an error message to client and wait for another username.
        try {
            boolean isUsernameOk = false;
            String username = "";
            while (!isUsernameOk) {
                username = this.dis.readUTF();
                if (Server.users.containsKey(username)) {
                    String errorMessage = "err: UserName already exists... please enter another one: ";
                    this.dos.writeUTF(errorMessage);
                } else {
                    String okMessage = "OK: Username was OK.";
                    this.dos.writeUTF(okMessage);
                    isUsernameOk = true;
                }
            }
            Server.users.put(username, this);       // Create the user in server
            ClientHandler targetHandler = Server.users.get(username);    // Get the handler for the target username.
            Server.sl.add(targetHandler);                               // add handler to list.
            System.out.println("User " + username + " Connected!");

            dos.writeUTF("The Game is on");

            while (true) {
                while (gamer) {
                    String msg = dis.readUTF();
                    long answer = Long.parseLong(msg);
                    if (answer == Server.num) {
                        dos.writeUTF("You Won!");
                        Server.sl.remove(targetHandler);
                        Server.round_counter++;
                        Server.num = ((long) (Math.random() * 90000)) + 10000;
                        System.out.println(Server.num);
                        gamer = false;
                    } else {
                        dos.writeUTF("Wrong");
                    }
                }
                for (int i = 0; i < Server.sl.size(); i++) {
                    ClientHandler targetHandler2 = Server.sl.get(i);
                    targetHandler2.dos.writeUTF(username + " Won the Game!");
                    targetHandler2.dos.writeUTF("Round " + Server.round_counter + " STARTED!");
                }
                Server.sl.add(targetHandler);
                gamer = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

