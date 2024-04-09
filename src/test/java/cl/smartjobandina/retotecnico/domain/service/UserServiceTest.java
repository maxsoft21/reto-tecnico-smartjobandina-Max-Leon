package cl.smartjobandina.retotecnico.domain.service;

import cl.smartjobandina.retotecnico.application.output.UserOutputPort;
import cl.smartjobandina.retotecnico.domain.dto.ResponseUser;
import cl.smartjobandina.retotecnico.domain.dto.UserDto;
import cl.smartjobandina.retotecnico.infrastucture.config.security.JwtUtils;
import cl.smartjobandina.retotecnico.infrastucture.output.entity.UserEntity;
import cl.smartjobandina.retotecnico.infrastucture.output.exception.ValidateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserOutputPort userOutputPort;
    @Mock
    ModelMapper modelMapper;

    @Mock
    private JwtUtils jwtUtils;

    private static String EMAIL = "aaaaaaa@dominio.cl";

    @Test
    void testRegistrationWithEmailAlreadyExists() {
        String existingEmail = EMAIL;
        when(userOutputPort.findByEmail(existingEmail)).thenReturn(new UserEntity());

        UserDto userDto = new UserDto();
        userDto.setEmail(existingEmail);
        ValidateException exception = assertThrows(ValidateException.class, () -> {
            userService.registerUser(userDto);
        });
        assertEquals("El correo ya registrado", exception.getMessage());
    }

    @Test
    void testRegistrationWithNewEmail() {
        String newEmail = "new@dominio.cl";
        when(userOutputPort.findByEmail(newEmail)).thenReturn(null);
        UserDto userDto = new UserDto();
        userDto.setEmail(newEmail);
        when(jwtUtils.generateToken(any())).thenReturn("");
        when(userOutputPort.registerUser(any())).thenReturn(new UserEntity());
        assertDoesNotThrow(() -> {
            userService.registerUser(userDto);
        });
    }

    @Test
    void testUpdateUserWithExistingEmail() throws Exception {
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setEmail(EMAIL);
        when(userOutputPort.findByEmail(existingUserEntity.getEmail())).thenReturn(existingUserEntity);
        UserDto userDto = new UserDto();
        userDto.setEmail(existingUserEntity.getEmail());
        when(userOutputPort.registerUser(any())).thenReturn(existingUserEntity);

        assertDoesNotThrow(() -> {
            ResponseUser response = userService.updaterUser(userDto);
            assertNotNull(response);
            assertNotNull(response.getUser());
            assertEquals(existingUserEntity.getEmail(), response.getUser().getEmail());
        });
    }

    @Test
    void testUpdateUserWithNonExistingEmail() throws Exception {
        String nonExistingEmail = EMAIL;
        UserDto userDto = new UserDto();
        userDto.setEmail(nonExistingEmail);

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            userService.updaterUser(userDto);
        });

        assertEquals("No se encontró registro", exception.getMessage());
    }

    @Test
    void testDeleteUserWithExistingEmail() throws Exception {
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setEmail(EMAIL);
        when(userOutputPort.findByEmail(existingUserEntity.getEmail())).thenReturn(existingUserEntity);

        assertDoesNotThrow(() -> {
            userService.deleteUser(existingUserEntity.getEmail());
        });
    }

    @Test
    void testDeleteUserWithNonExistingEmail() throws Exception {
        String nonExistingEmail = EMAIL;
        when(userOutputPort.findByEmail(nonExistingEmail)).thenReturn(null);

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            userService.deleteUser(nonExistingEmail);
        });

        assertEquals("No se encontró registro", exception.getMessage());
    }
    @Test
    void testFindUserByEmailWithExistingEmail() throws Exception {
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setEmail(EMAIL);
        when(userOutputPort.findByEmail(existingUserEntity.getEmail())).thenReturn(existingUserEntity);

        ResponseUser responseUser = userService.findUserByEmail(existingUserEntity.getEmail());

        assertNotNull(responseUser);
        assertEquals(existingUserEntity.getEmail(), responseUser.getUser().getEmail());
    }

    @Test
    void testFindUserByEmailWithNonExistingEmail() {
        String nonExistingEmail = EMAIL;
        when(userOutputPort.findByEmail(nonExistingEmail)).thenReturn(null);

        ValidateException exception = assertThrows(ValidateException.class, () -> {
            userService.findUserByEmail(nonExistingEmail);
        });

        assertEquals("No se encontró registro", exception.getMessage());
    }

    @Test
    void testLoadUserByUsernameWithExistingUsername() {
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setEmail(EMAIL);
        when(userOutputPort.findByEmail(existingUserEntity.getEmail())).thenReturn(existingUserEntity);

        UserDetails userDetails = userService.loadUserByUsername(existingUserEntity.getEmail());

        assertNotNull(userDetails);
        assertEquals(existingUserEntity.getEmail(), userDetails.getUsername());
    }
}