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

import java.util.List;

@DgsComponent
public class UserMutator {

    @Autowired
    private UserRepository userRepository;

    @DgsMutation
    public User createUser(@InputArgument CreateUserInput input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        return userRepository.save(user);
    }

    @DgsMutation
    public User updateUser(@InputArgument Long id, @InputArgument UpdateUserInput input) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        return userRepository.save(user);
    }

    @DgsMutation
    public User deleteUser(@InputArgument Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return user;
    }
}