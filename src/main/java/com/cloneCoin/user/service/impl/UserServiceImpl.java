package com.cloneCoin.user.service.impl;

import com.cloneCoin.user.kafka.KafkaProducer;
import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.jpa.UserEntity;
import com.cloneCoin.user.jpa.UserRepository;
import com.cloneCoin.user.kafka.event.LeaderApplyEventMsg;
import com.cloneCoin.user.kafka.event.UserCreateMsg;
import com.cloneCoin.user.service.UserService;
import com.cloneCoin.user.vo.UserBasicFormForApi;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    KafkaProducer kafkaProducer;
    Environment env;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        log.info("loadUserByUsername : {}", userEntity);
        if(userEntity == null)
            throw new UsernameNotFoundException(username);

        return new User(userEntity.getUsername(),
                userEntity.getEncryptedPassword(),
                true, true,
                true, true,
                new ArrayList<>());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, KafkaProducer kafkaProducer, Environment env) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        UserDto newUser = mapper.map(userEntity, UserDto.class);

        UserCreateMsg userCreateMsg = new UserCreateMsg();
        userCreateMsg.setUserId(userEntity.getId());

        // kafka 로 user created 생성 produce 작업 필요
        kafkaProducer.send("user-create-topic", userCreateMsg);
        log.info("message sent by createUser : {}", userCreateMsg);

        return newUser;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity == null) {
            throw new UsernameNotFoundException("user not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }


    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }


    @Override
    public UserDto getUserDetailsByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("user not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto applyLeader(UserBasicFormForApi userform) {

        Optional<UserEntity> userEntity = userRepository.findById(userform.getUserId());

        if (userEntity == null)
            throw new UsernameNotFoundException("user not found");

        UserEntity user = userEntity.get();
        user.setRole("leader");
        user.setSecretKey(userform.getSecretKey());
        user.setApiKey(userform.getApiKey());
        userRepository.save(user);

        ModelMapper mapper = new ModelMapper();
        UserDto updatedUser = mapper.map(user, UserDto.class);

        LeaderApplyEventMsg leaderApplyEventMsg = mapper.map(user, LeaderApplyEventMsg.class);
//        leaderApplyEvent.setEventName("LeaderApplyEvent");
        leaderApplyEventMsg.setLeaderId(user.getId());
//        leaderApplyEventMsg.setLeaderName(user.getUsername());
//        kafkaProducer.send("user-leader-apply-topic", leaderApplyEventMsg);
        kafkaProducer.send("user-topic", leaderApplyEventMsg);

        log.info("message sent by applyLeader : {}", leaderApplyEventMsg);
        return updatedUser;
    }

    @Override
    public UserDto quitLeader(Long id) {

        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity == null)
            throw new UsernameNotFoundException("user not found");

        UserEntity user = userEntity.get();
        user.setRole("normal");
        user.setSecretKey(null);
        user.setApiKey(null);
        userRepository.save(user);

        ModelMapper mapper = new ModelMapper();
        UserDto updatedUser = mapper.map(user, UserDto.class);

        LeaderApplyEventMsg leaderApplyEventMsg = mapper.map(user, LeaderApplyEventMsg.class);
//        leaderApplyEventMsg.setEventName("LeaderQuitEvent");

        kafkaProducer.send("user-leader-apply-topic", leaderApplyEventMsg);
        log.info("message sent by applyLeader : {}", leaderApplyEventMsg);
        return updatedUser;
    }
}
