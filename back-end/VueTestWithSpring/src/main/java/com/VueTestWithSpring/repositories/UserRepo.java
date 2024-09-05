package com.VueTestWithSpring.repositories;

import com.VueTestWithSpring.dto.LoginDto;
import com.VueTestWithSpring.dto.UserDto;
import com.VueTestWithSpring.enitites.User;
import com.VueTestWithSpring.exception.AppException;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRepo {

    private final DynamoDBMapper dynamoDBMapper;


    public UserDto save(UserDto dto) {
        var userDto = new UserDto();
        var user = User.builder()
                .id(dto.getId())
                .created_date(dto.getCreated_date())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .build();
        try {
            dynamoDBMapper.save(user);
            userDto.setUser(user);
            userDto.setMessage("Successfully Created");
            userDto.setStatusCode(201);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userDto;
    }

    public UserDto findUsersById(String id) {
        var dto = new UserDto();
        try {
            DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                    .withHashKeyValues(User.builder().id(id).build());
            var userList = dynamoDBMapper.query(User.class, queryExpression);
            dto.setStatusCode(200);
            dto.setUserList(userList);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return dto;
    }

    public UserDto findAllUsers() {
        var dto = new UserDto();
        try {
            var users = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
            dto.setUserList(users);
            dto.setStatusCode(200);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return dto;
    }

    public UserDto deleteUser(String id, String date) {
        var dto = new UserDto();
        try {
            var user = User.builder()
                    .id(id)
                    .created_date(date)
                    .build();
            dynamoDBMapper.delete(user);
            dto.setStatusCode(200);
            dto.setMessage("Successfully deleted");
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return dto;
    }

    public UserDto updateUser(String id, String date, UserDto userDto) {
        var dto = new UserDto();
        try {
            var user = User.builder().id(id)
                    .created_date(date)
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .phoneNumber(userDto.getPhoneNumber())
                    .build();
            dynamoDBMapper.save(user, dynamodbSaveExpression(id, date));
            dto.setUser(user);
            dto.setMessage("Updated Successfully");
            dto.setStatusCode(200);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return dto;
    }

    private DynamoDBSaveExpression dynamodbSaveExpression(String id, String date) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> eav = new HashMap<>();
        eav.put("id", new ExpectedAttributeValue(new AttributeValue().withS(id)));
        eav.put("created_date", new ExpectedAttributeValue(new AttributeValue().withS(date)));
        saveExpression.setExpected(eav);
        return saveExpression;
    }

    public UserDto login(LoginDto dto) {
        var userDto = new UserDto();
        try {
            var user = loginHandler(dto);
            if (user == null) {
                throw new AppException("Email or Password is incorrect!", HttpStatus.NOT_FOUND);
            }
            log.info("Just Test =>" + user);
            userDto.setStatusCode(200);
            userDto.setMessage("Login Successful");
            userDto.setUser(user);
            return userDto;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // this approach is not good when your table is large as it takes many read capacity

    // when your table is large, consider using email as gsi **
    private User loginHandler(LoginDto dto) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":email", new AttributeValue().withS(dto.getEmail()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("email = :email")
                .withExpressionAttributeValues(eav);

        List<User> users = dynamoDBMapper.scan(User.class, scanExpression);


        if (!users.isEmpty() && users.get(0).getPassword().equals(dto.getPassword())) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public UserDto signUp(LoginDto dto) {
        var userDto = new UserDto();
        var user = User.builder()
                .created_date(dto.getDate())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        try {
            dynamoDBMapper.save(user);
            userDto.setUser(user);
            userDto.setMessage("Successfully Sign-up! Please Login back");
            userDto.setStatusCode(200);
            return userDto;
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
