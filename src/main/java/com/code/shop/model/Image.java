package com.code.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "images")
public class Image {

    @Id
    private String id;  // MongoDB uses String as the default type for IDs

    private String fileName;
    private String fileType;

    private byte[] imageData;  // Store the image as a byte array

    private String downloadUri;

    @DBRef
    private Product product;  // Reference to Product in MongoDB
}
