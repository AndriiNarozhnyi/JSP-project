package org.itstep.model.dao.impl;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.CoursePage;
import org.itstep.model.dao.Pageable;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.dao.mapper.UserMapper;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.itstep.model.dao.impl.SQLConstants.*;

public class JDBCCourseDao implements CourseDao {
    private Connection connection;
    public JDBCCourseDao(Connection connection) {
        this.connection = connection;
    }


    public boolean createCourse(Course course) throws SQLException {
        try{
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_NEW_COURSE, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, course.getName());
            ps.setString( 2, course.getNameukr());
            ps.setString( 3, course.getTopic());
            ps.setString( 4, course.getTopicukr());
            ps.setDate( 5, Date.valueOf(course.getEndDate()));
            ps.setLong( 6, course.getDuration());
            ps.setDate( 7, Date.valueOf(course.getStartDate()));
            ps.setLong( 8, course.getTeacher().getId());

            ResultSet rs;
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    course.setId(((Long) rs.getLong(1)));
                }
            }

        PreparedStatement psc = connection.prepareStatement(SQL_CHECK_TEACHER);
                    psc.setLong(1, course.getTeacher().getId());
                    ResultSet rsc = psc.executeQuery();
                    int count = 0;
            while (rsc.next()) {
                count = rsc.getInt(1);
            }
                    if (count == 0) {
                        PreparedStatement psi = connection.prepareStatement(SQL_SET_TEACHER);
                        psi.setLong(1, course.getTeacher().getId());
                        ResultSet rsi = psi.executeQuery();
                    }
                    connection.commit();

        }
        catch (SQLException ex) {
            // (1) write to log
            connection.rollback();
        }
        return true;
    }

    @Override
    public CoursePage findAllPageable(Pageable pageable, String queryMade) {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Course> courses = new HashMap<>();
        int offset = pageable.getPage()*pageable.getSize() - pageable.getSize();
        int numberOfRecords = pageable.getSize();
        int numberOfRowsDb = 0;
        pageable.setSort("c.start_date");
        List<Long> ids = new ArrayList<>();
        CoursePage page = new CoursePage();

        try(PreparedStatement stForPag = connection.prepareStatement(queryMade)){
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
        String query = Utils.queryBuilder(ids, SQL_COURSE_TEMPLATE, pageable);
        page.setEntities((List<Course>) getDataBaseRows(courses, query));
        page.setPageNumber(pageable.getPage());
        page.setTotalPages(numberOfRowsDb%numberOfRecords==0
                ?numberOfRowsDb/numberOfRecords:numberOfRowsDb/numberOfRecords+1);
        page.setSize(pageable.getSize());
        page.setTotalRows(numberOfRowsDb);
        return page;
    }

    @Override
    public CoursePage findByFilterDispatcher(Pageable pageable, Map<String, String> paramMap, String path, Long userId) {
        StringBuilder sb = new StringBuilder(SQL_COURSE_FILTER);
        String queryMade = "";
        switch (path){
            case "/teacher/cabinet":
                queryMade = maKeQuery(sb, 300, "and c.usr_id="+userId + " ");
                break;
            case "/user/cabinet":
                queryMade = maKeQuery(sb, 300, "and uhc.usr_id="+userId+" ");
                break;
            default:
                queryMade = sb.toString();
                break;
        }

        return findByFilter (pageable, paramMap, queryMade);
    }
    private String maKeQuery(StringBuilder sb, int offset, String addition){
        return sb.insert(offset, addition).toString();
    }

    @Override
    public void enrollUser(Long courseId, Long userId) {
        try(PreparedStatement ps = connection.prepareStatement(SQL_ENROLL_USER)){
            ps.setLong(1,userId);
            ps.setLong(2, courseId);

            if (ps.executeUpdate() > 0) {
                return;
            } else {
                throw new RuntimeException("Enrollment was not done correctly");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void unenrollUser(long courseId, Long userId) {
        try(PreparedStatement ps = connection.prepareStatement(SQL_UNENROLL_USER)){
            ps.setLong(1,userId);
            ps.setLong(2, courseId);

            if (ps.executeUpdate() > 0) {
                return;
            } else {
                throw new RuntimeException("Unenrollment was not done correctly");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public CoursePage findAllDispatcher(Pageable pageable, String path, Long userId) {
        StringBuilder sb = new StringBuilder(SQL_COURSE_FOR_PAGE);
        String queryMade = "";
        switch (path){
            case "/teacher/cabinet":
                queryMade = maKeQuery(sb, 131,"where c.usr_id="+userId + " ");
                break;
            case "/user/cabinet":
                queryMade = maKeQuery(sb, 131, "where uhc.usr_id="+userId+" ");
                break;
            default:
                queryMade = sb.toString();
                break;
        }
        return findAllPageable (pageable, queryMade);
    }

    private CoursePage findByFilter(Pageable pageable, Map<String, String> paramMap, String queryMade) {
        Map<Long, Course> courses = new HashMap<>();
        CoursePage page = new CoursePage();
        int offset = pageable.getPage()*pageable.getSize() - pageable.getSize();
        int numberOfRecords = pageable.getSize();
        pageable.setSort("c.start_date");
        List<Object> res = getIdsForFilter(offset, numberOfRecords, queryMade, paramMap);
        List<Long> ids = (List<Long>) res.get(0);
        int numberOfRowsDb = (int) res.get(1);

        String query = Utils.queryBuilder(ids, SQL_COURSE_TEMPLATE, pageable);
        page.setEntities((List<Course>) getDataBaseRows(courses, query));
        page.setPageNumber(pageable.getPage());
        page.setTotalPages(numberOfRowsDb%numberOfRecords==0
                ?numberOfRowsDb/numberOfRecords:numberOfRowsDb/numberOfRecords+1);
        page.setSize(pageable.getSize());
        page.setTotalRows(numberOfRowsDb);
        return page;
    }
    private List<Object> getIdsForFilter(int offset, int numberOfRecords, String queryMade,
                                         Map<String, String> paramMap){
        List<Object> res = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        int numberOfRowsDb = 0;
        try(PreparedStatement stForPag = connection.prepareStatement(queryMade)){
            stForPag.setString(1,"%"+paramMap.get("fname")+"%");
            stForPag.setString(2,"%"+paramMap.get("fnameukr")+"%");
            stForPag.setString(3,"%"+paramMap.get("ftopic")+"%");
            stForPag.setString(4,"%"+paramMap.get("ftopicukr")+"%");
            stForPag.setDate(5, Date.valueOf(LocalDate.parse(paramMap.get("fstartDate")==""
                    ?"0002-02-02":paramMap.get("fstartDate"))));
            stForPag.setInt(6, Integer.parseInt(paramMap.get("fdurationMin")==""?"0":paramMap.get("fdurationMin")));
            stForPag.setInt(7, Integer.parseInt(paramMap.get("fdurationMax")==""?"9999":paramMap.get("fdurationMax")));
            stForPag.setDate(8, Date.valueOf(LocalDate.parse(paramMap.get("fendDate")==""
                    ?"9999-02-02":paramMap.get("fendDate"))));
            stForPag.setString(9, "%" + paramMap.get("fteacher")+"%");

            stForPag.setInt(10,offset);
            stForPag.setInt(11, numberOfRecords);
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
        res.add(new ArrayList<>(ids));
        res.add(numberOfRowsDb);
        return res;
    }

    private Object getDataBaseRows(Map<Long, Course> courses, String query) {
        try (Statement ps = connection.createStatement()) {
        ResultSet rs = ps.executeQuery(query);

        CourseMapper courseMapper = new CourseMapper();
        UserMapper userMapper = new UserMapper();

        while (rs.next()) {
            Course course = courseMapper.extractFromResultSet(rs);
            course = courseMapper.makeUnique(courses, course);
            User teacher = userMapper.extractFromResultSetC(rs);
            courses.get(course.getId()).setTeacher(teacher);
            if (rs.getLong(10)!=0) {
                courses.get(course.getId()).getEnrolledStudents().add(rs.getLong(10));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return new ArrayList<>(courses.values());
    }

    @Override
    public void create(Course entity) {

    }

    @Override
    public Optional<Course> findById(Long id) {
        Map<Long, Course> courses = new HashMap<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_FIND_COURSE_BY_ID)){
            ps.setLong( 1, id);
            ResultSet rs = ps.executeQuery();
            Course course = new Course();

            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                course = courseMapper.extractFromResultSet(rs);
                course = courseMapper.makeUnique(courses, course);
                User teacher = userMapper.extractFromResultSetC(rs);
                courses.get(course.getId()).setTeacher(teacher);
                if (rs.getLong(10)!=0) {
                    courses.get(course.getId()).getEnrolledStudents().add(rs.getLong(10));
                }
            }
            return Optional.of(courses.get(course.getId()));


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Course> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void update(Course course) throws SQLException {
        try{
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_COURSE);
            ps.setString( 1, course.getName());
            ps.setString( 2, course.getNameukr());
            ps.setString( 3, course.getTopic());
            ps.setString( 4, course.getTopicukr());
            ps.setDate( 5, Date.valueOf(course.getEndDate()));
            ps.setLong( 6, course.getDuration());
            ps.setDate( 7, Date.valueOf(course.getStartDate()));
            ps.setLong( 8, course.getTeacher().getId());
            ps.setLong(9, course.getId());

            ps.executeUpdate();


            PreparedStatement psc = connection.prepareStatement(SQL_CHECK_TEACHER);
            psc.setLong(1, course.getTeacher().getId());
            ResultSet rsc = psc.executeQuery();
            int count = 0;
            while (rsc.next()) {
                count = rsc.getInt(1);
            }
            if (count == 0) {
                PreparedStatement psi = connection.prepareStatement(SQL_SET_TEACHER);
                psi.setLong(1, course.getTeacher().getId());
                ResultSet rsi = psi.executeQuery();
            }
            connection.commit();

        }
        catch (SQLException ex) {
            // (1) write to log
            connection.rollback();
        }
    }

    @Override
    public void delete(long courseId) throws SQLException {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement ps1 = connection.prepareStatement(SQL_CLEAR_ENROLLMENT);
            ps1.setLong(1, courseId);
            int rows1 = ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement(SQL_DELETE_COURSE_BY_ID);
            ps2.setLong(1, courseId);
            int rows2 = ps2.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            // (1) write to log
            connection.rollback();
        }
    }

        @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkNameDateTeacher(String name, String startDate, Long teacherId) {
        boolean res = false;
        try(PreparedStatement ps = connection.prepareStatement(SQL_CHECK_NAME_DATE_TEACHER)){
            ps.setString(1, name);
            ps.setString(2, startDate);
            ps.setLong(3, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getLong(1)>0) {
                res = true;
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }


}
