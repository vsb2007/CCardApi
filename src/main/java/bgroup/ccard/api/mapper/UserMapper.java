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

    /*
    @Insert("insert into page(page_id,page_header,page_date_create) values(#{pageId},#{pageHeader},#{pageDateCreate})")
    @SelectKey(statement = " SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertDataPage(DataPage dataPage);
    */

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
/*
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "pageId", column = "page_id"),
            @Result(property = "pageHeader", column = "page_header"),
            @Result(property = "pageDamage", column = "page_damage"),
            @Result(property = "pageDescription", column = "page_description"),
            @Result(property = "pageDateCreate", column = "page_date_create"),
            @Result(property = "pageChance", column = "page_chance")
    })
    @Select("select * from page")
    List<DataPage> findAllPages();
*/
}
