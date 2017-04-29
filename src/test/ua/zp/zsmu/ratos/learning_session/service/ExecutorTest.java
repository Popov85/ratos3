package ua.zp.zsmu.ratos.learning_session.service;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andrey on 4/26/2017.
 */
public class ExecutorTest {

        @Test
        public void testExecute() {
                System.out.println("Thread: "+this);
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                        @Override
                        public void run() {
                                System.out.println("New thread: "+this);
                        }
                });
        }
}
