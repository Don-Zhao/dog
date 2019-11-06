package com.dog.model;

public class User {
	private int id;
	
	private String name;
	
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
