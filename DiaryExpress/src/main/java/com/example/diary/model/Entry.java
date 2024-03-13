package com.example.diary.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="entries")
public class Entry {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Date entrydate;
 private String description;
 private String username;

 public Entry() {
 }

 public Entry(Date entrydate, String description, String username) {
     this.entrydate = entrydate;
     this.description = description;
     this.username = username;
 }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Date getEntrydate() {
	return entrydate;
}

public void setEntryDate(Date entryDate) {
	this.entrydate = entryDate;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

 
}
