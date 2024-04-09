package cl.smartjobandina.retotecnico.domain.service;

import cl.smartjobandina.retotecnico.application.input.UserUseCase;
import cl.smartjobandina.retotecnico.application.output.UserOutputPort;
import cl.smartjobandina.retotecnico.domain.dto.ResponseUser;
import cl.smartjobandina.retotecnico.domain.dto.UserDto;
import cl.smartjobandina.retotecnico.infrastucture.config.security.JwtUtils;
import cl.smartjobandina.retotecnico.infrastucture.output.entity.UserEntity;
import cl.smartjobandina.retotecnico.infrastucture.output.exception.ValidateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserUseCase, UserDetailsService {

    @Autowired
    private final UserOutputPort userOutputPort;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public ResponseUser registerUser(UserDto userDto) throws Exception{
        if(this.userOutputPort.findByEmail(userDto.getEmail())!=null){
            throw new ValidateException("El correo ya registrado");
        }

        Date fecha = new Date();
        userDto.setCreated(fecha);
        userDto.setLastLogin(fecha);
        userDto.setActive(true);
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        String token = this.jwtUtils.generateToken(user);
        user.setToken(token);
        UserEntity userRsp=userOutputPort.registerUser(user);
        UserDto userDtoRsp = modelMapper.map(userRsp, UserDto.class);

        return ResponseUser.builder().user(userDtoRsp).build();
    }

    @Override
    public ResponseUser updaterUser(UserDto userDto) throws Exception {
        UserEntity userEntity = this.userOutputPort.findByEmail(userDto.getEmail());
        if(userEntity==null){
            throw new ValidateException("No se encontró registro");
        }
        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setId(userEntity.getId());
        user.setModified(new Date());
        user.setCreated(userEntity.getCreated());
        user.setLastLogin(userEntity.getLastLogin());
        user.setToken(userEntity.getToken());
        UserEntity userRsp=userOutputPort.registerUser(user);
        UserDto userDtoRsp = modelMapper.map(userRsp, UserDto.class);
        return ResponseUser.builder().user(userDtoRsp).build();
    }

    @Override
    public void deleteUser(String email) throws Exception {
        UserEntity userEntity = this.userOutputPort.findByEmail(email);
        if(userEntity==null){
            throw new ValidateException("No se encontró registro");
        }
        userOutputPort.deleteUser(userEntity);
    }

    @Override
    public ResponseUser findUserByEmail(String email) throws Exception {
        UserEntity userEntity = this.userOutputPort.findByEmail(email);
        if(userEntity==null){
            throw new ValidateException("No se encontró registro");
        }
        UserDto userDtoRsp = modelMapper.map(userEntity, UserDto.class);
        return ResponseUser.builder().user(userDtoRsp).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userOutputPort.findByEmail(username);
    }
}
