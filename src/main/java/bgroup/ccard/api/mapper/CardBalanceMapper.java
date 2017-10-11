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
}
