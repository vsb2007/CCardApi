package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Created by VSB on 10.08.2017.
 * httpClientConfluence
 */
@Service
public interface UserMapper {
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    @Select("select\n" +
            "username,\n" +
            "password\n" +
            "from my_users\n" +
            "where username = #{username}\n"
    )
    public User findUserByLogin(
            @Param(value = "username") String username
    );
}
