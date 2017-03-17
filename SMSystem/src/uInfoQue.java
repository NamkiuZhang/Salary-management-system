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

	// ���ɿؼ�
	Label idLab = new Label("Ա����");
	Label nameLab = new Label("Ա����");
	Label dtmtLab = new Label("����");
	
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	TextField dtmtTxt = new TextField();
	
	Choice userChoice = new Choice();
	Choice dtmtChoice = new Choice();
	
	Button queryBut = new Button("��ѯ");
	Button exitBut = new Button("�˳�");

	public uInfoQue() {
		super("Ա����Ϣ��ѯ");
		setSize(600, 500);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		// ���ÿؼ���С
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

		// ���ÿؼ�λ��
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

		// �Ѹ��ؼ���ӵ�Frame��
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
		this.btnShow = new JButton("��ʾ����Ա����Ϣ");
		this.btnShow.setBounds(180, 145, 200, 30);

		

		add(this.scpDemo);
		add(this.btnShow);
		this.setVisible(true);

		// ��������ť��ע�������
		btnShow.addActionListener(this);
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);
		userChoice.addItemListener(this);
        dtmtChoice.addItemListener(this);
		// ��ʼ��userChoice�б�
		initChoice();
		initDtmtChoice();
		setVisible(true);

		// ����"�ر�"��ť��Ӵ���������
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
		if (ae.getActionCommand().equals("�˳�"))
			dispose();
		if (ae.getActionCommand().equals("��ѯ")) {
			//δ�����ѯ����
			if (idTxt.getText().equals("") && nameTxt.getText().equals("") && dtmtTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "��������Ҫ��ѯ������!", "����", 0);
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
				// �õ��ܼ�¼��
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

				String[] title = { "Ա����", "Ա������", "�Ա�", "����", "ְ��", "����",
						"ѧ��", "����" };
				// ����JTable
				this.tabDemo = new JTable(info, title){
					public boolean isCellEditable(int row,int column){
						return false;
					}
					
				};
				// ��ʾ��ͷ
				this.jth = this.tabDemo.getTableHeader();
				// ��JTable���뵽���������������
				this.scpDemo.getViewport().add(tabDemo);
			} catch (Exception sqle) {
				// sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "����:" + sqle.getMessage(),
						"����", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (ae.getActionCommand().equals("��ʾ����Ա����Ϣ")) {
			try {
				String sql1 = "select count(*) from employees";
				String sql2 = "select * from employees";

				conn = new sqlconn();

				ResultSet rs1 = conn.getRs(sql1);
				int count = 0;
				// �õ��ܼ�¼��
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

				String[] title = { "Ա����", "Ա������", "�Ա�", "����", "ְ��", "����",
						"ѧ��", "����" };
				// ����JTable
				this.tabDemo = new JTable(info, title){
					public boolean isCellEditable(int row,int column){
						return false;
					}
					
				};
				//�����ǹ����ķ�Χ��Ԥ������dimension�Ĵ�С
				tabDemo.setPreferredSize(new Dimension(800,300));
				// ��ʾ��ͷ
				this.jth = this.tabDemo.getTableHeader();
				// ��JTable���뵽���������������
				this.scpDemo.getViewport().add(tabDemo);
			} catch (Exception sqle) {
				// sqle.printStackTrace();
				JOptionPane.showMessageDialog(null, "����:" + sqle.getMessage(),
						"����", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
		dtmtTxt.setText(dtmtChoice.getSelectedItem());
	}
}
