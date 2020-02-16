package dm.sciencecenter;

import dm.sciencecenter.configs.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import(Config.class)
public class ScienceCenterApplication {

    public static void main(String[] args) { SpringApplication.run(ScienceCenterApplication.class, args); }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
