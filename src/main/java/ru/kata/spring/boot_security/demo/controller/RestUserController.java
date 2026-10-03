package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private UserService userService;

    public RestUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers()
                .stream()
                .map(user -> new UserDto(user.getId(),user.getName(), user.getRoles()
                        .stream()
                        .map(role -> role.getRoleName())
                        .toArray(String[]::new)))
                .collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    public void updateUserControler(@PathVariable Long id,
                                    @RequestParam String name,
                                    @RequestParam Set<String> role,
                                    @RequestParam String password) {
        userService.updateUser(name, password, id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping
    public UserDto createUser(@RequestParam  String name,
                           @RequestParam Set<String> role,
                           @RequestParam String password) {
        User user = new User(null, name, password);
        User userCreate = userService.addUser(user, role);
        UserDto userDto = new UserDto(
                userCreate.getId(),
                userCreate.getName(),
                userCreate.getRoles()
                        .stream()
                        .map(roles -> roles.getRoleName())
                        .toArray(String[]::new));
        return userDto;
    }



}
