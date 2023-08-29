package com.robben.model.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema
public class UploadFil2Req {

    @Schema(name = "文件名")
    String fileName;
    @Schema(name = "文件描述")
    String fileDesc;
    @Schema(name = "文件")
    MultipartFile file;

}
