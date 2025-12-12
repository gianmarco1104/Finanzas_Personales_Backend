package finances_practice.gmejia;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GmejiaApplication {

    @Value("${spring.jackson.time-zone}")
    private String timeZone;

	public static void main(String[] args) {
        //Carga el archivo .env
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
        SpringApplication.run(GmejiaApplication.class, args);
	}

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        System.out.println("ZONA HORARIA CONFIGURADA A: " + timeZone);
    }
}
