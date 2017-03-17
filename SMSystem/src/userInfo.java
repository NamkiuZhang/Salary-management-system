import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
//import java.awt.Window;
//import java.util.*;

class userInfo extends Frame implements ActionListener, ItemListener {
	// 数据库语句
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// 建立控件
	Label nameLab = new Label("帐   户");
	Label pwdLab = new Label("密   码");
	TextField nameTxt = new TextField();
	TextField pwdTxt = new TextField();
	Choice userChoice = new Choice();
	Button addBut = new Button("增加");
	Button delBut = new Button("删除");
	Button exitBut = new Button("退出");

	public userInfo() {
		super("系统用户管理");
		setSize(410, 250);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// 设置各控件的大小
		nameLab.setSize(50, 20);
		pwdLab.setSize(50, 20);
		nameTxt.setSize(170, 20);
		pwdTxt.setSize(170, 20);
		userChoice.setSize(170, 20);
		addBut.setSize(55, 20);
		delBut.setSize(55, 20);
		exitBut.setSize(55, 20);

		// 设置各控件的位置
		nameLab.setLocation(80, 50);
		nameTxt.setLocation(160, 50);
		pwdLab.setLocation(80, 100);
		pwdTxt.setLocation(160, 100);
		userChoice.setLocation(160, 150);
		addBut.setLocation(90, 200);
		delBut.setLocation(180, 200);
		exitBut.setLocation(270, 200);

		// 把各控件添加到Frame上
		add(nameLab);
		add(pwdLab);
		add(nameTxt);
		add(pwdTxt);
		add(userChoice);
		add(addBut);
		add(delBut);
		add(exitBut);

		// 在四个按钮上注册监听器
		addBut.addActionListener(this);
		delBut.addActionListener(this);
		exitBut.addActionListener(this);

		// 初始化userChoice列表
		initChoice();
		// 列表添加ItemListener监听器
		userChoice.addItemListener(this);

		pwdTxt.setEchoChar('*');
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
		if (ee.getActionCommand().equals("增加")) {
			// 先判断添加用户名是否为空密码是否为空
			if (nameTxt.getText().equals("") || pwdTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "用户名和密码不能为空", "警告", 1);
			} else {
				sql = "select *  from users where name='" + nameTxt.getText()
						+ "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (rs.next()) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "该用户已经存在!", "警告", 1);
						conn.dbClose(rs);
						conn = null;
						rs = null;
					} else {
						try {
							sql = "insert into users values('"
									+ nameTxt.getText() + "','"
									+ pwdTxt.getText() + "')";
							conn = new sqlconn();
							conn.dbMod(sql);
							conn.dbClose(rs);
							JOptionPane jop = new JOptionPane();
							jop.showMessageDialog(null, "添加成功!", "提示信息", 1);
						} catch (Exception se) {
						}
						userChoice.addItem(nameTxt.getText());
						nameTxt.setText("");
						pwdTxt.setText("");
						conn = null;
					}
				} catch (Exception a) {
				}
			}
		}
		if (ee.getActionCommand().equals("删除")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "你确定要删除此记录吗?", "请确认", 1) == 0) {

				sql = "select * from users where name='" + nameTxt.getText()
						+ "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "用户不存在,删除失败!", "警告", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					}
				} catch (Exception exc) {
				}

				try {
					conn = new sqlconn();
					sql = "delete from users where name='" + nameTxt.getText()
							+ "'";
					conn.dbMod(sql);
					conn.dbClose(rs);
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "删除成功!", "提示信息", 1);
				} catch (Exception se) {
				}
				conn = null;
				userChoice.remove(userChoice.getSelectedIndex());
				nameTxt.setText("");
				pwdTxt.setText("");
			}
		}
	}

	// 把用户列表的第一用户名赋值给nameTxt文本框
	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
	}
}