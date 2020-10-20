package org.itstep.model.dao.mapper;

import org.itstep.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("idstuden"));
        user.setUsername(rs.getString("studen.name"));
        user.setUsernameukr(rs.getString("group"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache,
                              User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
