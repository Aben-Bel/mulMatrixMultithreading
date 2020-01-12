package matrixMulti.mulMatrixMultithreading;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Worker {
    private static final int PORT = 4040;
    public Worker(){
        try {

            Socket s = new Socket("127.0.0.1", PORT);

            InputStream inStream = s.getInputStream();
            OutputStream outputStream = s.getOutputStream();

            Scanner in = new Scanner(inStream, "UTF-8");

            PrintWriter pr = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            while(true){
                if(in.hasNextLine()){
                    String val = in.nextLine();
                    System.out.println("SERVER: >>> " + val);

                    // split into to get a row and column
                    StringTokenizer st = new StringTokenizer(val, "|");
                    String rowS = st.nextToken();
                    String colS = st.nextToken();

                    //  changing string to double for row
                    st = new StringTokenizer(rowS, " ");
                    double[] row = new double[3];
                    for(int i = 0; st.hasMoreTokens(); i++){
                        row[i] = Double.parseDouble(st.nextToken());
                    }

                    // changing string to double for column
                    st = new StringTokenizer(colS, " ");
                    double[] col = new double[3];
                    for(int i = 0; st.hasMoreTokens(); i++){
                        col[i] = Double.parseDouble(st.nextToken());
                    }

                    // calculating and send it to server
                    pr.println(mulMat(row, col));


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double mulMat(double[] row, double[] col){
        double sum = 0;
        for(int i = 0; i < row.length; i++){
            sum = sum + row[i]*col[i];
        }
        return sum;
    }
    public static void main(String[] args) {
        new Worker();
    }
}
