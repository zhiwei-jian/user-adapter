package com.hide.user;

import com.hide.entity.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    void deleteUser(List<User> users);
}
