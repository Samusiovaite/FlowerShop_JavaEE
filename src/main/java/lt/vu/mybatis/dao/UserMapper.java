package lt.vu.mybatis.dao;

import java.util.List;
import lt.vu.mybatis.model.User;
import org.mybatis.cdi.Mapper;

@Mapper
public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.USER
     *
     * @mbg.generated Tue Mar 12 13:14:09 EET 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.USER
     *
     * @mbg.generated Tue Mar 12 13:14:09 EET 2024
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.USER
     *
     * @mbg.generated Tue Mar 12 13:14:09 EET 2024
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.USER
     *
     * @mbg.generated Tue Mar 12 13:14:09 EET 2024
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.USER
     *
     * @mbg.generated Tue Mar 12 13:14:09 EET 2024
     */
    int updateByPrimaryKey(User record);
}