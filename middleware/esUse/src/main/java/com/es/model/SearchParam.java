package com.es.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchParam {
    @ApiModelProperty("索引")
    private String index;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("id")
    private String id;

}