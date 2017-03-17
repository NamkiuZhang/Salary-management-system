import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
//import java.awt.Window;
//import java.util.*;

class userInfo extends Frame implements ActionListener, ItemListener {
	// ���ݿ����
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// �����ؼ�
	Label nameLab = new Label("��   ��");
	Label pwdLab = new Label("��   ��");
	TextField nameTxt = new TextField();
	TextField pwdTxt = new TextField();
	Choice userChoice = new Choice();
	Button addBut = new Button("����");
	Button delBut = new Button("ɾ��");
	Button exitBut = new Button("�˳�");

	public userInfo() {
		super("ϵͳ�û�����");
		setSize(410, 250);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// ���ø��ؼ��Ĵ�С
		nameLab.setSize(50, 20);
		pwdLab.setSize(50, 20);
		nameTxt.setSize(170, 20);
		pwdTxt.setSize(170, 20);
		userChoice.setSize(170, 20);
		addBut.setSize(55, 20);
		delBut.setSize(55, 20);
		exitBut.setSize(55, 20);

		// ���ø��ؼ���λ��
		nameLab.setLocation(80, 50);
		nameTxt.setLocation(160, 50);
		pwdLab.setLocation(80, 100);
		pwdTxt.setLocation(160, 100);
		userChoice.setLocation(160, 150);
		addBut.setLocation(90, 200);
		delBut.setLocation(180, 200);
		exitBut.setLocation(270, 200);

		// �Ѹ��ؼ���ӵ�Frame��
		add(nameLab);
		add(pwdLab);
		add(nameTxt);
		add(pwdTxt);
		add(userChoice);
		add(addBut);
		add(delBut);
		add(exitBut);

		// ���ĸ���ť��ע�������
		addBut.addActionListener(this);
		delBut.addActionListener(this);
		exitBut.addActionListener(this);

		// ��ʼ��userChoice�б�
		initChoice();
		// �б����ItemListener������
		userChoice.addItemListener(this);

		pwdTxt.setEchoChar('*');
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
		if (ee.getActionCommand().equals("����")) {
			// ���ж�����û����Ƿ�Ϊ�������Ƿ�Ϊ��
			if (nameTxt.getText().equals("") || pwdTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "�û��������벻��Ϊ��", "����", 1);
			} else {
				sql = "select *  from users where name='" + nameTxt.getText()
						+ "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (rs.next()) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "���û��Ѿ�����!", "����", 1);
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
							jop.showMessageDialog(null, "��ӳɹ�!", "��ʾ��Ϣ", 1);
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
		if (ee.getActionCommand().equals("ɾ��")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "��ȷ��Ҫɾ���˼�¼��?", "��ȷ��", 1) == 0) {

				sql = "select * from users where name='" + nameTxt.getText()
						+ "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "�û�������,ɾ��ʧ��!", "����", 1);
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
					jop.showMessageDialog(null, "ɾ���ɹ�!", "��ʾ��Ϣ", 1);
				} catch (Exception se) {
				}
				conn = null;
				userChoice.remove(userChoice.getSelectedIndex());
				nameTxt.setText("");
				pwdTxt.setText("");
			}
		}
	}

	// ���û��б�ĵ�һ�û�����ֵ��nameTxt�ı���
	public void itemStateChanged(ItemEvent ie) {
		nameTxt.setText(userChoice.getSelectedItem());
	}
}