import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

class addDtmt extends Frame implements ActionListener, ItemListener {
	// ���ݿ����
	String sql = null;
	ResultSet rs = null;
	sqlconn conn = null;

	// �����ؼ�
	Label idLab = new Label("���ű��");
	Label nameLab = new Label("��������");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	Choice idChoice = new Choice();
	Button addBut = new Button("����");
	Button delBut = new Button("ɾ��");
	Button clrBut = new Button("���");
	Button exitBut = new Button("�˳�");

	public addDtmt() {
		super("�༭������Ϣ");
		setSize(380, 250);
		setResizable(false);
		this.setLocationRelativeTo(this.getParent());
		setLayout(null);

		// ���ø��ؼ��Ĵ�С
		idLab.setSize(70, 20);
		nameLab.setSize(70, 20);
		idTxt.setSize(170, 20);
		nameTxt.setSize(170, 20);
		idChoice.setSize(170, 20);
		addBut.setSize(50, 20);
		delBut.setSize(50, 20);
		clrBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// ���ø��ؼ���λ��
		idLab.setLocation(80, 50);
		idTxt.setLocation(160, 50);
		nameLab.setLocation(80, 100);
		nameTxt.setLocation(160, 100);
		idChoice.setLocation(160, 150);
		addBut.setLocation(60, 200);
		delBut.setLocation(130, 200);
		clrBut.setLocation(200, 200);
		exitBut.setLocation(270, 200);

		// �Ѹ��ؼ���ӵ�Frame��
		add(idLab);
		add(nameLab);
		add(idTxt);
		add(nameTxt);
		add(idChoice);
		add(addBut);
		add(delBut);
		add(clrBut);
		add(exitBut);

		// ��������ť��ע�������
		addBut.addActionListener(this);
		delBut.addActionListener(this);
		clrBut.addActionListener(this);
		exitBut.addActionListener(this);

		// ��ʼ��userChoice�б�
		initChoice();
		// �б����ItemListener������
		idChoice.addItemListener(this);

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
		if (ee.getActionCommand().equals("�˳�"))
			dispose();
		if (ee.getActionCommand().equals("���")) {
			idTxt.setText("");
			nameTxt.setText("");
		}
		if (ee.getActionCommand().equals("����")) {
			if(idTxt.getText().equals("") || nameTxt.getText().equals("")){
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "�������������!", "����", 0);
			}else{
			sql = "select *  from department where dtmt_id='" + idTxt.getText()
					+ "' or dtmt_name = '" + nameTxt.getText() + "'";
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
						sql = "insert into department values('"
								+ idTxt.getText() + "','" + nameTxt.getText()
								+ "')";
						conn = new sqlconn();
						conn.dbMod(sql);
						conn.dbClose(rs);
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "��ӳɹ�!", "��ʾ��Ϣ", 1);
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
		if (ee.getActionCommand().equals("ɾ��")) {
			JOptionPane jopask = new JOptionPane();
			if (jopask.showConfirmDialog(null, "��ȷ��Ҫɾ���˼�¼��?", "��ȷ��", 1) == 0) {

				sql = "select * from department where dtmt_id='"
						+ idTxt.getText() + "'";
				conn = new sqlconn();
				try {
					rs = conn.getRs(sql);
					if (!(rs.next())) {
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "û�иò���,ɾ��ʧ��!", "����", 1);
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
						jop.showMessageDialog(null, "�ò�����Ա�����޷�ɾ��!", "����", 1);
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
						jop.showMessageDialog(null, "ɾ���ɹ�!", "��ʾ��Ϣ", 1);
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

	// ���û��б���ѡ��id��ֵ��idTxt�ı���
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
			System.out.println("�б��������ݿ����!");
		}
	}
}