package nq.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

public class SqlTest {
	
	static String driver="com.mysql.jdbc.Driver";
	static String url="jdbc:mysql://localhost:3306/javaee";
	static String user="root";
	static String pwd="root";
	static Connection conn;
	static Statement stmt;
	public static void conn() throws Exception{
		//try{
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,pwd);
			stmt=conn.createStatement();
		//}
	}
	public static void main(String[] args) throws Exception {
		conn();
		ResultSet rs1,rs2;
		
		/*------插入语句-------*/
		//String sql="insert into news_inf (news_id,news_title) values (5,'Learning php');";有错，不知道是啥！
		//String sql="insert into news_inf (news_id,news_title) values (5,'Learning2php');";正确，Statement的弊端
		//int changeNum=stmt.executeUpdate(sql);
		//System.out.println("受到影响的列是："+changeNum);
		
		/*------查询语句-------*/
		String s1="select * from news_inf;";
		rs1=stmt.executeQuery(s1);//s和sql可用同一个stmt来调用execute*()
		while(rs1.next()){
			
			System.out.println("序号："+rs1.getString(1)+"---->"+"书名:"+rs1.getString(2));
		}
		
		/*--------execute的使用------------*/
		/*execute只能返回boolean，要用Statement的getResultSet()-->ResultSet; getUpdateCount-->changeNum*/
		String s2="select * from news_inf where news_id=5 or news_id=6 ;";
		boolean hasresult=stmt.execute(s2);
		if(hasresult){
			rs2=stmt.getResultSet();
			/*-------------管理结果集的练习-------------*/
			ResultSetMetaData rsmd=rs2.getMetaData();
			int columnNum=rsmd.getColumnCount();//对于一般的查询，不知道会有多少列
			Vector<String> v=new Vector<String>();//保存列属性的名称,没有必要只是复习一下
			for(int i=0;i<columnNum;i++){
				String tmpStr=rsmd.getColumnName(i+1);
				v.add(tmpStr);
			}
			System.out.println("capacity:"+v.capacity());
			System.out.println("size:"+v.size());
			
			for(int i=0;i<v.size();i++){//打印表头名称
				System.out.print(v.get(i)+"\t");
			}
			System.out.println();
			for(int i=0;i<v.size();i++){//打印表头类型
				System.out.print(rsmd.getColumnTypeName(i+1)+"\t");
			}
			System.out.println();
			
			
			while(rs2.next()){
				for(int i=0;i<columnNum;i++){//按列打出
					System.out.print(rs2.getString(i+1)+"\t");
				}
				System.out.println();
			}
		}
		else {
			System.out.println(stmt.getUpdateCount()+"条记录发生了变化");
		}
		
		/*
		int num=0;
		ResultSet tmpRs=stmt.executeQuery("select * from news_inf;"); 
		while(tmpRs.next()) num++;
		/*------------PreparedStatement---------------------*/
		int num=getNum();
		System.out.println(num);
		String s3="insert into news_inf values (?,?);";
		PreparedStatement ppstmt=conn.prepareStatement(s3);
		for(int i=1;i<=5;i++){
			ppstmt.setInt(1, i+num);
			//ppstmt.setString(2, Integer.toString(i+num));
			ppstmt.setString(2, "PhP"+String.valueOf(i+num));
			ppstmt.executeUpdate();
		}
		

	}
	
	public static int getNum() throws Exception{//获取当前表中记录个数
		ResultSet tmpRs=stmt.executeQuery("select * from news_inf;"); 
		int num=0;
		while(tmpRs.next())
			num++;
		return num;
	}

}
