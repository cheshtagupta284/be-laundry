package com.cheshtagupta.laundry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("register")
    public User create(@Validated @RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @PostMapping("")
    public User get(@RequestBody User userinput) {
        User user = userRepository.findByEmail(userinput.getEmail());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "user not found"
            );
        }
        if (!Objects.equals(user.getPassword(), userinput.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "invalid password"
            );
        }
        return user;
    }

    @PatchMapping("")
    public User update(@RequestBody User userinput) {
        User user = userRepository.findByEmail(userinput.getEmail());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "user not found"
            );
        }
        if (userinput.getFName() != null)
            user.setFName(userinput.getFName());
        if (userinput.getLName() != null)
            user.setLName(userinput.getLName());
        if (userinput.getPassword() != null)
            user.setPassword(userinput.getPassword());
        if (userinput.getNotificationTime() != null)
            user.setNotificationTime(userinput.getNotificationTime());
        if (userinput.getNotificationDays() != 0)
            user.setNotificationDays(userinput.getNotificationDays());
        userRepository.save(user);
        return user;
    }

    @DeleteMapping("")
    public void delete(@RequestBody User userinput) {
        User user = userRepository.findByEmail(userinput.getEmail());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "user not found"
            );
        }
        userRepository.delete(user);
    }
}
