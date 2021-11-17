package client_project.y2s1.team2.graphium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphiumApplication {

    public static void main(String[] args) {
        // Setting the application.properties profile for the application when ran
        System.setProperty("spring.profiles.active", "dev-h2");
//        System.setProperty("spring.profiles.active", "dev-maria");

        SpringApplication.run(GraphiumApplication.class, args);
    }

}
