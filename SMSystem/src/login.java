import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.geom.*;

class logFrame extends Frame{
	String sql=null;
	ResultSet rs=null;
	sqlconn dbconn=new sqlconn();
	//Label nameLab=new Label(" ��  ��: ",Label.CENTER);
	//Label pwdLab=new Label(" ��  ��: ",Label.CENTER);
	//Label desLab=new Label("���ʹ���ϵͳ");
	TextField nameTxt=new TextField();
	TextField pwdTxt=new TextField();
	Button logBut=new Button("��¼");
	Button exitBut=new Button("�˳�");
	Image img;
	
	
	public logFrame(){
		//System.out.println("slkjdla");
		setTitle("��ӭʹ�ù��ʹ���ϵͳ");
		setLayout(null);
		setResizable(false);
		setSize(500,350);
		setBackground(Color.LIGHT_GRAY);
		
		//System.out.println("slkjdla");
		Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();
   	Dimension frm=this.getSize();
   	setLocation((scr.width-frm.width)/2,(scr.height-frm.height)/2-18);
   	
   	img=getToolkit().getImage(getClass().getResource("/piclogin1.jpg")); 
		
		//nameLab.setBounds(145,185,50,30);
		//nameLab.FontSize=30;
		//pwdLab.setBounds(145,225,50,30);
		//nameLab.setOpaque(true);
		nameTxt.setBounds(215,177,120,30);
		pwdTxt.setBounds(215,217,120,30);
		logBut.setBounds(180,270,60,20);
		exitBut.setBounds(255,270,60,20);
		//desLab.setBounds(160,50,80,30);
		//add(nameLab);
		//add(pwdLab);
		add(nameTxt);
		add(pwdTxt);
		add(logBut);
		add(exitBut);
		//add(desLab);
		
	  //�����ı�����ʾ"*"
		pwdTxt.setEchoChar('*');
		setVisible(true);
		
		//ע��"��½"��ť������
		logBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sql="select * from users where name='"+nameTxt.getText()+"' and password='"+pwdTxt.getText()+"'";
				try{
					rs=dbconn.getRs(sql);
					if(rs.next())
					{
						mainFrame mf=new mainFrame();
						dispose();
						//���ù��ʹ���ϵͳ������
						mf.show();						
					}
					else{
						JOptionPane jop=new JOptionPane();
						jop.showMessageDialog(null,"�û��������벻��,����������!","������ʾ",1);
					}
				}
				catch(Exception ee){}
			}
		});
		
		//ע���ı��������
		//�������"�س�"��,����û�����
		pwdTxt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sql="select * from users where name='"+nameTxt.getText()+"' and password='"+pwdTxt.getText()+"'";
				try{
					rs=dbconn.getRs(sql);
					if(rs.next())
					{
						mainFrame mf=new mainFrame();
						dispose();
						mf.show();						
					}
					else{
						JOptionPane jop=new JOptionPane();
						jop.showMessageDialog(null,"�û��������벻��,����������!","������ʾ",1);
					}
				}
				catch(Exception ee){}
			}
		});
		//��"�˳�"��ť�˳�ϵͳ
		exitBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//dispose();
				System.exit(0);
			}
		});
		
		//��Ӵ���"�ر�"��ť�Ĺرչ���
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
			}
		});
		
	}
	
	public void paint(Graphics g){
  	g.setClip(new Rectangle2D.Float(0,0,500,350));
    g.drawImage(img,0,0,this);
 	}
}

public class login{
	public static void main(String[] args){
		
		logFrame logf1=new logFrame();	
		//logf1.setVisible(true);
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //System.out.println(df.format(new Date(1)));
	}
}