package me.becky.service.welcome.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

@RestController
public class IndexController {

    @Value("${service.upload-root}")
    private String uploadDir;

    @GetMapping("/")
    public String index() {
        return "welcome this is service-welcome";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(StandardMultipartHttpServletRequest request) throws IOException, ServletException {
        Iterator<String> uploadFileNames = request.getFileNames();
        while (uploadFileNames.hasNext()) {
            String uploadFileName = uploadFileNames.next();
            MultipartFile file = request.getFile(uploadFileName);
            //文件目录
            Path rootLocation = Paths.get(uploadDir);
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            //data.js是文件
            Path path = rootLocation.resolve(file.getOriginalFilename());
            Files.write(path, file.getBytes(), StandardOpenOption.CREATE_NEW);
        }

        return "OK";
    }

}
