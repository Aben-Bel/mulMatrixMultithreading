package matrixMulti.mulMatrixMultithreading;

public class Workers {
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Worker();
                }
            }).start();
        }
    }
}
