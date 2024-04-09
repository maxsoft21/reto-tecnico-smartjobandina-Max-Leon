package cl.smartjobandina.retotecnico.infrastucture.input.rest;

import cl.smartjobandina.retotecnico.application.input.UserUseCase;
import cl.smartjobandina.retotecnico.domain.dto.ResponseUser;
import cl.smartjobandina.retotecnico.domain.dto.UserDto;
import cl.smartjobandina.retotecnico.infrastucture.config.AppConfig;
import cl.smartjobandina.retotecnico.infrastucture.output.exception.ValidateException;
import cl.smartjobandina.retotecnico.infrastucture.output.utility.Util;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cl.smartjobandina.retotecnico.infrastucture.output.utility.Constantes.MSG_PASSWORD_INCORRECT;


@RestController
@RequestMapping("/user")
@Validated
@Slf4j
public class UserAdapter {
    @Autowired
    UserUseCase userUseCase;
    @Autowired
    private AppConfig appConfig;

    @ApiOperation(
            value = "Registar Usuario",
            authorizations = {@Authorization(value = "JWT")})
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUser> register(@Valid @RequestBody UserDto user) throws Exception{
        if(!new Util(appConfig).validatePassword(user.getPassword())){
            throw new ValidateException(MSG_PASSWORD_INCORRECT);
        }

        ResponseUser response = userUseCase.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @ApiOperation(
            value = "Actualizar Usuario",
            authorizations = {@Authorization(value = "JWT")})
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUser> update(@Valid @RequestBody UserDto user,@RequestParam String email) throws Exception{
        user.setEmail(email);
        if(!new Util(appConfig).validatePassword(user.getPassword())){
            throw new ValidateException(MSG_PASSWORD_INCORRECT);
        }

        ResponseUser response = userUseCase.updaterUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @ApiOperation(
            value = "Eliminar Usuario",
            authorizations = {@Authorization(value = "JWT")})
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam String email) throws Exception{
        userUseCase.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(
            value = "Listar Usuario",
            authorizations = {@Authorization(value = "JWT")})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUser> getUserEmail(@RequestParam String email) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(userUseCase.findUserByEmail(email));
    }
}
