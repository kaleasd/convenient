package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.TagInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface TagInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    int insert(TagInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    int insertSelective(TagInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    TagInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TagInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tag_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TagInfo record);
}
