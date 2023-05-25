package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "upload.files")
public class upload {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    private String filename;

    public String getFilename() {
        return filename;
    }

    private long length;

    public long getLength() {
        return length;
    }

    /**
     * @param id the id to set
     */
    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @param length the length to set
     */
    public void setLength(long length) {
        this.length = length;
    }

}
