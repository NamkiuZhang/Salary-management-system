import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class changePwd extends Frame implements ActionListener, ItemListener {
	// ���ݿ����
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// �����ؼ�
	Label nameLab = new Label("��   ��");
	Label pwdLab1 = new Label("�� �� ��");
	Label pwdLab2 = new Label("�� �� ��");
	Label pwdLab3 = new Label("ȷ��������");
	TextField nameTxt = new TextField();
	TextField pwdTxt1 = new TextField();
	TextField pwdtxt2 = new TextField();
	TextField pwdTxt3 = new TextField();
	Choice userChoice = new Choice();
	Button confirmBut = new Button("ȷ��");
	// Button addBut = new Button("����");
	// Button modBut = new Button("�޸�");
	// Button delBut = new Button("ɾ��");
	Button exitBut = new Button("�˳�");

	public changePwd() {
		super("ϵͳ�û�����");
		setSize(390, 400);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// ���ø��ؼ��Ĵ�С
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

		// ���ø��ؼ���λ��
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

		// �Ѹ��ؼ���ӵ�Frame��
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

		// �ڰ�ť��ע�������
		// addBut.addActionListener(this);
		// modBut.addActionListener(this);
		// delBut.addActionListener(this);
		confirmBut.addActionListener(this);
		exitBut.addActionListener(this);

		// ��ʼ��userChoice�б�
		initChoice();
		// �б����ItemListener������
		userChoice.addItemListener(this);

		pwdTxt1.setEchoChar('*');
		pwdtxt2.setEchoChar('*');
		pwdTxt3.setEchoChar('*');
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
		if (ee.getActionCommand().equals("�˳�"))
			dispose();
		if (ee.getActionCommand().equals("ȷ��")) {
			// ���ж�����û����Ƿ�Ϊ�������Ƿ�Ϊ��
			if (nameTxt.getText().equals("") || pwdTxt1.getText().equals("")
					|| pwdtxt2.getText().equals("")
					|| pwdTxt3.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "����Ϊ��", "����", 1);
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
						jop.showMessageDialog(null, "���û�������!", "����", 1);
						conn.dbClose(rs);
						sql = "";
						conn = null;
						rs = null;
						return;
					}// ԭ�������
					else if (!(rs1.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "���������", "������ʾ", 1);
					}// ������������벻һ��
					else if (!(pwdtxt2.getText().equals(pwdTxt3.getText()))) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "�����벻һ�£�����������", "����", 1);
					}else{
				     // try {
				    	  sql = "update users set password='" + pwdtxt2.getText()
				    			  + "'where name='" + nameTxt.getText() + "'";
				    	  conn = new sqlconn();
				    	  conn.dbMod(sql);
				    	  conn.dbClose(rs);
				    	  JOptionPane jop = new JOptionPane();
				    	  jop.showMessageDialog(null, "�޸ĳɹ�!", "��ʾ��Ϣ", 1);
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

	// ���û��б�ĵ�һ�û�����ֵ��nameTxt�ı���
	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
	}
}
