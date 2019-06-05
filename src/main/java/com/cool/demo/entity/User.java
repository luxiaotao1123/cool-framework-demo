package com.cool.demo.entity;

import com.core.entity.Base;
import com.core.annotation.Table;

@Table("dem_user")
public class User extends Base {

	private static final long serialVersionUID = 1L;
	
	public User(){}

	private String name;

	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}
}
