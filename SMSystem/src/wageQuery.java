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

	Label idLab = new Label("Ա����");
	Label nameLab = new Label("Ա����");
	Label wDateLab = new Label("��������");

	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	TextField dateTxt = new TextField();
	//Choice dateChoice = new Choice();
	Label yearLab=new Label("��");
	Label monthLab=new Label("��");
	
	Choice yearChoice=new Choice();
	Choice monthChoice=new Choice();

	Button queryBut = new Button("��ѯ");
	Button exitBut = new Button("�˳�");

	public wageQuery() {
		super("���ʲ�ѯ");
		setSize(600, 500);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		// ���ÿؼ���С
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

		// ���ÿؼ�λ��
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
		// �Ѹ��ؼ���ӵ�Frame��
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
		this.scpDemo.setBounds(50, 200, 500, 260);// �� �� �� ��
		// this.btnShow = new JButton("��ʾ������Ϣ");
		// this.btnShow.setBounds(70, 150, 250, 30);

		add(this.scpDemo);
		// add(this.btnShow);
		this.setVisible(true);

		// ��������ť��ע�������
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);
		//dateChoice.addItemListener(this);
		yearChoice.addItemListener(this);
		monthChoice.addItemListener(this);

		//initDateChoice();
		initYChoice();
		initMChoice();
		setVisible(true);

		// ����"�ر�"��ť��Ӵ���������
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
		if (ae.getActionCommand().equals("�˳�"))
			dispose();
		if (ae.getActionCommand().equals("��ѯ")) {
			// ��Ϊ��
			if (idTxt.getText().equals("") && nameTxt.getText().equals("")&&dateTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "�������ѯ����!", "����", 0);
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
					// �õ��ܼ�¼��
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

					String[] title = { "Ա����", "Ա������", "��������", "����", "�ۿ�",
							"ʵ�ʹ���", "��������" };
					// ����JTable
					this.tabDemo = new JTable(info, title) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					// tabDemo.setPreferredSize(new Dimension(800,900));
					// ��ʾ��ͷ
					this.jth = this.tabDemo.getTableHeader();
					// ��JTable���뵽���������������
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