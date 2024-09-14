package com.robben.agg.base.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "文件name",description = "文件对象")
public class FileResp {

    @Schema(description = "随机名称")
    private String random;
    @Schema(description = "文件名称")
    private String name;
    @Schema(description = "文件大小")
    private Long size;
    @Schema(description = "是否上传成功")
    private Boolean success;
}
