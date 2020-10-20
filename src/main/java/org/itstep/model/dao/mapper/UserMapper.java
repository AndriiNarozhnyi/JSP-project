package org.itstep.model.dao.mapper;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMapper implements ObjectMapper<User> {
    public static Map<String, Role> roleMap = Stream.of(new Object[][] {
            {"ADMIN", Role.ADMIN},
            {"USER", Role.USER},
            {"TEACHER", Role.TEACHER},
            {"UNKNOWN", Role.UNKNOWN}
    }).collect(Collectors.toMap(data-> (String)data[0], data->(Role)data[1]));

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setUsernameukr(rs.getString("usernameukr"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("active"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache,
                              User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
