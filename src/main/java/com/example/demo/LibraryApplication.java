package com.example.demo;

import com.example.demo.conroller.Sha512Encoder;
import com.example.demo.dao.DAO;
import com.example.demo.model.Book;
import com.example.demo.model.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LibraryApplication {

    private static DAO<Member> memberDAO;

    public LibraryApplication(DAO<Member> dao){
        this.memberDAO = dao;
    }
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
