package com.goodpower.pvams.controller.common;

import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.util.FileHandleUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public ResultMap upload(MultipartFile file) throws IOException {
        String path = FileHandleUtil.upload(file.getInputStream(), file.getOriginalFilename());
        ResultMap resultMap = new ResultMap();
        resultMap.success().setData(path).message("上传成功");
        return resultMap;
    }

}
