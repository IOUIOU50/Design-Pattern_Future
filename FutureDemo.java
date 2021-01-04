import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    static Future<Data> setup() {
        FutureTask<Data> future = new FutureTask<Data>(() ->{
            Data data = new Data();
            for (int i = 1; i <= 10; i++) {
                System.out.println(i * 10 + "%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            data.setData("done");
            return data;
        });
        new Thread(future).start();
        return future;
    }
}
