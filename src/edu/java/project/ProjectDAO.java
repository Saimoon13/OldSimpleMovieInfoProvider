package edu.java.project;

import java.util.List;


public interface ProjectDAO {

	int insert(Member m);
	List<Rate> select(GUI window);
	int insertRate(Rate r);
	int selectByEmail(Member m);
}
