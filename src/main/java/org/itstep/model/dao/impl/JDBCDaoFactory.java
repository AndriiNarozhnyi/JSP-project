package org.itstep.model.dao.impl;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.DaoFactory;
import org.itstep.model.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = org.itstep.model.dao.impl.ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new org.itstep.model.dao.impl.JDBCUserDao(getConnection());
    }
    @Override
    public CourseDao createCourseDao() {
        return new org.itstep.model.dao.impl.JDBCCourseDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
