import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

class addDtmt extends Frame implements ActionListener, ItemListener {
	// 数据库语句
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// 建立控件
	Label idLab = new Label("部门编号");
	Label nameLab = new Label("部门名称");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	Choice idChoice = new Choice();
	Button addBut = new Button("增加");
	Button delBut = new Button("删除");
	Button clrBut = new Button("清空");
	Button exitBut = new Button("退出");

	public addDtmt() {
		super("编辑部门信息");
		setSize(380, 250);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// 设置各控件的大小
		idLab.setSize(70, 20);
		nameLab.setSize(70, 20);
		idTxt.setSize(170, 20);
		nameTxt.setSize(170, 20);
		idChoice.setSize(170, 20);
		addBut.setSize(50, 20);
		delBut.setSize(50, 20);
		clrBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// 设置各控件的位置
		idLab.setLocation(80, 50);
		idTxt.setLocation(160, 50);
		nameLab.setLocation(80, 100);
		nameTxt.setLocation(160, 100);
		idChoice.setLocation(160, 150);
		addBut.setLocation(60, 200);
		delBut.setLocation(130, 200);
		clrBut.setLocation(200, 200);
		exitBut.setLocation(270, 200);

		// 把各控件添加到Frame上
		add(idLab);
		add(nameLab);
		add(idTxt);
		add(nameTxt);
		add(idChoice);
		add(addBut);
		add(delBut);
		add(clrBut);
		add(exitBut);

		// 在三个按钮上注册监听器
		addBut.addActionListener(this);
		delBut.addActionListener(this);
		clrBut.addActionListener(this);
		exitBut.addActionListener(this);

		// 初始化userChoice列表
		initChoice();
		// 列表添加ItemListener监听器
		idChoice.addItemListener(this);

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
			sql = "select dtmt_id from department";
			rs = conn.getRs(sql);
			while (rs.next()) {
				idChoice.addItem(rs.getString("dtmt_id"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}

	public void actionPerformed(ActionEvent ee) {
		if (ee.getActionCommand().equals("退出"))
			dispose();
		if (ee.getActionCommand().equals("清空")) {
			idTxt.setText("");
			nameTxt.setText("");
		}
		if (ee.getActionCommand().equals("增加")) {
			if(idTxt.getText().equals("") || nameTxt.getText().equals("")){
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "请输入添加内容!", "警告", 0);
			}else{
			sql = "select *  from department where dtmt_id='" + idTxt.getText()
					+ "' or dtmt_name = '" + nameTxt.getText() + "'";
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
						sql = "insert into department values('"
								+ idTxt.getText() + "','" + nameTxt.getText()
								+ "')";
						conn = new sqlconn();
						conn.dbMod(sql);
						conn.dbClose(rs);
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "添加成功!", "提示信息", 1);
					} catch (Exception se) {
					}
					idChoice.addItem(idTxt.getText());
					idTxt.setText("");
					nameTxt.setText("");
					conn = null;
				}
			} catch (Exception a) {
			}
		}
		}
		if (ee.getActionCommand().equals("删除")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "你确定要删除此记录吗?", "请确认", 1) == 0) {

				sql = "select * from department where dtmt_id='"
						+ idTxt.getText() + "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "没有该部门,删除失败!", "警告", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					}
				} catch (Exception exc) {
				}

				try {
					sql = "select count(*) from employees where department='"
							+ nameTxt.getText() + "'";
					ResultSet rs1 = conn.getRs(sql);
					int count1 = 0;
					if (rs1.next()) {
						count1 = rs1.getInt(1);
					}
					//System.out.println(count1);
					if (count1 > 0) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "该部门有员工，无法删除!", "警告", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					} else {

						conn = new sqlconn();
						sql = "delete from department where dtmt_id='"
								+ idTxt.getText() + "'";
						conn.dbMod(sql);

						conn.dbClose(rs);
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "删除成功!", "提示信息", 1);
					}
				} catch (Exception se) {
				}
				conn = null;
				idChoice.remove(idChoice.getSelectedIndex());
				idTxt.setText("");
				nameTxt.setText("");
			}
		}
	}

	// 把用户列表所选的id赋值给idTxt文本框
	public void itemStateChanged(ItemEvent ie) {
		String sqlTemp;
		ResultSet rsTemp = null;
		idTxt.setText(idChoice.getSelectedItem());
		sqlTemp = "select * from department where dtmt_id='" + idTxt.getText()
				+ "'";
		try {
			sqlconn sConn = new sqlconn();
			rsTemp = sConn.getRs(sqlTemp);
			if (rsTemp.next()) {
				idTxt.setText(rsTemp.getString("dtmt_id"));
				nameTxt.setText(rsTemp.getString("dtmt_name"));
				sConn.dbClose(rsTemp);
				sConn = null;
				rsTemp = null;
				sConn = null;
			}
		} catch (Exception e) {
			System.out.println("列表连接数据库出错!");
		}
	}
}