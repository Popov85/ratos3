package ua.edu.ratos.service.generator.load;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.edu.ratos.service.generator.Rnd;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
@Profile({"dev", "demo"})
public class StartPerformance {

    @Autowired
    private Rnd rnd;

    private static final String START_URL = "http://localhost:8090/student/session/start?schemeId=";

    public void doPerformanceTry(Long schemeId, int threads, long delay) {
        List<Runnable> tasks = new ArrayList<>();
        String url = START_URL + schemeId;
        IntStream.range(1, threads+1).forEach(i -> {
            tasks.add(() -> {
                String login = "name" + i + "." + "surname" + i + "@example.com";
                String password = "name&surname" + i;
                RestTemplate restTemplate = new RestTemplateBuilder().basicAuthorization(login, password).build();
                // Actual test begins
                long start = System.nanoTime();
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                long finish = System.nanoTime();

                long timing = (finish - start) / 1000000;

                log.debug("Timing for task = {} = {}", i, timing);

            });
        });

        try {
            startAllInBatches((int) delay, tasks);
        } catch (Exception e) {
            log.error("Error executing the list of tasks, message = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void startAll(int delay, List<Runnable> tasks) throws InterruptedException {
        for (int i = 0; i < tasks.size(); i++) {
            log.debug("Max memory={} in MB", Runtime.getRuntime().maxMemory() / 1000000);
            log.debug("Total memory={} in MB", Runtime.getRuntime().totalMemory() / 1000000);
            log.debug("Free memory={} in MB", Runtime.getRuntime().freeMemory() / 1000000);
            Thread thread = new Thread(tasks.get(i));
            thread.start();
            int randomDelay = this.rnd.rnd(100, delay);
            log.debug("Delay for task = {} = {}", i, randomDelay);
            Thread.sleep(randomDelay);
        }
    }

    private void startAllInBatches(int delay, List<Runnable> tasks) throws InterruptedException {
        for (int i = 0; i < tasks.size(); i++) {
            log.debug("Max memory={} in MB",Runtime.getRuntime().maxMemory()/1000000);
            log.debug("Total memory={} in MB",Runtime.getRuntime().totalMemory()/1000000);
            log.debug("Free memory={} in MB",Runtime.getRuntime().freeMemory()/1000000);
            Thread thread = new Thread(tasks.get(i));
            thread.start();
            if (i%20==0) {
                int randomDelay = this.rnd.rnd(5000, delay);
                log.debug("Delay for task = {} = {}", i, randomDelay);
                Thread.sleep(randomDelay);
            }
            Thread.sleep(200);
        }
    }
}
