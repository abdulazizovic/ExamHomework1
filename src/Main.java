import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static ExecutorService executorService= Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LocalDateTime now=LocalDateTime.now();
        int counter=0;
        for (int i = 1; i < 1_000_000; i++) {
            counter+=i;
        }
        System.out.println(counter);
        LocalDateTime end=LocalDateTime.now();
        Duration duration=Duration.between(now,end);
        System.out.println(duration.toMillis());
        LocalDateTime nowForThread=LocalDateTime.now();
        Thread.sleep(1000);
        int counter1=calculate();
        System.out.println(counter1);
        LocalDateTime endForThread=LocalDateTime.now();
        System.out.println("When 5 threads work " +Duration.between(nowForThread,endForThread).toMillis());
    }

    private static int calculate() throws ExecutionException, InterruptedException {
        List<Future<Integer>> futures=new ArrayList<>();
        int step=200_000;
        for (int i = 0; i < 5; i++) {
            final int start=i*step+1;
            final int end=(i+1)*step;

            futures.add(executorService.submit(()->{
                int sum=0;
                for (int j = start; j <= end; j++) {
                    sum+=j;
                }
                return sum;
            }));
        }
        int totalSum=0;
        for (Future<Integer> future : futures) {
            totalSum+=future.get();
        }
        return totalSum;
    }
}