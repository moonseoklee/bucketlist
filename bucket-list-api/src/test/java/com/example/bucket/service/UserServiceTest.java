package com.example.bucket.service;


import com.example.bucket.domain.User;
import com.example.bucket.domain.UserRepository;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

       // mockUserRepository();

        userService = new UserService(userRepository);

    }
    /*
    public void mockUserRepository(){
        List<User> list = new ArrayList<>();
        User user = User.builder().userId("testTitle").password("Des1").build();

        list.add(user);
        given(userRepository.findAll()).willReturn(list);
    }*/

    @Test
    public void getUsers(){
        List<User> mockUsers = new ArrayList<User>();
        mockUsers.add(User.builder().userId("a").password("tester").build());
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        assertThat(users.get(0).getUserId(),is("tester"));
    }
    @Test
    public void create(){
        User mockUser = User.builder().userId("test").password("testP").id("asdfasdf").build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.createUser(User.builder().userId("test").password("testP").build());

        assertThat(user.getPassword(),is("testP"));

    }

}