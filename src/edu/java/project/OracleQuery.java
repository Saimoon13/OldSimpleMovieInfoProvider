package edu.java.project;

public interface OracleQuery {

	public static final String URL =
			"jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";

	public static final String SQL_SELECT_BY_INDEX =
			"select * from project_movie where title = ?";
	public static final String SQL_SELECT_BY_EMAIL =
			"select * from project_member where memail = ?";
	public static final String SQL_INSERT_REVIEW =
			"insert into project_cr (crn, crc, title) values (?,?,?)";
	public static final String SQL_INSERT_MEMBER =
			"insert into project_member(MNAME, MEMAIL, MPW) values (?,?,?)";
	public static final String SQL_SELECT_ALL =
			"select * from project_cr where title = ?";
	public static final String SQL_SELECED_BY_ID =
			"select * from project_member where memail = ?";
	public static final String SQL_SELECTED_BY_TITLE =
			"select * from project_movie where upper(title) = ?";
	
} // end of interface
