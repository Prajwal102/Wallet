package wallet;


import java.sql.DriverManager;
import java.sql.Connection;



public class Conne {
	static Connection conn = null;
	static String url = "jdbc:mysql://localhost:3306/testdb";
	static String user = "root";
	static String pass = "root";
	
public static Connection getcon() {

	try {

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url,user,pass);
		if(conn != null)
		System.out.println("connection sucessfull");
	}
	catch(Exception e) {
		System.out.println(e);
	}
	return conn;
}
}
	

