package cl.smartjobandina.retotecnico.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

import static cl.smartjobandina.retotecnico.infrastucture.output.utility.Constantes.MAIL_INCORRECT;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    @Email(message = MAIL_INCORRECT)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = MAIL_INCORRECT)
    private String email;
    private String password;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private boolean isActive;
    private List<PhoneDto> phones;

}
