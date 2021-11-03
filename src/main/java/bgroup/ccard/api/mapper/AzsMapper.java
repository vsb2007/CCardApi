package bgroup.ccard.api.mapper;

import bgroup.ccard.api.model.Azs;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AzsMapper {
    @Results({
            @Result(property = "codeAzs", column = "codeAzs"),
            @Result(property = "azsName", column = "azsName"),
    })
    @MapKey("codeAzs")
    @Select("select \n" +
            "shopKey as codeAzs\n" +
            ", shopName as azsName\n" +
            "from shops")
    public Map<Integer, Azs> getAzsMap();
}
