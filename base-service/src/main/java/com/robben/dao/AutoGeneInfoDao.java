package com.robben.dao;

import com.robben.entity.AutoGeneInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper
public interface AutoGeneInfoDao {

    AutoGeneInfoEntity getById(@NotNull Integer id);

    List<AutoGeneInfoEntity> listByEntity(AutoGeneInfoEntity autoGeneInfoEntity);

    AutoGeneInfoEntity getByEntity(AutoGeneInfoEntity autoGeneInfoEntity);

    List<AutoGeneInfoEntity> listByIds(@NotEmpty List<Integer> list);

    int insert(@NotNull AutoGeneInfoEntity autoGeneInfoEntity);

    int insertBatch(@NotEmpty List<AutoGeneInfoEntity> list);

    int update(@NotNull AutoGeneInfoEntity autoGeneInfoEntity);

    int updateByField(@NotNull @Param("where") AutoGeneInfoEntity where, @NotNull @Param("set") AutoGeneInfoEntity set);

    int updateBatch(@NotEmpty List<AutoGeneInfoEntity> list);

    int deleteById(@NotNull Integer id);

    int deleteByEntity(@NotNull AutoGeneInfoEntity autoGeneInfoEntity);

    int deleteByIds(@NotEmpty List<Integer> list);

    int countAll();

    int countByEntity(AutoGeneInfoEntity autoGeneInfoEntity);

}