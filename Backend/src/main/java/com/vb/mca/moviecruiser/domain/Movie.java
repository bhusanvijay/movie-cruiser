package com.vb.mca.moviecruiser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "MOVIE")
public class Movie {
	
	private int id;
	private String title;
	private String comments;
	private String poster_path;
	private String release_date;
	private String overview;
	
	public Movie() {}
	
	public Movie(int id, String title, String poster_path, String release_date, String overview, String comments) {
		super();
		this.id = id;
		this.title = title;		
		this.poster_path = poster_path;
		this.release_date = release_date;
		this.overview = overview;
		this.comments = comments;
	}
	@Id
	@Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}	
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="COMMENTS")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Column(name="POSTER_PATH")
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	@Column(name="RELEASE_DATE")
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	@Column(name="OVERVIEW", length=512)
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}	
}
