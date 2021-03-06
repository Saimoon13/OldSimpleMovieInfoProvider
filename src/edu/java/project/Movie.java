package edu.java.project;

public class Movie {

	// 멤버변수
	private String title;
	private String movieinfo;
	private String tomato;
	private String poster;
	
	// 생성자
	public Movie() {}
	public Movie(String title, String movieinfo, String tomato, String poster) {
		this.title = title;
		this.movieinfo = movieinfo;
		this.tomato = tomato;
		this.poster = poster;
	}
	
	// getter and setter
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMovieinfo() {
		return movieinfo;
	}
	public void setMovieinfo(String movieinfo) {
		this.movieinfo = movieinfo;
	}
	public String getTomato() {
		return tomato;
	}
	public void setTomato(String tomato) {
		this.tomato = tomato;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	};
	
}
