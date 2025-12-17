package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserById(@Param("userId") Integer userId);
    User selectUserByPhone(@Param("phone") String phone);
    List<User> selectAllUsers();
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(@Param("userId") Integer userId);
}