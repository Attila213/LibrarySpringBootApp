package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T>{

    List<T> list();

    T get(int id);
    int create(T t);
    int update(T t1,int id);
    void delete(int id);

}
