package org.itstep.model.dao.impl;

import lombok.Builder;
import org.itstep.model.dao.Pageable;
import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.UserPage;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.dao.mapper.UserMapper;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import sun.jvm.hotspot.debugger.Page;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao, SQLConstants{

    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS)){
            ps.setString( 1, user.getUsername());
            ps.setString( 2, user.getUsernameukr());
            ps.setString( 3, user.getEmail());
            ps.setString( 4, user.getPassword());
            ps.setBoolean( 5, user.isActive());

            ResultSet rs;
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(((Long) rs.getLong(1)));
                }
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        for (Role role : user.getRoles()){
            try(PreparedStatement ps = connection.prepareCall(SQL_ADD_ROLE_FOR_USER)){
                ps.setLong(1, user.getId());
                ps.setString( 2, role.toString());
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public Optional<User> findById(Long id) {
        Map<Long, User> users = new HashMap<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_FIND_USER_BY_ID)){
            ps.setLong( 1, id);
            ResultSet rs = ps.executeQuery();
            User user = new User();

            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);
                users.get(user.getId()).getRoles().add((userMapper.roleMap.get(rs.getString(8))));
            }
            return Optional.of(users.get(user.getId()));


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return null;
    }

    public Optional<User> findForLogin (String username){
        Map<Long, User> users = new HashMap<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_FIND_USER_FOR_LOGIN)){
            ps.setString( 1, username);
            ResultSet rs = ps.executeQuery();
            User user = new User();

            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                user = userMapper
                        .extractFromResultSetP(rs);
                user = userMapper
                        .makeUnique(users, user);
                users.get(user.getId()).getRoles().add((userMapper.roleMap.get(rs.getString(8))));
            }
            return Optional.of(users.get(user.getId()));


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }



    }

    @Override
    public boolean saveEditedUser(User user) {
        boolean res1 = false;
        boolean res2= false;
        boolean res3= false;
        try(PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_USER)){
            ps.setString( 1, user.getUsername());
            ps.setString( 2, user.getUsernameukr());
            ps.setString( 3, user.getEmail());
            ps.setBoolean( 4, user.isActive());
            ps.setLong(5, user.getId());

           int rs = ps.executeUpdate();
            if (rs > 0) {
                res1 = true;
                }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE_USER_ROLES)){
            ps.setLong( 1, user.getId());

            int rs = ps.executeUpdate();
            if (rs > 0) {
                res2 = true;
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        for (Role role : user.getRoles()){
            try(PreparedStatement ps = connection.prepareCall(SQL_ADD_ROLE_FOR_USER)){
                ps.setLong(1, user.getId());
                ps.setString( 2, role.toString());
                res3 = ps.executeUpdate()>0;
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }
        return res1&&res2&&res3;
    }

    @Override
    public UserPage findUsersByFilter(String fusername, String fusernameukr, Pageable pageable) {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Course> courses = new HashMap<>();
        Set<Role> roles = new HashSet<>();
        int offset = pageable.getPage()*pageable.getSize() - pageable.getSize();
        int numberOfRecords = pageable.getSize();
        int numberOfRowsDb = 0;
        List<Long> ids = new ArrayList<>();
        UserPage page = new UserPage();

        try(PreparedStatement stForPag = connection.prepareStatement(SQL_USER_FOR_PAGE_FILTER)){
            stForPag.setString(1,"%" + fusername + "%");
            stForPag.setString(2,"%" + fusernameukr + "%");
            stForPag.setInt(3,offset);
            stForPag.setInt(4, numberOfRecords);
            ResultSet rs = stForPag.executeQuery();
            while (rs.next()){
                ids.add(rs.getLong(1));
            }
            rs = stForPag.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next())
                numberOfRowsDb = rs.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        if (ids.size()==0){
            page.setEntities(new ArrayList<>());
            return page;
        }
        String query = Utils.queryBuilder(ids, SQL_USER_TEMPLATE);

        try (Statement ps = connection.createStatement()) {

            ResultSet rs = ps.executeQuery(query);

            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()){
                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);
                users.get(user.getId()).getRoles().add((userMapper.roleMap.get(rs.getString(8))));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        page.setPageNumber(pageable.getPage());
        page.setTotalPages(numberOfRowsDb%numberOfRecords==0
                ?numberOfRowsDb/numberOfRecords:numberOfRowsDb/numberOfRecords+1);
        page.setSize(pageable.getSize());
        page.setEntities(new ArrayList<>(users.values()));
        page.setTotalRows(numberOfRowsDb);
        return page;
    }

    @Override
    public List<User> getAllTeachers() {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Course> courses = new HashMap<>();
        Set<Role> roles = new HashSet<>();

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL_FIND_ALL_TEACHERS);

            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);
                users.get(user.getId()).getRoles().add((userMapper.roleMap.get(rs.getString(8))));

            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserPage findAllPageable(Pageable pageable) {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Course> courses = new HashMap<>();
        Set<Role> roles = new HashSet<>();
        int offset = pageable.getPage()*pageable.getSize() - pageable.getSize();
        int numberOfRecords = pageable.getSize();
        int numberOfRowsDb = 0;
        List<Long> ids = new ArrayList<>();
        UserPage page = new UserPage();

        try(PreparedStatement stForPag = connection.prepareStatement(SQL_USER_FOR_PAGE)){
            stForPag.setInt(1,offset);
            stForPag.setInt(2, numberOfRecords);
            ResultSet rs = stForPag.executeQuery();
            while (rs.next()){
                ids.add(rs.getLong(1));
            }
            rs = stForPag.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next())
                numberOfRowsDb = rs.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        String query = Utils.queryBuilder(ids, SQL_USER_TEMPLATE);

        try (Statement ps = connection.createStatement()) {

            ResultSet rs = ps.executeQuery(query);

            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()){
                User user = userMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);
                    users.get(user.getId()).getRoles().add((userMapper.roleMap.get(rs.getString(8))));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        page.setPageNumber(pageable.getPage());
        page.setTotalPages(numberOfRowsDb%numberOfRecords==0
                ?numberOfRowsDb/numberOfRecords:numberOfRowsDb/numberOfRecords+1);
        page.setSize(pageable.getSize());
        page.setEntities(new ArrayList<>(users.values()));
        page.setTotalRows(numberOfRowsDb);
        return page;
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
