package cl.smartjobandina.retotecnico.infrastucture.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${password.regex}")
    private String passwordRegex;

    public String getPasswordRegex() {
        return passwordRegex;
    }
}