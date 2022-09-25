import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 3050);       // connect to 3050 port on local host.
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // Get a username from user. Send it to server and see if it's ok or not!
        Scanner in = new Scanner(System.in);
        boolean isUsernameOk = false;
        while (!isUsernameOk) {
            System.out.println("Enter your username please: ");
            String username = in.nextLine();
            dos.writeUTF(username);
            String userValidation = dis.readUTF();
            if (userValidation.startsWith("OK"))
                isUsernameOk = true;
            else
                System.out.println("UserName already exists");
        }

        // This thread waits for server to send a message to it. It then prints it in console.
        Thread messageListener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = dis.readUTF();
                        System.out.println("Incoming message: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        messageListener.start();

        String msg = dis.readUTF();
        System.out.println(msg);

        // This loop gets a number and sends it to server for checking it.
        while (true) {
            System.out.println("Enter your number:");
            String answer = in.nextLine();
            dos.writeUTF(answer);
        }
    }
}
