import java.sql.*;

public class sqlconn{
	public Connection conn;
	
	//��ʼ��----װ���������������ݿ�
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
     	Statement stmt=conn.createStatement();//��������sql���Ķ���stmt. 
     	rs=stmt.executeQuery(sql);//ִ�в�ѯ���sql,�ü�¼����
    }
    catch(Exception e){
    	System.out.println(""+e);
    }
    return rs;
	}
	
	//�Զ�����ж����ݿ�ʵ�����ӣ�ɾ�����޸Ĺ��ܵķ�����
	public void dbMod(String sql){ 
  	try{
  		//��������sql���Ķ���stmt
  		Statement stmt=conn.createStatement();  
  		//ʵ�����ӣ�ɾ�����޸����ݿ�Ȳ���
      stmt.executeUpdate(sql);  
    }
    catch(Exception e){}
 	}
	
	
	//����ر����Ӷ���ͼ�¼���ķ���
	public void dbClose(ResultSet rs){ 
		try{ 
			//�ر����Ӷ���
			conn.close();	
			//�رռ�¼��		
      rs.close();  			
   	}
    catch(SQLException rse){}  
	}
}