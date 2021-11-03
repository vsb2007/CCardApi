package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.FioModel;
import bgroup.ccard.api.model.SurnameModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface FioMapper {
    @Results({
            @Result(property = "last_name", column = "last_name"),
            @Result(property = "name", column = "first_name"),
            @Result(property = "second_name", column = "second_name"),
            @Result(property = "phone", column = "phone"),
            //@Result(property = "email", column = "email"),
            @Result(property = "birthday", column = "birthday")
    })
    @Select("SELECT\n" +
            "c.RequisitesValue as last_name\n" +
            ",a.RequisitesValue as first_name\n" +
            ",b.RequisitesValue as second_name\n" +
            ",p.RequisitesValue as phone\n" +
            "-- ,e.RequisitesValue as email\n" +
            ",birth.RequisitesValue as birthday\n" +
            "FROM\n" +
            "card\n" +
            "left join person on person.PersonKey = card.personkey\n" +
            "LEFT JOIN RequisitesValue c ON c.RequisitesName = 'LastName' and c.RequisitesSetKey = person.RequisitesSetKey\n" +
            "LEFT JOIN RequisitesValue a ON a.RequisitesName = 'FirstName' and a.RequisitesSetKey = person.RequisitesSetKey\n" +
            "LEFT JOIN RequisitesValue b ON b.RequisitesName = 'SecondName' and b.RequisitesSetKey = person.RequisitesSetKey\n" +
            "LEFT JOIN RequisitesValue p ON p.RequisitesName = 'Phone' and p.RequisitesSetKey = person.RequisitesSetKey\n" +
            "-- LEFT JOIN RequisitesValue e ON e.RequisitesName = 'Email' and e.RequisitesSetKey = person.RequisitesSetKey\n" +
            "LEFT JOIN RequisitesValue birth ON birth.RequisitesName = 'BirthDate' and birth.RequisitesSetKey = person.RequisitesSetKey\n" +
            "where electronicnumber =  #{cardNumber}")
    public FioModel getFioByCardNumber(@Param(value = "cardNumber") String cardNumber);
}
