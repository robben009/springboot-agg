package com.robben.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel
public class UploadFil2Req {

    @ApiModelProperty("文件名")
    String fileName;
    @ApiModelProperty("文件描述")
    String fileDesc;
    @ApiModelProperty("文件")
    MultipartFile file;

}
