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

    public User extractFromResultSetP(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setUsername(rs.getString(2));
        user.setUsernameukr(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPassword(rs.getString(5));
        user.setActive(rs.getBoolean(6));
        return user;
    }
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setUsername(rs.getString(2));
        user.setUsernameukr(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setActive(rs.getBoolean(6));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache,
                              User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
