import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

class uInfoQue extends Frame implements ActionListener, ItemListener {
	String sql,sql1,sql2;
	ResultSet rs,rs1,rs2 = null;
	sqlconn conn = null;

	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;

	// 生成控件
	Label idLab = new Label("员工号");
	Label nameLab = new Label("员工名");
	Label dtmtLab = new Label("部门");
	
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	TextField dtmtTxt = new TextField();
	
	Choice userChoice = new Choice();
	Choice dtmtChoice = new Choice();
	
	Button queryBut = new Button("查询");
	Button exitBut = new Button("退出");

	public uInfoQue() {
		super("员工信息查询");
		setSize(600, 500);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		// 设置控件大小
		idLab.setSize(50, 20);
		nameLab.setSize(50, 20);
		dtmtLab.setSize(50,20);
		idTxt.setSize(130, 20);
		nameTxt.setSize(130, 20);
		dtmtTxt.setSize(130,20);
		
		userChoice.setSize(130, 20);
		dtmtChoice.setSize(130, 20);
		queryBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// 设置控件位置
		idLab.setLocation(80, 50);
		nameLab.setLocation(80, 80);
		dtmtLab.setLocation(80,110);
		idTxt.setLocation(150, 50);
		nameTxt.setLocation(150, 80);
		dtmtTxt.setLocation(150,110);
		dtmtChoice.setLocation(300, 110);
		userChoice.setLocation(300, 80);
		queryBut.setLocation(100, 150);
		exitBut.setLocation(400, 150);

		// 把各控件添加到Frame上
		add(idLab);
		add(nameLab);
		add(dtmtLab);
		add(idTxt);
		add(nameTxt);
		add(dtmtTxt);
		add(dtmtChoice);
		add(userChoice);
		add(queryBut);
		add(exitBut);

		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(50, 190, 500, 250);
		this.btnShow = new JButton("显示所有员工信息");
		this.btnShow.setBounds(180, 145, 200, 30);

		

		add(this.scpDemo);
		add(this.btnShow);
		this.setVisible(true);

		// 在两个按钮上注册监听器
		btnShow.addActionListener(this);
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);
		userChoice.addItemListener(this);
        dtmtChoice.addItemListener(this);
		// 初始化userChoice列表
		initChoice();
		initDtmtChoice();
		setVisible(true);

		// 窗口"关闭"按钮添加窗口适配器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void initChoice() {
		try {
			conn = new sqlconn();
			sql = "select name from employees";
			rs = conn.getRs(sql);
			while (rs.next()) {
				userChoice.addItem(rs.getString("name"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}

	public void initDtmtChoice() {
		try {
			conn = new sqlconn();
			sql = "select dtmt_name from department";
			rs = conn.getRs(sql);
			while (rs.next()) {
				dtmtChoice.addItem(rs.getString("dtmt_name"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("退出"))
			dispose();
		if (ae.getActionCommand().equals("查询")) {
			//未输入查询内容
			if (idTxt.getText().equals("") && nameTxt.getText().equals("") && dtmtTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "请输入你要查询既内容!", "警告", 0);
				dispose();
				uInfoQue uiq = new uInfoQue();
				return;
			}
			
			else{
				
				    sql1 = "select count(*) from employees where name = '"
							+ nameTxt.getText() + "' or id like '%" + idTxt.getText()
									+ "%' or department='"+ dtmtTxt.getText() + "'";
					 sql2 = "select * from employees where name = '"
						+ nameTxt.getText() + "' or id like '%" + idTxt.getText()
						+ "%'or department='"+ dtmtTxt.getText() + "'";
			}
				conn = new sqlconn();
				//System.out.println(nameTxt.getText());
          try{
				 rs1 = conn.getRs(sql1);
				//System.out.println(rs1);
				int count = 0;
				// 得到总记录数
				if (rs1.next()) {
					count = rs1.getInt(1);
				}
				rs1.close();

				Object[][] info = new Object[count][8];
				count = 0;
				 rs2 = conn.getRs(sql2);
				

				while (rs2.next()) {
					info[count][0] = rs2.getString("id");
					info[count][1] = rs2.getString("name");
					info[count][2] = rs2.getString("sex");
					info[count][3] = rs2.getString("age");
					info[count][4] = rs2.getString("post");
					info[count][5] = rs2.getString("department");
					info[count][6] = rs2.getString("eduBg");
					info[count][7] = rs2.getString("baswage");
					count++;
				}
				for (Object[] ob : info) {
					System.out.println(ob[0]);
					System.out.println(ob[1]);
					System.out.println(ob[2]);
					System.out.println(ob[3]);
					System.out.println(ob[4]);
					System.out.println(ob[5]);
					System.out.println(ob[6]);
					System.out.println(ob[7]);

				}

				String[] title = { "员工号", "员工姓名", "性别", "年龄", "职称", "部门",
						"学历", "工资" };
				// 创建JTable
				this.tabDemo = new JTable(info, title){
					public boolean isCellEditable(int row,int column){
						return false;
					}
					
				};
				// 显示表头
				this.jth = this.tabDemo.getTableHeader();
				// 将JTable加入到带滚动条的面板中
				this.scpDemo.getViewport().add(tabDemo);
			} catch (Exception sqle) {
				// sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "错误:" + sqle.getMessage(),
						"错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (ae.getActionCommand().equals("显示所有员工信息")) {
			try {
				String sql1 = "select count(*) from employees";
				String sql2 = "select * from employees";

				conn = new sqlconn();

				ResultSet rs1 = conn.getRs(sql1);
				int count = 0;
				// 得到总记录数
				if (rs1.next()) {
					count = rs1.getInt(1);
				}
				rs1.close();

				Object[][] info = new Object[count][8];
				count = 0;
				ResultSet rs2 = conn.getRs(sql2);

				while (rs2.next()) {
					info[count][0] = rs2.getString("id");
					info[count][1] = rs2.getString("name");
					info[count][2] = rs2.getString("sex");
					info[count][3] = rs2.getString("age");
					info[count][4] = rs2.getString("post");
					info[count][5] = rs2.getString("department");
					info[count][6] = rs2.getString("eduBg");
					info[count][7] = rs2.getString("baswage");
					count++;
				}
				for (Object[] ob : info) {
					System.out.println(ob[0]);
					System.out.println(ob[1]);
					System.out.println(ob[2]);
					System.out.println(ob[3]);
					System.out.println(ob[4]);
					System.out.println(ob[5]);
					System.out.println(ob[6]);
					System.out.println(ob[7]);

				}

				String[] title = { "员工号", "员工姓名", "性别", "年龄", "职称", "部门",
						"学历", "工资" };
				// 创建JTable
				this.tabDemo = new JTable(info, title){
					public boolean isCellEditable(int row,int column){
						return false;
					}
					
				};
				//问题是滚动的范围是预设的这个dimension的大小
				tabDemo.setPreferredSize(new Dimension(800,300));
				// 显示表头
				this.jth = this.tabDemo.getTableHeader();
				// 将JTable加入到带滚动条的面板中
				this.scpDemo.getViewport().add(tabDemo);
			} catch (Exception sqle) {
				// sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "错误:" + sqle.getMessage(),
						"错误", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
		dtmtTxt.setText(dtmtChoice.getSelectedItem());
	}
}
