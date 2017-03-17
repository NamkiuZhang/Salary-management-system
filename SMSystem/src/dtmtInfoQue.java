import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.table.JTableHeader;

//import com.sun.istack.internal.logging.Logger;

class dtmtInfoQue extends Frame implements ActionListener {
	String sql;
	ResultSet rs = null;
	sqlconn conn = null;
	
	private JScrollPane scpDemo;
	private JTableHeader jth;
	private JTable tabDemo;
	private JButton btnShow;

	//Label tipsLab = new Label("��: W001,W002");
	Label idLab = new Label("���ű��");
	Label nameLab = new Label("��������");
	TextField idTxt = new TextField();
	TextField nameTxt = new TextField();
	Button queryBut = new Button("��ѯ");
	Button exitBut = new Button("�˳�");

	public dtmtInfoQue() {
		super("������Ϣ��ѯ");
		setSize(400, 570);
		setResizable(false);
		setLayout(null);

		this.setLocationRelativeTo(this.getParent());

		
		
		// ���ÿؼ���С
		//tipsLab.setSize(150, 20);
		idLab.setSize(70, 20);
		nameLab.setSize(70, 20);
		idTxt.setSize(130, 20);
		nameTxt.setSize(130, 20);
		queryBut.setSize(50, 20);
		exitBut.setSize(50, 20);

		// ���ÿؼ�λ��
		//tipsLab.setLocation(160, 110);
		idLab.setLocation(90, 80);
		nameLab.setLocation(90, 140);
		idTxt.setLocation(160, 80);
		nameTxt.setLocation(160, 140);
		queryBut.setLocation(100, 170);
		exitBut.setLocation(210, 170);
		
		this.scpDemo = new JScrollPane();
		this.scpDemo.setBounds(70,240,250,270);
		this.btnShow = new JButton("��ʾ���в�����Ϣ");
		this.btnShow.setBounds(70, 210, 250, 30);
		
		this.btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				btnShow_ActionPerformed(ae);
		    }
		});
		add(this.scpDemo);
		add(this.btnShow);
		this.setVisible(true);

		// �Ѹ��ؼ���ӵ�Frame��
		//add(tipsLab);
		add(idLab);
		add(nameLab);
		add(idTxt);
		add(nameTxt);
		add(queryBut);
		add(exitBut);
		
      
		// ��������ť��ע�������
		queryBut.addActionListener(this);
		exitBut.addActionListener(this);

		setVisible(true);
		
		

		// ����"�ر�"��ť��Ӵ���������
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("�˳�"))
			dispose();
		if (ae.getActionCommand().equals("��ѯ")) {
			if (idTxt.getText().equals("") && nameTxt.getText().equals("")) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "�������ѯ����!", "����", 0);
				dispose();
				dtmtInfoQue diq = new dtmtInfoQue();
			}else{
			sql = "select * from department where dtmt_id='" + idTxt.getText()
					+ "' or dtmt_name= '" + nameTxt.getText()
							+ "'";
			conn = new sqlconn();
			try {
				rs = conn.getRs(sql);
				if (rs.next()) {
					idTxt.setText(rs.getString("dtmt_id"));
					nameTxt.setText(rs.getString("dtmt_name"));
					conn.dbClose(rs);
					conn = null;
					rs = null;
				} else {
					JOptionPane jop = new JOptionPane();
					jop.showMessageDialog(null, "�ò��Ų�����!", "����", 0);
				}
			} catch (Exception e) {
			}
			}
		}
	}
	
	public void btnShow_ActionPerformed(ActionEvent ae){
		try{
		    String sql1 = "select count(*) from department";
		    String sql2 = "select * from department";
		    
		    conn = new sqlconn();
		 
		    ResultSet rs1 = conn.getRs(sql1);
		    int count = 0;
		    //�õ��ܼ�¼��
		    if(rs1.next()){
		    	count = rs1.getInt(1);
		    }
		    rs1.close();
		    
		    Object[][] info = new Object[count][3];
		    count=0;
		    ResultSet rs2 = conn.getRs(sql2);
		    
		    while(rs2.next()){
		    	info[count][0] = rs2.getString(1);
		    	info[count][1] = rs2.getString(2);
		    	String sql3 = "select count(*) from employees where department ='"+info[count][1]+"'";
		    	ResultSet rs3 = conn.getRs(sql3);
		    	int count3 = 0;
		    	if(rs3.next()){
			    	count3 = rs3.getInt(1);
			    }
		    	//rs3.close();
		    	System.out.println(info[count][1]);
		    	info[count][2] = rs3.getInt(1);
		    	count++;
		    }
		       
		    for(Object [] ob:info){
		    	System.out.println(ob[0]);
		    	System.out.println(ob[1]);
		    	System.out.println(ob[2]);

		    }
		    
		    String[] title = {"���ű��","��������","��������"};
		    //����JTable
		    this.tabDemo = new JTable(info,title){
				public boolean isCellEditable(int row,int column){
					return false;
				}
				
			};
		    //��ʾ��ͷ
		    this.jth = this.tabDemo.getTableHeader();
		    //��JTable���뵽���������������
		    this.scpDemo.getViewport().add(tabDemo);
		}catch(Exception sqle){
			//sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, "����:"+sqle.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
		}
	}
}