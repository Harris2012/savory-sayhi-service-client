package cn.savory.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Program implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("----------------------------");

        List<String> items = discoveryClient.getServices();
        for (String serviceId : items) {

            System.out.println("+++++++++++++++++++++");

            System.out.println(serviceId);
        }

        List<ServiceInstance> list = discoveryClient.getInstances("sayhi-service");
        for (ServiceInstance serviceInstance : list) {

            System.out.println(gson.toJson(serviceInstance));
        }

        for (int i = 0; i < 10; i++) {
            String response = restTemplate.getForEntity("http://sayhi-service/first", String.class).getBody();
            System.out.println(response);
        }

        System.out.println("okok");
    }
}
