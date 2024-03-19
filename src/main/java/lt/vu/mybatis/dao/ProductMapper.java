package lt.vu.mybatis.dao;

import java.util.List;
import lt.vu.mybatis.model.Product;
import org.mybatis.cdi.Mapper;

@Mapper
public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PRODUCT
     *
     * @mbg.generated Sun Mar 10 20:12:27 EET 2024
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PRODUCT
     *
     * @mbg.generated Sun Mar 10 20:12:27 EET 2024
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PRODUCT
     *
     * @mbg.generated Sun Mar 10 20:12:27 EET 2024
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PRODUCT
     *
     * @mbg.generated Sun Mar 10 20:12:27 EET 2024
     */
    List<Product> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PRODUCT
     *
     * @mbg.generated Sun Mar 10 20:12:27 EET 2024
     */
    int updateByPrimaryKey(Product record);
}