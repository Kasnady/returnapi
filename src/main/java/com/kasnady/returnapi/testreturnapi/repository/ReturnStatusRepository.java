package com.kasnady.returnapi.testreturnapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.kasnady.returnapi.testreturnapi.entity.ReturnStatus;

public interface ReturnStatusRepository extends CrudRepository<ReturnStatus, Integer> {

}
