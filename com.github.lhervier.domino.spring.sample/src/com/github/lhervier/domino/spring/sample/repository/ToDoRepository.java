package com.github.lhervier.domino.spring.sample.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.lhervier.domino.spring.sample.entity.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {

	public void delete(ToDo deleted);
	
    public List<ToDo> findAll();
 
    public ToDo findOne(Long id);
 
    public ToDo save(ToDo persisted);
}
