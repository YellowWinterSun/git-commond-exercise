package com.dy.demo0.dao.mapper;

import com.dy.demo0.dao.model.Student;
import com.dy.demo0.dao.model.StudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper {
    int countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(String studentid);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(String studentid);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    @Update("update student set identityCard=#{newIdCard}, name=#{newName} " +
            "where studentId=#{id} and identityCard=#{oldIdCard}")
    int updateTest(@Param("id") String id, @Param("newName") String newName, @Param("newIdCard") String newIdCard,
                   @Param("oldIdCard") String oldIdCard);

    @Update("update student set name=#{newName} where studentId=#{id}")
    int updateTest2(@Param("id") String id, @Param("newName") String newName);
}