import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

class mainFrame extends Frame implements ActionListener{
	
	Image img;
	
	//�˵���ʽ�˵�
	MenuBar mb=new MenuBar();
	Menu usersM=new Menu("ϵͳ����");
	Menu employeeM=new Menu("��Ա����");
	Menu dtmtM=new Menu("���Ź���");
	Menu wageM=new Menu("���ʹ���");
	Menu helpM=new Menu("����");
	
	MenuItem uInfoMi =new MenuItem("�û�����");
	MenuItem pwdChange=new MenuItem("�޸�����");
	MenuItem logoutMi=new MenuItem("ע��");
	MenuItem emQueryMi=new MenuItem("Ա����Ϣ��ѯ");
	MenuItem addEmMi=new MenuItem("���Ա��");
	MenuItem modifyEmMi = new MenuItem("�޸�Ա����Ϣ");
	MenuItem wQueryMi=new MenuItem("���ʲ�ѯ");
	MenuItem addWageMi=new MenuItem("��ӹ�����Ϣ");
	MenuItem modifyWageMi = new MenuItem("�޸Ĺ�����Ϣ");
	
	MenuItem dtmtInfoMi=new MenuItem("������Ϣ");
	MenuItem addDtmtMi=new MenuItem("�༭������Ϣ");
	MenuItem aboutMi=new MenuItem("����...");
	
	
	//����ʽ�˵�
	PopupMenu popM=new PopupMenu();
	//MenuItem aveWageMi=new MenuItem("ƽ������");
	
	public mainFrame(){
		setTitle("��ӭʹ�ù��ʹ���ϵͳ...");
		setSize(800,507);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		img=getToolkit().getImage(getClass().getResource("/bg1.png")); 
		
		Dimension scr=Toolkit.getDefaultToolkit().getScreenSize();
  	Dimension frm=this.getSize();
  	setLocation((int)(scr.getWidth()-frm.getWidth())/2,
             (int)(scr.getHeight()-frm.getHeight())/2-40);
		
		mb.add(usersM);
		mb.add(employeeM);
		mb.add(dtmtM);
		mb.add(wageM);
		mb.add(helpM);
		usersM.add(uInfoMi);
		usersM.add(pwdChange);
		usersM.add(logoutMi);
		employeeM.add(emQueryMi);
		employeeM.add(addEmMi);
		employeeM.add(modifyEmMi);
		wageM.add(wQueryMi);
		wageM.add(addWageMi);
		wageM.add(modifyWageMi);
		
		dtmtM.add(dtmtInfoMi);
		dtmtM.add(addDtmtMi);
		helpM.add(aboutMi);
		//helpM.add(logoutMi);
		
		//popM.add(aveWageMi);
		//aveWageMi.addActionListener(this);
		this.add(popM);
		this.addMouseListener(new MouseAdapter(){
			//��갴���ɿ��¼������˵�
			public void mouseReleased(MouseEvent me){
				//�������¼��Ƿ��ɵ����˵�����
				if(me.isPopupTrigger())
					//�������˵���ʾ���û���굥����λ��
					popM.show((Component)me.getSource(),me.getX(),me.getY());
			}
		});
		
		usersM.addActionListener(this);
		employeeM.addActionListener(this);
		wageM.addActionListener(this);
		dtmtM.addActionListener(this);
		helpM.addActionListener(this);
		
		this.setMenuBar(mb);
		setVisible(true);
		
		//��Ӵ���"�ر�"��ť�Ĺرչ���
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				confirmExit conE =new confirmExit();
			}
		});
	}
	
	public void paint(Graphics g){
  	g.setClip(new Rectangle2D.Float(0,0,800,600));
    g.drawImage(img,0,0,this);
 	}
	
	public void actionPerformed(ActionEvent ae){
			if(ae.getActionCommand().equals("�û�����")){
				userInfo user1=new userInfo();
				user1.show();
			}
			if(ae.getActionCommand().equals("�޸�����")){
	            changePwd chaPwd = new changePwd();
	            chaPwd.show();
			}
			if(ae.getActionCommand().equals("ע��")){
				logFrame logf1=new logFrame();
				dispose();
			}
			if(ae.getActionCommand().equals("Ա����Ϣ��ѯ")){
				uInfoQue uiq=new uInfoQue();
				uiq.show();
			}
			if(ae.getActionCommand().equals("���Ա��")){
				addEmInfo aei=new addEmInfo();
				aei.show();
			}
			if(ae.getActionCommand().equals("�޸�Ա����Ϣ")){
				modifyEmInfo mei=new modifyEmInfo();
				mei.show();
			}
			if(ae.getActionCommand().equals("���ʲ�ѯ")){
				wageQuery wq=new wageQuery();
				wq.show();
			}
			if(ae.getActionCommand().equals("��ӹ�����Ϣ")){
				addWage aw=new addWage();
				aw.show();
			}
			if(ae.getActionCommand().equals("�޸Ĺ�����Ϣ")){
				modifyWage mw=new modifyWage();
				mw.show();
			}
			if(ae.getActionCommand().equals("������Ϣ")){
				dtmtInfoQue diq=new dtmtInfoQue();
				diq.show();
			}
			if(ae.getActionCommand().equals("�༭������Ϣ")){
				addDtmt ad=new addDtmt();
				ad.show();
			}
			if(ae.getActionCommand().equals("����...")){
				about ab=new about();
				ab.show();
			}
			/*
			if(ae.getActionCommand().equals("ƽ������")){
				aWage aw=new aWage();
				aw.show();			
			}
			*/
		}
}