package com.example.userservice.service;



import com.example.userservice.OrderServiceClient;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final OrderServiceClient orderServiceClient;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow((() ->
                new RuntimeException("User not found")));
    }

    public void deleteUserById(Long userId) {

        orderServiceClient.deleteUser(userId);
        userRepository.deleteById(userId);
    }
}