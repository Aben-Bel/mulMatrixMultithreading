package matrixMulti.mulMatrixMultithreading;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 4444;
    private double[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
    private double[][] matrix2 = {{1,0,0}, {0,1,0},{0,0,1}};
    public Client(){
        try {

            Socket s = new Socket("127.0.0.1", PORT);

            InputStream inStream = s.getInputStream();
            OutputStream outputStream = s.getOutputStream();

            Scanner in = new Scanner(inStream, "UTF-8");
            Scanner keyboard = new Scanner(System.in);

            PrintWriter pr = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

            // a thread to collect message from server
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        while(in.hasNextLine()){
                            System.out.println("SERVER: >>> " + in.nextLine());
                        }
                        in.close();
                    }

                }
            }).start();

            // Getting input from user using Scanner;
            while(true){
                System.out.println("\n>>> ");

                // sending user input to server
                // use input: 1 2 3,4 5 6,7 8 9|1 0 0,0 1 0,0 0 1
                pr.println(keyboard.nextLine());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
