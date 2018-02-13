package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.SurnameModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface SurnameMapper {
    @Results({
            @Result(property = "surname", column = "surname")

    })
    @Select("SELECT\n" +
            "c.RequisitesValue as surname\n" +
            "FROM\n" +
            "card\n" +
            "left join person on person.PersonKey = card.personkey\n" +
            "LEFT JOIN RequisitesValue c ON c.RequisitesName = 'LastName' and c.RequisitesSetKey = person.RequisitesSetKey\n" +
            "where electronicnumber =  #{cardNumber}")
    public SurnameModel getSurnameByCardNumber(@Param(value = "cardNumber") String cardNumber);
}
