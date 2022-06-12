package com.hide.user;

import com.hide.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;


    @Override
    public List<User> getUsers() {
        return repo.getUsers();
    }

    @Override
    public void deleteUser(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        repo.deleteUser(users);
    }
}
