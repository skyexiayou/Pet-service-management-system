package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.user.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    UserDO selectUserByPhone(@Param("phone") String phone);
    UserDO selectUserByEmail(@Param("email") String email);
    List<UserDO> selectAllUsers();
}