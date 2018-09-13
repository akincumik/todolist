package com.akin.todolist;

import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class ListItem{
    private @Id @GeneratedValue long id;
    private String name;
    private String descreption;
    private Date deadline;
    private String status;

    ListItem(){}

    ListItem(String name, String descreption, Date deadline, String status){
        this.name = name;
        this.descreption = descreption;
        this.deadline = deadline;
        this.status = status;
    }
}