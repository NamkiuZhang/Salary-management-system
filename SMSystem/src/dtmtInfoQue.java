import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.table.JTableHeader;

//import com.sun.istack.internal.logging.Logger;

class dtmtInfoQue extends Frame implements ActionListener {
	String sql;
	ResultSet rs = null;
	sqlconn conn = null;
	
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;

	//Label tipsLab = new Label("如: W001,W002");
	Label idLab = new Label("部门编号");
	Label nameLab = new Label("部门名称");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	Button queryBut = new Button("查询");
	Button exitBut = new Button("退出");

	public dtmtInfoQue() {
		super("部门信息查询");
		setSize(400, 570);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		
		
		// 设置控件大小
		//tipsLab.setSize(150, 20);
		idLab.setSize(70, 20);
		nameLab.setSize(70, 20);
		idTxt.setSize(130, 20);
		nameTxt.setSize(130, 20);
		queryBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// 设置控件位置
		//tipsLab.setLocation(160, 110);
		idLab.setLocation(90, 80);
		nameLab.setLocation(90, 140);
		idTxt.setLocation(160, 80);
		nameTxt.setLocation(160, 140);
		queryBut.setLocation(100, 170);
		exitBut.setLocation(210, 170);
		
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(70,240,250,270);
		this.btnShow = new JButton("显示所有部门信息");
		this.btnShow.setBounds(70, 210, 250, 30);
		
		this.btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);
		    }
		});
		add(this.scpDemo);
		add(this.btnShow);
		this.setVisible(true);

		// 把各控件添加到Frame上
		//add(tipsLab);
		add(idLab);
		add(nameLab);
		add(idTxt);
		add(nameTxt);
		add(queryBut);
		add(exitBut);
		
      
		// 在两个按钮上注册监听器
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);

		setVisible(true);
		
		

		// 窗口"关闭"按钮添加窗口适配器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("退出"))
			dispose();
		if (ae.getActionCommand().equals("查询")) {
			if (idTxt.getText().equals("") && nameTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "请输入查询内容!", "警告", 0);
				dispose();
				dtmtInfoQue diq = new dtmtInfoQue();
			}else{
			sql = "select * from department where dtmt_id='" + idTxt.getText()
					+ "' or dtmt_name= '" + nameTxt.getText()
							+ "'";
			conn = new sqlconn();
			try {
				rs = conn.getRs(sql);
				if (rs.next()) {
					idTxt.setText(rs.getString("dtmt_id"));
					nameTxt.setText(rs.getString("dtmt_name"));
					conn.dbClose(rs);
					conn = null;
					rs = null;
				} else {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "该部门不存在!", "警告", 0);
				}
			} catch (Exception e) {
			}
			}
		}
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{
		    String sql1 = "select count(*) from department";
		    String sql2 = "select * from department";
		    
		    conn = new sqlconn();
		 
		    ResultSet rs1 = conn.getRs(sql1);
		    int count = 0;
		    //得到总记录数
		    if(rs1.next()){
		    	count = rs1.getInt(1);
		    }
		    rs1.close();
		    
		    Object[][] info = new Object[count][3];
		    count=0;
		    ResultSet rs2 = conn.getRs(sql2);
		    
		    while(rs2.next()){
		    	info[count][0] = rs2.getString(1);
		    	info[count][1] = rs2.getString(2);
		    	String sql3 = "select count(*) from employees where department ='"+info[count][1]+"'";
		    	ResultSet rs3 = conn.getRs(sql3);
		    	int count3 = 0;
		    	if(rs3.next()){
			    	count3 = rs3.getInt(1);
			    }
		    	//rs3.close();
		    	System.out.println(info[count][1]);
		    	info[count][2] = rs3.getInt(1);
		    	count++;
		    }
		       
		    for(Object [] ob:info){
		    	System.out.println(ob[0]);
		    	System.out.println(ob[1]);
		    	System.out.println(ob[2]);

		    }
		    
		    String[] title = {"部门编号","部门名称","部门人数"};
		    //创建JTable
		    this.tabDemo = new JTable(info,title){
				public boolean isCellEditable(int row,int column){
					return false;
				}
				
			};
		    //显示表头
		    this.jth = this.tabDemo.getTableHeader();
		    //将JTable加入到带滚动条的面板中
		    this.scpDemo.getViewport().add(tabDemo);
		}catch(Exception sqle){
			//sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "错误:"+sqle.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
}