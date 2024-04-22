package oleksii.leheza.labs;

import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        int numbers = 1000;
        AtomicInteger iterationNumber = new AtomicInteger();
        Drop drop = new Drop();
        new Thread(new Producer(drop,numbers)).start();
        new Thread(new Consumer(drop,iterationNumber)).start();

//        new Thread(new Producer(drop, numbers)).start();
//        for (int i = 0; i < 10; i++) {
//            new Thread(new Consumer(drop, iterationNumber)).start();
//        }

        //        for (int i = 0; i < 10; i++) {
//            new Thread(new Producer(drop, numbers)).start();
//        }
//        new Thread(new Consumer(drop, iterationNumber)).start();

//        for (int i = 0; i < 2; i++) {
//            new Thread(new Producer(drop, numbers)).start();
//        }
//        for (int i = 0; i < 10; i++) {
//            new Thread(new Consumer(drop, iterationNumber)).start();
//        }



//        for (int i = 0; i < 10; i++) {
//            new Thread(new Producer(drop, numbers)).start();
//        }
//        for (int i = 0; i < 2; i++) {
//            new Thread(new Consumer(drop, iterationNumber)).start();
//        }
    }
}