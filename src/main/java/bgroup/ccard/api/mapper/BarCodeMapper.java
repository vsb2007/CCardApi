package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.BarCodeDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Service
public interface BarCodeMapper {
    @Results({
            @Result(property = "cardNumber", column = "cardNumber"),
            @Result(property = "orgCode", column = "orgCode"),
            @Result(property = "clientCode", column = "clientCode")
    })
    @Select("SELECT \n" +
            "c.ElectronicNumber AS CardNumber,\n" +
            "o.COD_R AS orgCode, \n" +
            "MAX(p.COD_OWN) AS clientCode\n" +
            "FROM card c\n" +
            "LEFT JOIN Person p ON p.PersonKey = c.PersonKey\n" +
            "LEFT JOIN Organization o on o.OrganizationKey=p.OrganizationKey\n" +
            "WHERE \n" +
            "c.ElectronicNumber =  #{cardNumber}\n" +
            "and c.PersonKey IS NOT NULL \n" +
            "AND o.COD_R = 1470\n" +
            "GROUP BY c.ElectronicNumber\n" +
            "ORDER BY c.ElectronicNumber")
    public BarCodeDetails getBarCodeDetailsByNumber(@Param(value = "cardNumber") String cardNumber);
}
