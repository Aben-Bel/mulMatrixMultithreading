package matrixMulti.mulMatrixMultithreading;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private String[] matrix1n = new String[3];
    private String[] matrix2n = new String[3];
    private int atWorker = -1;
    private int j = 0;
    private int k = 0;
    private PrintWriter clinetpr;

    private double[][] matrix = {{0.0,0.0,0.0},{0.0,0.0,0.0},{0.0,0.0,0.0}};



    public Server() throws IOException{
            // A THREAD FOR CLIENT TO CONNECT AT PORT 4444
            Thread forClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    ServerSocket s = null;
                    try {
                        s = new ServerSocket(4444);
                        Socket soc = s.accept();

                        InputStream inputStream = soc.getInputStream();
                        OutputStream outputStream = soc.getOutputStream();

                        Scanner in = new Scanner(inputStream, "UTF-8");

                        StringTokenizer st = null;
                        while(true){
                            PrintWriter pr = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                            // when there is message from client to server
                            // in = Scanner(inputStream).hasNextLine() is a BLOCKING method call.
                            if(in.hasNextLine()) {
                                // get request from clinet
                                String request = in.nextLine();

                                // split matrix into two, assuming 3x3 two matrices
                                st = new StringTokenizer(request, "|");
                                String matrix1s = st.nextToken();
                                String matrix2s = st.nextToken();

                                // getting the first matrix's rows
                                st = new StringTokenizer(matrix1s, ",");
                                for (int i = 0; st.hasMoreTokens(); i++) {
                                    String val = st.nextToken();
                                    matrix1n[i] = val;
                                }

                                // getting the second matrix's column
                                // INVERTING matrix by iterating from top to down and from left to right
                                double[][] matrix2nt = new double[3][3];
                                st = new StringTokenizer(matrix2s, ",");
                                for(int i = 0; st.hasMoreTokens();i++){
                                    String val = st.nextToken();
                                    StringTokenizer numt = new StringTokenizer(val, " ");
                                    for(int j = 0; numt.hasMoreTokens(); j++){
                                        String num = numt.nextToken();
                                        matrix2nt[i][j] = Integer.parseInt(num);
                                    }
                                }

                                // recording the second inverted matrix's row
                                for(int col = 0; col < matrix2nt[0].length; col++){
                                    String val = "";
                                    for(int row = 0; row < matrix2nt.length; row++){
                                        val+= matrix2nt[row][col] + " ";
                                    }
                                    matrix2n[col] = val;
                                }

                                // sending message for client to wait
                                pr.println("Will get back to you with result!");
                                clinetpr = pr;

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            forClient.start();

            // THREAD FOR WORKER
            Thread forWorkers = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerSocket s = new ServerSocket(4040);
                        while(true){
                            Socket incoming = s.accept();
                            atWorker+=1;

                            // Thread for each worker
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        InputStream inputStream = incoming.getInputStream();
                                        OutputStream outputStream = incoming.getOutputStream();

                                        Scanner in = new Scanner(inputStream, "UTF-8");
                                        PrintWriter pr = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                                        // Sending message to worker with the row and column to multiply for us
                                        pr.println(matrix1n[j] + "|" + matrix2n[k]);
                                        while(true){
                                            // listening answers from worker
                                            if(in.hasNextLine()){
                                                // getting result from worker
                                                String val = in.nextLine();
                                                System.out.println("from worker " + atWorker + " >>> " + val);
                                                matrix[j][k] = Double.parseDouble(val); // registering result

                                                // counter that selects which row with which column to be multiplied
                                                k+=1;
                                                if(k == 3){
                                                    k = 0;
                                                    j += 1;
                                                }
                                                if(j == 3){
                                                    j = 0;
                                                }
                                                // sending the next question to worker
                                                pr.println(matrix1n[j] + "|" + matrix2n[k]);

                                            }
                                            // checks if calculation is finished and breaks the infinite loop.
                                            // sends result to clinet
                                            if(k==2 && j == 2){
                                                String str = "" + matrix[0][0] + " " + matrix[0][1] + " " + matrix[0][2] + "\n"
                                                        + matrix[1][0] + " " + matrix[1][1] + " " + matrix[1][2] + " " + "\n"
                                                        + matrix[2][0] + " " + matrix[2][1] + " " + matrix[2][2] + " ";
                                                clinetpr.println("answer found >>> " + "\n" + str);
                                                break;
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            forWorkers.start();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server();
    }
}
