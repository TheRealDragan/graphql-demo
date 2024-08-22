package com.presscentric.demo.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.presscentric.demo.model.CreateUserInput;
import com.presscentric.demo.model.UpdateUserInput;
import com.presscentric.demo.model.User;
import com.presscentric.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@DgsComponent
public class UserFetcher {

    @Autowired
    private UserRepository userRepository;

    @DgsQuery
    public List<User> users() {
        return userRepository.findAll();
    }

    @DgsQuery
    public User user(@InputArgument Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}