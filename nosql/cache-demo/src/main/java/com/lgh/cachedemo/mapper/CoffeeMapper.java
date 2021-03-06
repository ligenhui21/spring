package com.lgh.cachedemo.mapper;

import com.lgh.cachedemo.model.Coffee;
import com.lgh.cachedemo.model.CoffeeExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CoffeeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    long countByExample(CoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    int deleteByExample(CoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    @Delete({
        "delete from t_coffee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    @Insert({
        "insert into t_coffee (name, price, ",
        "create_time, update_time)",
        "values (#{name,jdbcType=VARCHAR}, #{price,jdbcType=BIGINT,typeHandler=com.lgh.cachedemo.handler.MoneyTypeHandler}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Coffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    int insertSelective(Coffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    List<Coffee> selectByExampleWithRowbounds(CoffeeExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    List<Coffee> selectByExample(CoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    @Select({
        "select",
        "id, name, price, create_time, update_time",
        "from t_coffee",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("com.lgh.cachedemo.mapper.CoffeeMapper.BaseResultMap")
    Coffee selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    int updateByExampleSelective(@Param("record") Coffee record, @Param("example") CoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    int updateByExample(@Param("record") Coffee record, @Param("example") CoffeeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    int updateByPrimaryKeySelective(Coffee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_coffee
     *
     * @mbg.generated Tue Aug 27 19:48:04 CST 2019
     */
    @Update({
        "update t_coffee",
        "set name = #{name,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=BIGINT,typeHandler=com.lgh.cachedemo.handler.MoneyTypeHandler},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Coffee record);
}