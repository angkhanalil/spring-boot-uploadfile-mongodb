package com.example.demo.service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.LoadFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    public String addFile(MultipartFile upload) {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());
        // metadata.put("productId", "3");
        // metadata.put("companyID", "1");

        Object fileID;
        try {

            fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(),
                    metadata);
            return fileID.toString();
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
            // e.printStackTrace();
        }

    }

    public LoadFile downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));

        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename(gridFSFile.getFilename());

            loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

            loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

            loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
        }

        return loadFile;
    }
}
