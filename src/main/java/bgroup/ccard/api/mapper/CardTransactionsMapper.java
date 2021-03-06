package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.CardTransactions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by VSB on 10.08.2017.
 * httpClientConfluence
 */
@Service
public interface CardTransactionsMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "idTr", column = "tr_key"),
            @Result(property = "date", column = "date_tr"),
            @Result(property = "operation", column = "operation"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "station", column = "station")
    })
    @Select("SELECT @id := @id + 1 AS id\n" +
            ", ss.*\n" +
            "from \n" +
            "(\n" +
            "SELECT\n" +
            "AccountProtocol.AccountProtocolKey as tr_key,\n" +
            "AccountProtocol.TransferDatetime as date_tr,\n" +
            "'withdraw' as operation,\n" +
            "round(AccountProtocol.TransferBonus,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join AccountProtocol On AccountProtocol.SourceAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON AccountProtocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber = #{cardNumber}\n" +
            "union\n" +
            "SELECT\n" +
            "bonusprotocol.BonusProtocolKey as tr_key,\n" +
            "bonusprotocol.BonusChangeDatetime as date_tr,\n" +
            "'admission' as operation,\n" +
            "round(bonusprotocol.ChargeBonusSum,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join bonusprotocol On bonusprotocol.SyntheticAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON bonusprotocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber =  #{cardNumber}\n" +
            ")ss, (select @id:=0) AS z\n" +
            "where ss.tr_key is not null\n" +
            "and ss.date_tr is not null\n" +
            "and ss.amount is not null\n" +
            "and ss.station is not null\n" +
            "order by ss.date_tr\n"
    )
    public List<CardTransactions> findCardTransactionsByNumber(String cardNumber);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "idTr", column = "tr_key"),
            @Result(property = "date", column = "date_tr"),
            @Result(property = "operation", column = "operation"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "station", column = "station")
    })
    @Select("SELECT @id := @id + 1 AS id\n" +
            ", ss.*\n" +
            "from \n" +
            "(\n" +
            "SELECT\n" +
            "AccountProtocol.AccountProtocolKey as tr_key,\n" +
            "AccountProtocol.TransferDatetime as date_tr,\n" +
            "'withdraw' as operation,\n" +
            "round(AccountProtocol.TransferBonus,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join AccountProtocol On AccountProtocol.SourceAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON AccountProtocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber = #{cardNumber}\n" +
            "union\n" +
            "SELECT\n" +
            "bonusprotocol.BonusProtocolKey as tr_key,\n" +
            "bonusprotocol.BonusChangeDatetime as date_tr,\n" +
            "'admission' as operation,\n" +
            "round(bonusprotocol.ChargeBonusSum,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join bonusprotocol On bonusprotocol.SyntheticAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON bonusprotocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber =  #{cardNumber}\n" +
            ")ss, (select @id:=0) AS z\n" +
            "where ss.tr_key is not null\n" +
            "and ss.date_tr is not null\n" +
            "and ss.amount is not null\n" +
            "and ss.station is not null\n" +
            "and ss.date_tr >= #{dateStart} and ss.date_tr < #{dateEnd} + interval 1 day\n" +
            "order by ss.date_tr\n"
    )
    public List<CardTransactions> findCardTransactionsByNumberAndDate(
            @Param(value = "cardNumber") String cardNumber
            , @Param(value = "dateStart") String dateStart
            , @Param(value = "dateEnd") String dateEnd);


    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "idTr", column = "tr_key"),
            @Result(property = "date", column = "date_tr"),
            @Result(property = "operation", column = "operation"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "station", column = "station")
    })
    @Select("SELECT @id := @id + 1 AS id\n" +
            ", ss.*\n" +
            "from \n" +
            "(\n" +
            "SELECT\n" +
            "AccountProtocol.AccountProtocolKey as tr_key,\n" +
            "AccountProtocol.TransferDatetime as date_tr,\n" +
            "'withdraw' as operation,\n" +
            "round(AccountProtocol.TransferBonus,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join AccountProtocol On AccountProtocol.SourceAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON AccountProtocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber = #{cardNumber}\n" +
            "union\n" +
            "SELECT\n" +
            "bonusprotocol.BonusProtocolKey as tr_key,\n" +
            "bonusprotocol.BonusChangeDatetime as date_tr,\n" +
            "'admission' as operation,\n" +
            "round(bonusprotocol.ChargeBonusSum,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join bonusprotocol On bonusprotocol.SyntheticAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON bonusprotocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber =  #{cardNumber}\n" +
            ")ss, (select @id:=0) AS z\n" +
            "where ss.tr_key is not null\n" +
            "and ss.date_tr is not null\n" +
            "and ss.amount is not null\n" +
            "and ss.station is not null\n" +
            "and ss.station = #{station}\n" +
            "order by ss.date_tr\n"
    )
    List<CardTransactions> findCardTransactionsByNumberAndStation(
            @Param(value = "cardNumber") String cardNumber
            , @Param(value = "station") String station
    );

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "idTr", column = "tr_key"),
            @Result(property = "date", column = "date_tr"),
            @Result(property = "operation", column = "operation"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "station", column = "station")
    })
    @Select("SELECT @id := @id + 1 AS id\n" +
            ", ss.*\n" +
            "from \n" +
            "(\n" +
            "SELECT\n" +
            "AccountProtocol.AccountProtocolKey as tr_key,\n" +
            "AccountProtocol.TransferDatetime as date_tr,\n" +
            "'withdraw' as operation,\n" +
            "round(AccountProtocol.TransferBonus,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join AccountProtocol On AccountProtocol.SourceAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON AccountProtocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber = #{cardNumber}\n" +
            "union\n" +
            "SELECT\n" +
            "bonusprotocol.BonusProtocolKey as tr_key,\n" +
            "bonusprotocol.BonusChangeDatetime as date_tr,\n" +
            "'admission' as operation,\n" +
            "round(bonusprotocol.ChargeBonusSum,2) as amount,\n" +
            "shops.ShopName as station\n" +
            "from\n" +
            "card\n" +
            "left join synthetic_account ON synthetic_account.PersonKey = card.PersonKey\n" +
            "left join bonusprotocol On bonusprotocol.SyntheticAccountKey = synthetic_account.SyntheticAccountKey\n" +
            "left join shops ON bonusprotocol.ShopKey = shops.shopKey\n" +
            "where ElectronicNumber =  #{cardNumber}\n" +
            ")ss, (select @id:=0) AS z\n" +
            "where ss.tr_key is not null\n" +
            "and ss.date_tr is not null\n" +
            "and ss.amount is not null\n" +
            "and ss.station is not null\n" +
            "and ss.date_tr >= #{dateStart} and ss.date_tr < #{dateEnd} + interval 1 day\n" +
            "and ss.station = #{station}\n" +
            "order by ss.date_tr\n"
    )
    List<CardTransactions> findCardTransactionsByNumberAndDateAndStation(
            @Param(value = "cardNumber") String cardNumber
            , @Param(value = "station") String station
            , @Param(value = "dateStart") String dateStart
            , @Param(value = "dateEnd") String dateEnd
            );
}
