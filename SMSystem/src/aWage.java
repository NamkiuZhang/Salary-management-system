import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class aWage extends Frame implements ActionListener{
	String sql="";
	sqlconn conn=null;
	ResultSet rs=null;
	
	Label desLab=new Label("请选择下面的单选按钮来按类型计算平均工资:");
	Label aWageLab=new Label("计算平均工资");
	TextField aWageTxt=new TextField();
	Button figureBut=new Button("计算");
	Label yearLab=new Label("年");
	Label monthLab=new Label("月");
	
	Choice yearChoice=new Choice();
	Choice monthChoice=new Choice();
	Choice dtmtChoice=new Choice();
	
	JRadioButton dtmtRadBut=new JRadioButton("按部门",true);
	JRadioButton pYRadBut=new JRadioButton("按全体",false);
	ButtonGroup bGroup=new ButtonGroup();
	
	public aWage(){
		setTitle("计算平均工资");
		setSize(350,250);
		setLayout(null);
		
		this.setLocationRelativeTo(this.getParent());
		
		desLab.setSize(250,20);
		aWageLab.setSize(50,20);
		aWageTxt.setSize(100,20);
		figureBut.setSize(40,20);
		
		yearLab.setSize(20,20);
		monthLab.setSize(20,20);
		yearChoice.setSize(60,20);
		monthChoice.setSize(60,20);
		dtmtChoice.setSize(100,20);
		
		dtmtRadBut.setSize(80,20);
		pYRadBut.setSize(80,20);
		
		desLab.setLocation(60,50);
		aWageLab.setLocation(60,80);
		aWageTxt.setLocation(130,80);
		figureBut.setLocation(250,80);
		
		dtmtRadBut.setLocation(120,110);
		pYRadBut.setLocation(210,110);
		
		yearChoice.setLocation(110,140);
		monthChoice.setLocation(205,140);
		yearLab.setLocation(175,140);
		monthLab.setLocation(270,140);
		
		dtmtChoice.setLocation(190,170);
		
		add(desLab);
		add(aWageLab);
		add(aWageTxt);
		add(figureBut);
		add(yearLab);
		add(monthLab);
		
		add(yearChoice);
		add(monthChoice);
		add(dtmtRadBut);
		add(pYRadBut);
		
		add(dtmtChoice);
		
		bGroup.add(dtmtRadBut);
		bGroup.add(pYRadBut);
		
		figureBut.addActionListener(this);
		dtmtRadBut.addActionListener(this);
		pYRadBut.addActionListener(this);
		
		//dtmtRadBut.addItemListener(this);
		//pYRadBut.addItemListener(this);
		
		initYChoice();
		initMChoice();
		initDtmtChoice();
		
		//yearChoice.setEnabled(false);
		//monthChoice.setEnabled(false);
		
		aWageTxt.setEditable(false);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
	}
	
	public void initYChoice(){
		int i=2015;
		String t="";
		while(i<2010){
			t=String.valueOf(i);
			yearChoice.addItem(t);
			i++;
		}
	}
	
	public void initMChoice(){
		int i=1;
		String t;
		while(i<=12){
			t=String.valueOf(i);
			monthChoice.addItem(t);
			i++;
		}
	}
	
	public void initDtmtChoice(){
  	try{
  		conn=new sqlconn();
      sql="select dtmt_name from department";
    	rs=conn.getRs(sql);
    	while(rs.next()){
    		dtmtChoice.addItem(rs.getString("dtmt_name"));
      }
      conn.dbClose(rs);
      conn=null;
   	}
    catch(Exception e){}
	}
	
	public void actionPerformed(ActionEvent e){
		if(dtmtRadBut.isSelected()){
			yearChoice.setEnabled(true);
			monthChoice.setEnabled(true);
		}
		if(pYRadBut.isSelected()){
			yearChoice.setEnabled(false);
			monthChoice.setEnabled(false);
		}
		
		if(e.getActionCommand().equals("计算")){
			//按部门计算平均工资
			if(dtmtRadBut.isSelected()){
				try{
					sql="select avg(factwage) from wages where id in(select id from employees where department='"+dtmtChoice.getSelectedItem()
					+"') and datepart('yyyy',wagedate)='"+yearChoice.getSelectedItem()+"' and datepart('m',wagedate)='"+monthChoice.getSelectedItem()+"'";
					conn=new sqlconn();
					rs=conn.getRs(sql);
					if(rs.next()){
						aWageTxt.setText(rs.getString("Expr1000"));
						conn.dbClose(rs);
					}
					if(aWageTxt.getText().equals(""))
						aWageTxt.setText("0");
					
				}
				catch(SQLException se){
					System.out.println("SQL语句问题！");
				}				
			}
			//按人、年计算平均工资
			if(pYRadBut.isSelected()){
				//System.out.println("按人、年");
				try{
					sql="select avg(factwage) from wages";
					conn=new sqlconn();
					rs=conn.getRs(sql);
					if(rs.next())
						aWageTxt.setText(rs.getString("Expr1000"));
				}
				catch(Exception se){
					System.out.println("SQL语句问题！");
				}
			}
		}
	}
}