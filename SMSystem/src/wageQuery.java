import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

class wageQuery extends Frame implements ActionListener, ItemListener {
	String sql,sql1,sql2;
	ResultSet rs,rs1,rs2= null;
	sqlconn conn = null;

	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;

	Label idLab = new Label("员工号");
	Label nameLab = new Label("员工名");
	Label wDateLab = new Label("工资日期");

	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	TextField dateTxt = new TextField();
	//Choice dateChoice = new Choice();
	Label yearLab=new Label("年");
	Label monthLab=new Label("月");
	
	Choice yearChoice=new Choice();
	Choice monthChoice=new Choice();

	Button queryBut = new Button("查询");
	Button exitBut = new Button("退出");

	public wageQuery() {
		super("工资查询");
		setSize(600, 500);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		// 设置控件大小
		idLab.setSize(50, 20);
		nameLab.setSize(50, 20);
		wDateLab.setSize(70, 20);

		idTxt.setSize(130, 20);
		nameTxt.setSize(130, 20);
		dateTxt.setSize(130, 20);
		queryBut.setSize(50, 20);
		exitBut.setSize(50, 20);
		//dateChoice.setSize(150, 20);
		
		yearLab.setSize(20,20);
		monthLab.setSize(20,20);
		yearChoice.setSize(90,20);
		monthChoice.setSize(60,20);

		// 设置控件位置
		idLab.setLocation(74, 50);
		nameLab.setLocation(330, 50);
		wDateLab.setLocation(60, 90);
		
		idTxt.setLocation(140, 50);
		nameTxt.setLocation(400, 50);
		dateTxt.setLocation(140, 90);
		//dateChoice.setLocation(340, 90);
		yearChoice.setLocation(320,90);
		monthChoice.setLocation(445,90);
		yearLab.setLocation(415,90);
		monthLab.setLocation(510,90);
		
		queryBut.setLocation(200, 140);
		exitBut.setLocation(310, 140);
		// 把各控件添加到Frame上
		add(idLab);
		add(nameLab);
		add(wDateLab);
		add(idTxt);
		add(nameTxt);
		add(dateTxt);
		add(queryBut);
		add(exitBut);
		//add(dateChoice);
		add(yearLab);
		add(monthLab);
		
		add(yearChoice);
		add(monthChoice);

		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(50, 200, 500, 260);// 左 上 宽 高
		// this.btnShow = new JButton("显示工资信息");
		// this.btnShow.setBounds(70, 150, 250, 30);

		add(this.scpDemo);
		// add(this.btnShow);
		this.setVisible(true);

		// 在两个按钮上注册监听器
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);
		//dateChoice.addItemListener(this);
		yearChoice.addItemListener(this);
		monthChoice.addItemListener(this);

		//initDateChoice();
		initYChoice();
		initMChoice();
		setVisible(true);

		// 窗口"关闭"按钮添加窗口适配器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
/*
	public void initDateChoice() {
		try {
			conn = new sqlconn();
			sql = "select distinct wageDate from wages";
			rs = conn.getRs(sql);
			while (rs.next()) {
				dateChoice.addItem(rs.getString("wageDate"));
			}
			conn.dbClose(rs);
			conn = null;
		} catch (Exception e) {
		}
	}
	*/
	public void initYChoice(){
		int i=2015;
		String t="";
		while(i<2050){
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
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("退出"))
			dispose();
		if (ae.getActionCommand().equals("查询")) {
			// 都为空
			if (idTxt.getText().equals("") && nameTxt.getText().equals("")&&dateTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "请输入查询内容!", "警告", 0);
				dispose();
				wageQuery uIn1 = new wageQuery();
				return;
			}else{
				sql1 = "select count(*) from wages where id ='"
						+ idTxt.getText() + "' or name='"
						+ nameTxt.getText() + "' or wageDate='" + dateTxt.getText() + "'";
				 sql2 = "select * from wages where id ='"
						+ idTxt.getText() + "' or name='"
						+ nameTxt.getText() + "'or wageDate='" + dateTxt.getText() + "'";
			}
								conn = new sqlconn();
					try{
					// System.out.println(nameTxt.getText());
					rs1 = conn.getRs(sql1);
					System.out.println(rs1);
					int count = 0;
					// 得到总记录数
					if (rs1.next()) {
						count = rs1.getInt(1);
					}
					rs1.close();
					
					Object[][] info = new Object[count][7];
					count = 0;
					 rs2 = conn.getRs(sql2);

					while (rs2.next()) {
						info[count][0] = rs2.getString("id");
						info[count][1] = rs2.getString("name");
						info[count][2] = rs2.getString("baswage");
						info[count][3] = rs2.getString("bonus");
						info[count][4] = rs2.getString("deductwage");
						// info[count][5] = rs2.getString("departmen");
						info[count][5] = rs2.getString("factwage");
						info[count][6] = rs2.getString("wageDate");
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

					}

					String[] title = { "员工号", "员工姓名", "基本工资", "奖金", "扣款",
							"实际工资", "工资日期" };
					// 创建JTable
					this.tabDemo = new JTable(info, title) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					// tabDemo.setPreferredSize(new Dimension(800,900));
					// 显示表头
					this.jth = this.tabDemo.getTableHeader();
					// 将JTable加入到带滚动条的面板中
					this.scpDemo.getViewport().add(tabDemo);
				} catch (Exception sqle) {
					sqle.printStackTrace();
				}
			
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		//dateTxt.setText(dateChoice.getSelectedItem());
		//System.out.println(yearChoice.getSelectedItem());
		dateTxt.setText(yearChoice.getSelectedItem() +"-"+ monthChoice.getSelectedItem());
		
	}
}