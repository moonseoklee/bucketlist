package com.example.bucket.domain;


import lombok.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="list")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {


    @Id
    private String id;

    private Integer idx;

    private String user;

    private String title;

    private String detail;

    private Boolean complete;

    public void setComplete(boolean flag){
        this.complete = flag;
    }
}
