package com.cloneCoin.user.controller;

import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.vo.RequestUser;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.service.UserService;
import com.cloneCoin.user.vo.Greeting;
import com.cloneCoin.user.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting; // 테스트용 VO

    public UserController(UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("User server is working %s", request.getServerPort());
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") Long id) {
        UserDto userDto = userService.getUserById(id);
        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser); // 201 success
    }

//    @PostMapping("/login")
//    public
}
