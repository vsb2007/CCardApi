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
            "mycarddate.elnum AS cardNumber\n" +
            ",o.COD_R AS orgCode\n" +
            ", p.COD_OWN AS clientCode \n" +
            "FROM mycarddate\n" +
            "LEFT JOIN (SELECT PersonKey,OrganizationKey,COD_OWN FROM Person GROUP BY OrganizationKey,COD_OWN) p ON p.PersonKey=mycarddate.PersonKey\n" +
            "LEFT JOIN (SELECT OrganizationKey,COD_R FROM Organization GROUP BY COD_R) o on  p.OrganizationKey=o.OrganizationKey\n" +
            "where mycarddate.elnum = #{cardNumber}")
    public BarCodeDetails getBarCodeDetailsByNumber(@Param(value = "cardNumber") String cardNumber);
}
