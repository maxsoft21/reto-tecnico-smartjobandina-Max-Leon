package cl.smartjobandina.retotecnico.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {
    private String number;
    private String citycode;
    private String contrycode;
}
