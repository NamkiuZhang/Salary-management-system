import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class changePwd extends Frame implements ActionListener, ItemListener {
	// 数据库语句
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// 建立控件
	Label nameLab = new Label("帐   户");
	Label pwdLab1 = new Label("旧 密 码");
	Label pwdLab2 = new Label("新 密 码");
	Label pwdLab3 = new Label("确认新密码");
	TextField nameTxt = new TextField();
	TextField pwdTxt1 = new TextField();
	TextField pwdtxt2 = new TextField();
	TextField pwdTxt3 = new TextField();
	Choice userChoice = new Choice();
	Button confirmBut = new Button("确定");
	// Button addBut = new Button("增加");
	// Button modBut = new Button("修改");
	// Button delBut = new Button("删除");
	Button exitBut = new Button("退出");

	public changePwd() {
		super("系统用户管理");
		setSize(390, 400);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// 设置各控件的大小
		nameLab.setSize(50, 20);
		pwdLab1.setSize(50, 20);
		pwdLab2.setSize(50, 20);
		pwdLab3.setSize(70, 20);
		nameTxt.setSize(170, 20);
		pwdTxt1.setSize(170, 20);
		pwdtxt2.setSize(170, 20);
		pwdTxt3.setSize(170, 20);
		userChoice.setSize(170, 20);
		// addBut.setSize(50, 20);
		// modBut.setSize(50, 20);
		// delBut.setSize(50, 20);
		confirmBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// 设置各控件的位置
		nameLab.setLocation(80, 50);
		nameTxt.setLocation(160, 50);
		pwdLab1.setLocation(80, 100);
		pwdTxt1.setLocation(160, 100);
		pwdLab2.setLocation(80, 150);
		pwdtxt2.setLocation(160, 150);
		pwdLab3.setLocation(60, 200);
		pwdTxt3.setLocation(160, 200);
		userChoice.setLocation(160, 250);
		// addBut.setLocation(60, 200);
		// modBut.setLocation(140, 200);
		// delBut.setLocation(220, 200);
		confirmBut.setLocation(140, 300);
		exitBut.setLocation(220, 300);

		// 把各控件添加到Frame上
		add(nameLab);
		add(pwdLab1);
		add(pwdLab2);
		add(pwdLab3);
		add(nameTxt);
		add(pwdTxt1);
		add(pwdtxt2);
		add(pwdTxt3);
		add(userChoice);
		// add(addBut);
		// add(modBut);
		// add(delBut);
		add(confirmBut);
		add(exitBut);

		// 在按钮上注册监听器
		// addBut.addActionListener(this);
		// modBut.addActionListener(this);
		// delBut.addActionListener(this);
		confirmBut.addActionListener(this);
		exitBut.addActionListener(this);

		// 初始化userChoice列表
		initChoice();
		// 列表添加ItemListener监听器
		userChoice.addItemListener(this);

		pwdTxt1.setEchoChar('*');
		pwdtxt2.setEchoChar('*');
		pwdTxt3.setEchoChar('*');
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
			sql = "select name from users";
			rs = conn.getRs(sql);
			while (rs.next()) {
				userChoice.addItem(rs.getString("name"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent ee) {
		if (ee.getActionCommand().equals("退出"))
			dispose();
		if (ee.getActionCommand().equals("确定")) {
			// 先判断添加用户名是否为空密码是否为空
			if (nameTxt.getText().equals("") || pwdTxt1.getText().equals("")
					|| pwdtxt2.getText().equals("")
					|| pwdTxt3.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "不能为空", "警告", 1);
			} else {
				sql = "select *  from users where name='" + nameTxt.getText()
						+ "'";
				
				 String sql1 = "select * from users where name='"+nameTxt.getText()+"' and password='"+pwdTxt1.getText()+"'";
				 conn = new sqlconn();
				 ResultSet rs1 = conn.getRs(sql1);
				 try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "该用户不存在!", "警告", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					}// 原密码错误
					else if (!(rs1.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "旧密码错误", "错误提示", 1);
					}// 两次输入的密码不一致
					else if (!(pwdtxt2.getText().equals(pwdTxt3.getText()))) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "新密码不一致，请重新输入", "警告", 1);
					}else{
				     // try {
				    	  sql = "update users set password='" + pwdtxt2.getText()
				    			  + "'where name='" + nameTxt.getText() + "'";
				    	  conn = new sqlconn();
				    	  conn.dbMod(sql);
				    	  conn.dbClose(rs);
				    	  JOptionPane jop = new JOptionPane();
				    	  jop.showMessageDialog(null, "修改成功!", "提示信息", 1);
				    }
				}catch (Exception se) {
				}
				    
				nameTxt.setText("");
				pwdTxt1.setText("");
				pwdtxt2.setText("");
				pwdTxt3.setText("");
				conn = null;

				}
		}
	}

	// 把用户列表的第一用户名赋值给nameTxt文本框
	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
	}
}
