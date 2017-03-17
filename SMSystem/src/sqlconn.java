import java.sql.*;

public class sqlconn{
	public Connection conn;
	
	//初始化----装载驱动、连接数据库
	public sqlconn(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(java.lang.ClassNotFoundException e){
			e.printStackTrace();
			//System.out.println("error");
		}
		try{		
		    conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8","root","");
			//conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?user=root&useUnicode=true&characterEncoding=UTF-8");
		}
		catch(SQLException e){
			//System.out.println("connect datab error!");
			e.printStackTrace();
			
		}
	}
	
	public ResultSet getRs(String sql){
		ResultSet rs=null;
    try{  
     	Statement stmt=conn.createStatement();//创建发送sql语句的对象stmt. 
     	rs=stmt.executeQuery(sql);//执行查询语句sql,得记录集。
    }
    catch(Exception e){
    	System.out.println(""+e);
    }
    return rs;
	}
	
	//自定义具有对数据库实现增加，删除，修改功能的方法：
	public void dbMod(String sql){ 
  	try{
  		//创建发送sql语句的对象stmt
  		Statement stmt=conn.createStatement();  
  		//实现增加，删除，修改数据库等操作
      stmt.executeUpdate(sql);  
    }
    catch(Exception e){}
 	}
	
	
	//定义关闭连接对象和记录集的方法
	public void dbClose(ResultSet rs){ 
		try{ 
			//关闭连接对象
			conn.close();	
			//关闭记录集		
      rs.close();  			
   	}
    catch(SQLException rse){}  
	}
}