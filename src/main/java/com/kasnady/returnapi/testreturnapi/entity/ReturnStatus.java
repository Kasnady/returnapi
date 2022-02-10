package com.kasnady.returnapi.testreturnapi.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "return_statuses")
public class ReturnStatus {
	public static final Integer AWAITING_APPROVAL = 1;
	public static final Integer ACCEPTED = 2;
	public static final Integer REJECTED = 3;
	public static final Integer COMPLETE = 9;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String value;

	public ReturnStatus() {
		super();
	}

	private ReturnStatus(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * Get All Statuses to store into DB
	 * 
	 * @return
	 */
	public static List<ReturnStatus> getStatuses() {
		List<ReturnStatus> statuses = new ArrayList<>();
		statuses.add(new ReturnStatus(AWAITING_APPROVAL, "AWAITING_APPROVAL"));
		statuses.add(new ReturnStatus(ACCEPTED, "ACCEPTED"));
		statuses.add(new ReturnStatus(REJECTED, "REJECTED"));
		statuses.add(new ReturnStatus(COMPLETE, "COMPLETE"));
		return statuses;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
