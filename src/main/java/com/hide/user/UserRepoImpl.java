package com.hide.user;

import com.hide.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl implements UserRepo {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<User> getUsers() {
        List<User> users = template.query("SELECT * FROM users", getUserRowMapper());
        return users;
    }

    private RowMapper<User> getUserRowMapper(){
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setNickName(resultSet.getString("nickname"));
                user.setHobby(resultSet.getString("hobby"));
                return null;
            }
        };
    }
}
