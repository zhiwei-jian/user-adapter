package com.hide.user;

import com.hide.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/api/hello")
    public String hello(@RequestParam String name) {
        return "Hello " + name + "!";
    }

    @RequestMapping("/api/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/api/users")
    public void deleteUsers(@RequestBody List<User> users){
        userService.deleteUser(users);
    }

}
