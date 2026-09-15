package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @GetMapping
    public String getUsersControler(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping
    public String addUserControler(@ModelAttribute User user, @RequestParam Set<String> role) {
        //Передаю всю форму сразу через ModelAttribute
        Set<Role> roles = new HashSet<>();
        for ( String roleName : role ) {
            Role userRole = roleRepository.findByRoleName(roleName);
            roles.add(userRole);

        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updateUserControler( Model model, @PathVariable Long id) {
        User user = userService.getUser(id);
        Set<Role> roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return  "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateUserControler(@RequestParam String name,
                                      @RequestParam String password,
                                      @PathVariable Long id,
                                      @RequestParam Set<String> role) {

        User user = userService.getUser(id);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        for ( String roleName : role ) {
            Role selectedRole = roleRepository.findByRoleName(roleName);
            roles.add(selectedRole);
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUserControler(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
