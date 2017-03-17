import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class addEmInfo extends Frame implements ActionListener, ItemListener {
	String sql;
	ResultSet rs = null;
	sqlconn conn = null;
	sqlconn connT = null;
	//employeeDAO edao=new employeeDAO();

	// 生成控件
	Label idLab = new Label("员工号");
	Label nameLab = new Label("员工名");
	Label sexLab = new Label("性别");
	Label ageLab = new Label("年龄");
	Label postLab = new Label("职称");
	Label dtmtLab = new Label("部门");
	Label wageLab = new Label("基本工资");
	Label edubgLab = new Label("学历");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	TextField sexTxt = new TextField("");
	TextField ageTxt = new TextField("");
	TextField postTxt = new TextField("");
	// TextField dtmtTxt=new TextField("");
	TextField wageTxt = new TextField("");
	Choice dtmtChoice = new Choice();
	TextField eduTxt = new TextField("");
	//Choice edubgChoice = new Choice();
	
	//Choice idChoice = new Choice();
	Button addBut = new Button("添加");
	//Button modBut = new Button("修改");
	Button delBut = new Button("删除");
	Button clrBut = new Button("清空");
	Button exitBut = new Button("退出");
	
	//String items[]={"高中以下","高中","中专","本科","研究生","博士"};
	//JComboBox cb = new JComboBox(items);

	public addEmInfo() {
		super(" 添加员工");
		setSize(350, 400);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		// 设置控件大小
		idLab.setSize(50, 20);
		nameLab.setSize(50, 20);
		sexLab.setSize(50, 20);
		ageLab.setSize(50, 20);
		postLab.setSize(50, 20);
		dtmtLab.setSize(50, 20);
		wageLab.setSize(70, 20);
		edubgLab.setSize(50, 20);
		idTxt.setSize(100, 20);
		nameTxt.setSize(100, 20);
		sexTxt.setSize(100, 20);
		ageTxt.setSize(100, 20);
		postTxt.setSize(100, 20);
		// dtmtTxt.setSize(100,20);
		wageTxt.setSize(100, 20);
		eduTxt.setSize(100, 20);

		dtmtChoice.setSize(120, 20);
		//edubgChoice.setSize(100, 20);
		
		//idChoice.setSize(100, 20);
		addBut.setSize(40, 20);
		//modBut.setSize(40, 20);
		delBut.setSize(40, 20);
		clrBut.setSize(40, 20);
		exitBut.setSize(40, 20);

		// 设置控件位置
		idLab.setLocation(80, 50);
		nameLab.setLocation(80, 80);
		sexLab.setLocation(80, 110);
		ageLab.setLocation(80, 140);
		postLab.setLocation(80, 170);
		dtmtLab.setLocation(80, 200);
		wageLab.setLocation(80, 230);
		edubgLab.setLocation(80, 260);
		idTxt.setLocation(160, 50);
		nameTxt.setLocation(160, 80);
		sexTxt.setLocation(160, 110);
		ageTxt.setLocation(160, 140);
		postTxt.setLocation(160, 170);
		// dtmtTxt.setLocation(160,200);
		wageTxt.setLocation(160, 230);
		eduTxt.setLocation(160, 260);
		dtmtChoice.setLocation(160, 200);
		//edubgChoice.setLocation(160, 260);
		
		//idChoice.setLocation(160, 290);
		addBut.setLocation(60, 320);
		//modBut.setLocation(100, 320);
		delBut.setLocation(115, 320);
		clrBut.setLocation(170, 320);
		exitBut.setLocation(225, 320);

		// 把各控件添加到Frame上
		add(idLab);
		add(nameLab);
		add(sexLab);
		add(ageLab);
		add(postLab);
		add(dtmtLab);
		add(wageLab);
		add(edubgLab);
		add(idTxt);
		add(nameTxt);
		add(sexTxt);
		add(ageTxt);
		add(postTxt);
		// add(dtmtTxt);
		add(wageTxt);

		add(dtmtChoice);
		//add(edubgChoice);
		add(eduTxt);
		//add(idChoice);
		add(addBut);
		//add(modBut);
		add(delBut);
		add(clrBut);
		add(exitBut);

		// 在四个按钮上注册监听器
		addBut.addActionListener(this);
		//modBut.addActionListener(this);
		delBut.addActionListener(this);
		clrBut.addActionListener(this);
		exitBut.addActionListener(this);

		// dtmtChoice.addItemListener(this);
		//idChoice.addItemListener(this);
		
		//cb.addItemListener(this);
			 
		// 初始化列表
		initDtmtChoice();
		//initEdubgChoice();
		//initIdChoice();

		wageTxt.setText("0");
		//wageTxt.setEditable(false);
		setVisible(true);

		// 窗口"关闭"按钮添加窗口适配器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
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
	
	//public void initEdubgChoice() {
		
		//int i = 1;
		//while (i <= 8) {
			//edubgChoice.addItem(i + "级");
			//i++;
		//}
		
		//cb.addItem(items);
		
	//}
/*
	public void initIdChoice() {
		try {
			conn = new sqlconn();
			sql = "select id from employees";
			rs = conn.getRs(sql);
			while (rs.next()) {
				idChoice.addItem(rs.getString("id"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}
	*/
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("退出"))
			dispose();
		//清空所填的信息
		if (ae.getActionCommand().equals("清空")) {
			idTxt.setText("");
			nameTxt.setText("");
			sexTxt.setText("");
			ageTxt.setText("");
			postTxt.setText("");
			wageTxt.setText("");
		}
		if (ae.getActionCommand().equals("添加")) {
			if(idTxt.getText().equals("")|| nameTxt.getText().equals("") || dtmtChoice.getSelectedItem().equals("")){
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "请输入员工号和姓名 选择部门", "警告", 1);
			}else{
			sql = "select * from employees where id='" + idTxt.getText() + "'";
			//conn = new sqlconn();
			try {
				rs = conn.getRs(sql);
				//rs=edao.getEmBYId(Integer.parseInt(idTxt.getText()));
				//rs=edao.getEmBYId(idTxt.getText());
				if (rs.next()) {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "该员工已经存在!", "警告", 1);
					conn.dbClose(rs);
					conn = null;
					rs = null;
				} else {
					try {
						/*sql = "insert into employees values('"
								+ idTxt.getText() + "','" + nameTxt.getText()
								+ "','" + sexTxt.getText() + "','"
								+ ageTxt.getText() + "','" + postTxt.getText()
								+ "','" + dtmtChoice.getSelectedItem() + "','"
								+ wageTxt.getText() + "','"
								+ eduTxt.getText() + "')";
								*/
						System.out.println(ageTxt.getText());
						sql = "insert into employees values('" + nameTxt.getText()
								+ "','" + sexTxt.getText() + "','"
								+ ageTxt.getText() + "','" + postTxt.getText()
								+ "','" + dtmtChoice.getSelectedItem() + "','"
								+ eduTxt.getText() + "','"
								+ idTxt.getText() + "','"
								+ wageTxt.getText() + "')";
												//System.out.println(nameTxt.getText());
						//System.out.println(nameTxt.getText());
						/*sql = "insert into employees values('"
								+ idTxt.getText() + "','" + nameTxt.getText()
								+ "','" + sexTxt.getText() + "','"
								+ ageTxt.getText() + "','" + postTxt.getText()
								+ "','" + dtmtChoice.getSelectedItem() + "','"
								+ wageTxt.getText() + "')";*/
						
						conn = new sqlconn();
						conn.dbMod(sql);
						conn.dbClose(rs);
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "添加成功!", "提示信息", 1);
					    
						conn = null;
					} catch (Exception se) {
						se.printStackTrace();
					}
					//idChoice.addItem(idTxt.getText());
					idTxt.setText("");
					nameTxt.setText("");
					sexTxt.setText("");
					ageTxt.setText("");
					postTxt.setText("");
					//dtmtTxt.setText("");
					wageTxt.setText("");
					// wTypeTxt.setText("");
					conn = null;
					connT = null;
				}
			} catch (Exception a) {
			}
			}
		}
		/*
		if (ae.getActionCommand().equals("删除")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "你确定要删除该员工及与他相关的所有数据吗?", "请确认", 1) == 0) {
				try {
					conn = new sqlconn();
					sql = "delete from employees where id='" + idTxt.getText()
							+ "'";
					conn.dbMod(sql);
					conn.dbClose(rs);
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "删除成功!", "提示信息", 1);
					sql = "delete from wages where id='" + idTxt.getText()
							+ "'";
					connT = new sqlconn();
					connT.dbMod(sql);
					connT.dbClose(rs);
				} catch (Exception se) {
				}
				conn = null;
				connT = null;
				idChoice.remove(idChoice.getSelectedIndex());
				idTxt.setText("");
				nameTxt.setText("");
				sexTxt.setText("");
				ageTxt.setText("");
				postTxt.setText("");
				// dtmtTxt.setText("");
				wageTxt.setText("");
				// wTypeTxt.setText("");
			}
		}*/
	}

	public void itemStateChanged(ItemEvent ie) {
		/*
		String sqlTemp;
		ResultSet rsTemp = null;

		//idTxt.setText(idChoice.getSelectedItem());

		sqlTemp = "select * from employees where id='" + idTxt.getText() + "'";
		try {
			sqlconn sConn = new sqlconn();
			rsTemp = sConn.getRs(sqlTemp);
			if (rsTemp.next()) {
				idTxt.setText(rsTemp.getString("id"));
				nameTxt.setText(rsTemp.getString("name"));
				sexTxt.setText(rsTemp.getString("sex"));
				ageTxt.setText(rsTemp.getString("age"));
				postTxt.setText(rsTemp.getString("post"));
				wageTxt.setText(rsTemp.getString("baswage"));
				eduTxt.setText(rsTemp.getString("eduBg"));
				// fWageTxt.setText(rsTemp.getString("factwage"));
				dtmtChoice.select(rsTemp.getString("department"));
				//edubgChoice.select(rsTemp.getString("wagetype"));
				//cb.getItemAt(cb.getSelectedIndex());
				//cb.setSelectedItem(rsTemp.getString("eduBg"));
				sConn.dbClose(rsTemp);
				sConn = null;
				rsTemp = null;
				sConn = null;
			}
			
		} catch (Exception e) {
			System.out.println("列表连接数据库出错!");
			e.printStackTrace();
		}*/
	}
}