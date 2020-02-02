package com.example.bucket.service;

import com.example.bucket.domain.Bucket;
import com.example.bucket.domain.BucketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class ListService {

    @Autowired
    private BucketRepository bucketRepository;

    public ListService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    public  Bucket findBucket(int idx) {
        List<Bucket> buckets = bucketRepository.findAll();

        Bucket bucket = buckets.get(idx-1);
        System.out.println(bucket);
        return bucket;
    }

    public List<Bucket> list(String user){

        List<Bucket> buckets = bucketRepository.findByUser(user);

        return buckets;
    }

    public Bucket addBucket(Bucket bucket) {
        List<Bucket> buckets = bucketRepository.findByUser(bucket.getUser());
        bucket = bucket.builder().user(bucket.getUser()).title(bucket.getTitle()).detail(bucket.getDetail()).idx(buckets.size()+1).complete(false).build();

        bucketRepository.save(bucket);

        return bucket;
    }

    public Bucket checkBucket(int idx) {
        Bucket bucket = bucketRepository.findByIdx(idx);


        if (bucket.getComplete()==true) {
            bucket.setComplete(false);
        } else {
            bucket.setComplete(true);
        }



        bucketRepository.save(bucket);
        return bucket;
    }
}
