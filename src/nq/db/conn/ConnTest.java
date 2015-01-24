package nq.db.conn;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

class ConnTest{
	public static void main(String args[]){
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/javaee";
		String user="root";
		String pwd="root";
		Connection conn;
		Statement stmt;
		try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, pwd);
			System.out.print(conn);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}