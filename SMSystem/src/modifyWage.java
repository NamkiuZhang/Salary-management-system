import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

class modifyWage extends Frame implements ActionListener, ItemListener,
		TextListener {
	String sql;
	ResultSet rs = null;
	sqlconn conn = null;
	sqlconn connT = null;

	Label idLab = new Label("员工号");
	Label nameLab = new Label("员工名");
	Label choiceEmIdLab = new Label("选择员工");
	//Label wTypeLab = new Label("工资类型");
	Label bWageLab = new Label("基本工资");
	Label bonusLab = new Label("奖  金");
	Label mWageLab = new Label("应减工资");
	Label fWageLab = new Label("实际工资");
	Label wDateLab = new Label("工资日期");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	//TextField wTypeTxt = new TextField("");
	TextField bWageTxt = new TextField("");
	TextField bonusTxt = new TextField("");
	TextField mWageTxt = new TextField("");
	TextField fWageTxt = new TextField("");
	TextField wDateTxt = new TextField("");
	Label yearLab = new Label("年");
	Label monthLab = new Label("月");

	Choice idChoice = new Choice();
	Choice yearChoice = new Choice();
	Choice monthChoice = new Choice();

	
	Button modBut = new Button("修改");
	Button delBut = new Button("删除");
	Button clrBut = new Button("清空");
	Button exitBut = new Button("退出");

	public modifyWage() {
		super("修改工资信息");
		setSize(350, 430);
		setResizable(false);
		setLayout(null);
		fWageTxt.setEditable(false);

		this.setLocationRelativeTo(this.getParent());

		// 设置控件大小
		choiceEmIdLab.setSize(70,20);
		idLab.setSize(50, 20);
		nameLab.setSize(50, 20);
		bWageLab.setSize(70, 20);
		bonusLab.setSize(50, 20);
		mWageLab.setSize(70, 20);
		fWageLab.setSize(70, 20);
		wDateLab.setSize(70, 20);
		idTxt.setSize(100, 20);
		nameTxt.setSize(100, 20);
		idTxt.setSize(100, 20);
		nameTxt.setSize(100, 20);
		bWageTxt.setSize(100, 20);
		bonusTxt.setSize(100, 20);
		mWageTxt.setSize(100, 20);
		fWageTxt.setSize(100, 20);
		wDateTxt.setSize(100, 20);

		yearLab.setSize(20, 20);
		monthLab.setSize(20, 20);
		idChoice.setSize(100, 20);
		yearChoice.setSize(90, 25);
		monthChoice.setSize(60, 25);

		
		modBut.setSize(40, 20);
		delBut.setSize(40, 20);
		clrBut.setSize(40, 20);
		exitBut.setSize(40, 20);

		// 设置控件位置
		choiceEmIdLab.setLocation(80,50);
		idLab.setLocation(94, 80);
		nameLab.setLocation(94, 110);
		bWageLab.setLocation(80, 140);
		bonusLab.setLocation(100, 170);
		mWageLab.setLocation(80, 200);
		fWageLab.setLocation(80, 230);
		wDateLab.setLocation(80, 260);
		idTxt.setLocation(160, 80);
		nameTxt.setLocation(160, 110);
		//wTypeTxt.setLocation(160, 110);
		bWageTxt.setLocation(160, 140);
		bonusTxt.setLocation(160, 170);
		mWageTxt.setLocation(160, 200);
		fWageTxt.setLocation(160, 230);
		wDateTxt.setLocation(160, 260);

		yearLab.setLocation(160, 290);
		monthLab.setLocation(240, 290);
		idChoice.setLocation(160, 50);
		yearChoice.setLocation(70, 290);
		monthChoice.setLocation(180, 290);

		
		modBut.setLocation(70, 360);
		delBut.setLocation(125, 360);
		clrBut.setLocation(180, 360);
		exitBut.setLocation(235, 360);

		// 把各控件添加到Frame上
		add(idLab);
		add(nameLab);
		add(choiceEmIdLab);
		add(bWageLab);
		add(bonusLab);
		add(mWageLab);
		add(fWageLab);
		add(wDateLab);
		add(idTxt);
		add(nameTxt);
		add(bWageTxt);
		add(bonusTxt);
		add(mWageTxt);
		add(fWageTxt);
		add(wDateTxt);
		add(yearLab);
		add(monthLab);
		add(idChoice);
		add(yearChoice);
		add(monthChoice);
		
		add(modBut);
		add(delBut);
		add(clrBut);
		add(exitBut);

		// 在五个按钮上注册监听器
		
		modBut.addActionListener(this);
		delBut.addActionListener(this);
		clrBut.addActionListener(this);
		exitBut.addActionListener(this);

		bWageTxt.addTextListener(this);
		bonusTxt.addTextListener(this);
		mWageTxt.addTextListener(this);
		// fWageTxt.addTextListener(this);

		yearChoice.addItemListener(this);
		monthChoice.addItemListener(this);
		idChoice.addItemListener(this);

		initIdChoice();
		initYChoice();
		initMChoice();

		idTxt.setEditable(false);
		nameTxt.setEditable(false);
		wDateTxt.setEditable(false);
		setVisible(true);

		// 窗口"关闭"按钮添加窗口适配器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void initIdChoice() {
		try {
			conn = new sqlconn();
			sql = "select distinct id from wages";
			rs = conn.getRs(sql);
			while (rs.next()) {
				idChoice.addItem(rs.getString("id"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initYChoice() {
		int i = 2015;
		String t = "";
		while (i < 2051) {
			t = String.valueOf(i);
			yearChoice.addItem(t);
			i++;
		}
	}

	public void initMChoice() {
		int i = 1;
		String t;
		while (i <= 12) {
			t = String.valueOf(i);
			monthChoice.addItem(t);
			i++;
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("退出"))
			dispose();
		if (ae.getActionCommand().equals("清空")) {
			idTxt.setText("");
			nameTxt.setText("");
			bWageTxt.setText("");
			bonusTxt.setText("");
			mWageTxt.setText("");
			fWageTxt.setText("");
		}
		if (ae.getActionCommand().equals("修改")) {
			try {

				// 测试部分
				sql = "select * from wages where id='" + idTxt.getText() + "' and wageDate='"+ wDateTxt.getText() + "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "信息不存在,无法修改!", "警告", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					}
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				// 以上是测试部分
                //只修改那一条记录
				sql = "update wages  set name='" + nameTxt.getText()
						+ "',baswage='"
						+ bWageTxt.getText() + "',bonus='" + bonusTxt.getText()
						+ "',deductwage='" + mWageTxt.getText() + "',factwage='"
						+ fWageTxt.getText() + "' where id='" + idTxt.getText()
						+ "' and wageDate='"+ wDateTxt.getText() + "'";
				conn = new sqlconn();
				conn.dbMod(sql);
				conn.dbClose(rs);
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "修改成功!", "提示信息", 1);
				// System.out.println(fWageTxt.getText());
				//sql = "update employees set wage='" + bWageTxt.getText()
						//+ "' where id='" + idTxt.getText() + "'";
				connT = new sqlconn();
				connT.dbMod(sql);
				connT.dbClose(rs);
			} catch (Exception se) {
				System.out.println("Question");
			}
			idTxt.setText("");
			nameTxt.setText("");
			
			bWageTxt.setText("");
			bonusTxt.setText("");
			mWageTxt.setText("");
			fWageTxt.setText("");
			wDateTxt.setText("");
			conn = null;
			connT = null;
		}
		if (ae.getActionCommand().equals("删除")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "你确定要删除此条记录吗?", "请确认", 1) == 0) {
				int i = 0;
				sql = "select * from wages where id='" + idTxt.getText() + "' and wageDate= '"+ wDateTxt.getText() +"'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					 if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "无该条记录,删除失败!", "警告", 1);
					
					conn.dbClose(rs);
					sql = "";
					conn = null;
					rs = null;
					return;
				}else{
						String sql1;
						 conn = new sqlconn();
						 sqlconn sConn1 = new sqlconn();
							sql1 = "delete from wages where id='" + idTxt.getText()
									+ "' and wageDate= '"+ wDateTxt.getText() +"'";
							conn.dbMod(sql1);
							conn.dbClose(rs);
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null, "删除成功!", "提示信息", 1);
				     } 
				}catch (Exception exc) {}
				conn = null;
				idTxt.setText("");
				nameTxt.setText("");
				bWageTxt.setText("");
				bonusTxt.setText("");
				mWageTxt.setText("");
				fWageTxt.setText("");
			}
		}
	}

	public void itemStateChanged(ItemEvent ie) {
		String sqlTemp;
		ResultSet rsTemp = null;
		idTxt.setText(idChoice.getSelectedItem());
		wDateTxt.setText(yearChoice.getSelectedItem() + "-"
				+ monthChoice.getSelectedItem());
		
		

		sqlTemp = "select * from wages where id='" + idTxt.getText() + "' and wageDate= '"+ wDateTxt.getText() +"'";
		try {
			sqlconn sConn = new sqlconn();
			rsTemp = sConn.getRs(sqlTemp);
			if(rsTemp.next()) {
				nameTxt.setText(rsTemp.getString("name"));
				bWageTxt.setText(rsTemp.getString("baswage"));
				bonusTxt.setText(rsTemp.getString("bonus"));
				mWageTxt.setText(rsTemp.getString("deductwage"));
				fWageTxt.setText(rsTemp.getString("factwage"));
				wDateTxt.setText(rsTemp.getString("wagedate"));
				System.out.println(rsTemp.getString("id"));	
				sConn.dbClose(rsTemp);
				sConn = null;
				rsTemp = null;
				
			}else{
				
			    String sqlTemp1;
				sqlTemp1="select * from employees where id='"+ idTxt.getText() + "'";
				sqlconn sConn1 = new sqlconn();
			
				rsTemp = sConn1.getRs(sqlTemp1);
				if(rsTemp.next()){
					String name =rsTemp.getString("name");
					if(name == null){
						name="";
					}
					nameTxt.setText(name);
				}
					
				
				bWageTxt.setText(rsTemp.getString("baswage"));
				//wDateTxt.setText(rsTemp.getString("wagedate"));
				 wDateTxt.setText(wDateTxt.getText());
				 
				 sConn1.dbClose(rsTemp);
				 sConn1 = null;
					rsTemp = null;
					//sConn1 = null;
			}
			
		 }catch (SQLException se) {
			System.out.println("列表连接数据库出错!");
			se.printStackTrace();
		}
	}

	public void textValueChanged(TextEvent te) {
		String t1, t2, t3, fw;
		double td1, td2, td3, sum;
		t1 = bWageTxt.getText();
		t2 = bonusTxt.getText();
		t3 = mWageTxt.getText();
		if (t1.equals(""))
			t1 = "0";
		if (t2.equals(""))
			t2 = "0";
		if (t3.equals(""))
			t3 = "0";
		try {

			td1 = Double.parseDouble(t1);
			td2 = Double.parseDouble(t2);
			td3 = Double.parseDouble(t3);
			sum = td1 + td2 - td3;
			fw = String.valueOf(sum);
			fWageTxt.setText(fw);
			// fWageTxt.setText("1");
		} catch (NumberFormatException dfe) {
		}
	}
}