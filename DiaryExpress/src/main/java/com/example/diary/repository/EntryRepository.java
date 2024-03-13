package com.example.diary.repository;

import com.example.diary.model.Entry;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {
	List<Entry> findByUsername(String username);

}
