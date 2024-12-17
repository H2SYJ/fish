package team.h2syj.fish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "team.h2syj.fish" })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}