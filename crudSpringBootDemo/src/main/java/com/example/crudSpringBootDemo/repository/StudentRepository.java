package com.example.crudSpringBootDemo.repository;

import com.example.crudSpringBootDemo.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentRepository {

    public Student saveStudent(Student studentReq){

        Student s1=new Student();
        s1.setName("Mani");
        s1.setAge(20);
        s1.setEmail("mani@gmail.com");
        s1.setRollno(12);
        s1.setSubject("Spring Boot");
        return s1;
    }
}
