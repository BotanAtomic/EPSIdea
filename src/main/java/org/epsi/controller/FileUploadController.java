package org.epsi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.epsi.configuration.Database;
import org.epsi.model.Document;
import org.epsi.model.User;
import org.epsi.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


@Controller
public class FileUploadController {


    @PostMapping("/files/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,HttpSession session,
                                    HttpServletRequest httpServletRequest) {
        int lastModule = (int) session.getAttribute("last_module");

        try {
            String path = httpServletRequest.getServletContext().getRealPath("/documents");

            File newFile = new File(path + File.separator + file.getOriginalFilename());

            if (!newFile.exists()) {
                Files.createFile(newFile.toPath());
            }


            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();

            Database.database.query("INSERT INTO documents(path, user, module) VALUES (?,?,?)");
            User user = (User) session.getAttribute("user");


            PreparedStatement p = Database.database.connection.prepareStatement("INSERT INTO documents(path, user, module) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);

            p.setString(1, file.getOriginalFilename());
            p.setInt(2, user.getId());
            p.setInt(3, lastModule);

            p.execute();

            ResultSet resultSet = p.getGeneratedKeys();
            int id = 0;
            if (resultSet.next())
                id = resultSet.getInt(1);

            System.err.println("Generated id " + id);

            new Document(id,file.getOriginalFilename(), user, lastModule);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println("You successfully uploaded " + file.getOriginalFilename() + "!");

        System.err.println(httpServletRequest.getLocalName());

        return "redirect:/vmodule/?id=" + lastModule;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleStorageFileNotFound(Exception exc) {
        return ResponseEntity.notFound().build();
    }

}