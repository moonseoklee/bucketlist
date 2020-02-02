package com.example.bucket.service;

import com.example.bucket.domain.Bucket;
import com.example.bucket.domain.BucketRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;


public class ListServiceTest {

    @Mock
    private BucketRepository bucketRepository;


    private ListService listService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockBucketRepository();

        listService = new ListService(bucketRepository);

    }

    public void mockBucketRepository(){
        List<Bucket> list = new ArrayList<>();
        Bucket bucket = Bucket.builder().title("testTitle").detail("Des1").build();

        list.add(bucket);
        given(bucketRepository.findAll()).willReturn(list);
    }

    @Test
    public void getBucket(){

        Bucket bucket = listService.findBucket(0);
        assertThat(bucket.getTitle(),is("test"));
    }

    @Test
    public void setComplete(){
        Bucket bucket = listService.checkBucket(1);
        assertThat(bucket.getTitle(),is("test"));
    }
}