package org.itstep.model.dao;

import org.itstep.model.dao.impl.JDBCDaoFactory;
import org.itstep.model.entity.User;

import java.util.List;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract CourseDao createCourseDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

}
