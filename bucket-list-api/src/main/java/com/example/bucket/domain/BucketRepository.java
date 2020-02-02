package com.example.bucket.domain;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BucketRepository extends MongoRepository<Bucket,Long> {

    List<Bucket> findAll();
    Bucket findByIdx(int idx);

    List<Bucket> findByUser(String id);
}
