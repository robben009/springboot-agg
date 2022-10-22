package com.robben.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/8/14 11:05
 */

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {DateFormatMapper.class})
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "name", target = "carName")
    @Mapping(source = "createTime", target = "startTime",qualifiedByName = "asString")
    CarVo carDtoToCarVo(CarDto carDto);

}
