package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private Storage.Users usersRepository = new Storage().new Users();
    private AtomicLong counter = new AtomicLong(-1);
    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        userDto.setId(counter.incrementAndGet());
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        usersRepository.createUser(userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return usersRepository.updateUser(userDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return usersRepository.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        usersRepository.deleteUserById(id);
    }

    @Override
    public List<UserDto> findAllUsers (){
        return  usersRepository.findAllUsers();
    }

}
