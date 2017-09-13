package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.CardBalance;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * Created by VSB on 10.08.2017.
 * httpClientConfluence
 */
@Service
public interface CardBalanceMapper {

    /*
    @Insert("insert into page(page_id,page_header,page_date_create) values(#{pageId},#{pageHeader},#{pageDateCreate})")
    @SelectKey(statement = " SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertDataPage(DataPage dataPage);
    */

    @Results({
            @Result(property = "cardNumber", column = "enum"),
            @Result(property = "balance", column = "bonus")
    })
    @Select("select\n" +
            "card.ElectronicNumber as enum,\n" +
            "round(synthetic_account.BonusCurrent,2) as bonus\n" +
            "from card\n" +
            "LEFT JOIN person on card.PersonKey = person.PersonKey\n" +
            "LEFT JOIN synthetic_account ON synthetic_account.PersonKey = person.PersonKey\n" +
            "where card.ElectronicNumber = #{cardNumber}")
    public CardBalance findCardBalanceByNumber(String cardNumber);
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
