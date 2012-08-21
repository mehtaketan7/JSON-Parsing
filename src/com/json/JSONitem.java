package com.json;

import java.util.Comparator;

public class JSONitem implements Comparator<JSONitem> {

	private String id;
	private String title;
	private String designation;
	

	private String image;
	private String largeimage;
	private String mobilePlayer;
	private String date;
	private String description;
	private float rating;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void getImage(String image) {
		this.image = image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLargeimage() {
		return largeimage;
	}

	public void setLargeimage(String largeimage) {
		this.largeimage = largeimage;
	}

	public String getMobilePlayer() {
		return mobilePlayer;
	}

	public void setMobilePlayer(String mobilePlayer) {
		this.mobilePlayer = mobilePlayer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public int compare(JSONitem lhs, JSONitem rhs) {
		// TODO Auto-generated method stub
		return compare(lhs, rhs);
	}

}
