package com.cloneCoin.user.kafka;

import com.cloneCoin.user.dto.UserDto;
import com.cloneCoin.user.kafka.event.LeaderApplyEventMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserDto send(String kafkaTopic, UserDto userDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("kafka producer send data from the user server" + userDto);

        return userDto;
    }

    public LeaderApplyEventMsg send(String kafkaTopic, LeaderApplyEventMsg leaderApplyEventMsg) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(leaderApplyEventMsg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(kafkaTopic, jsonInString);
        log.info("kafka producer send data from the user server" + leaderApplyEventMsg);

        return leaderApplyEventMsg;
    }

}
