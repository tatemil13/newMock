package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import java.math.BigDecimal;

@RestController
public class MainController {

    private Logger log  = LoggerFactory.getLogger(MainController.class);
    private Random random = new Random();

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId  = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String currency;

            //String RqUID = requestDTO.getRqUID();

            if (firstDigit == '8'){
               maxLimit = new BigDecimal(2000);
               currency = "US";
            }else if (firstDigit == '9'){
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            }else{
                maxLimit = new BigDecimal(10000);
                currency = "RU";
            }
            BigDecimal balance = maxLimit.multiply(new BigDecimal(random.nextDouble())).setScale(2, RoundingMode.HALF_UP);

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("*********** request **************" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("*********** response **************" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return ResponseEntity.ok(responseDTO);



        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }













































}
