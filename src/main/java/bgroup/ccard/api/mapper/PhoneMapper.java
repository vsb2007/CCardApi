package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.BarCodeDetails;
import bgroup.ccard.api.model.PhoneModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface PhoneMapper {
    @Results({
            @Result(property = "phone", column = "phone")

    })
    @Select("SELECT \n" +
            "c.RequisitesValue as phone\n" +
            "FROM \n" +
            "card\n" +
            "left join person on person.PersonKey = card.personkey\n" +
            "LEFT JOIN RequisitesValue c ON c.RequisitesName = 'Phone' and c.RequisitesSetKey = person.RequisitesSetKey\n" +
            "where electronicnumber = #{cardNumber}")
    public PhoneModel getPhoneByCardNumber(@Param(value = "cardNumber") String cardNumber);
}
