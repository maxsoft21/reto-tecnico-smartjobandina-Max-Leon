package cl.smartjobandina.retotecnico.infrastucture.output.utility;

import cl.smartjobandina.retotecnico.infrastucture.config.AppConfig;
import lombok.AllArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
//@NoArgsConstructor
public class Util {

    private final AppConfig appConfig;

    public boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(appConfig.getPasswordRegex());
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
