package sanity.nil.medassurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class MedAssuranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedAssuranceApplication.class, args);
    }

}
