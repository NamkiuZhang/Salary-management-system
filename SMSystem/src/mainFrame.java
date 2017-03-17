import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

class mainFrame extends Frame implements ActionListener{
	
	Image img;
	
	//菜单条式菜单
	MenuBar mb=new MenuBar();
	Menu usersM=new Menu("系统管理");
	Menu employeeM=new Menu("人员管理");
	Menu dtmtM=new Menu("部门管理");
	Menu wageM=new Menu("工资管理");
	Menu helpM=new Menu("帮助");
	
	MenuItem uInfoMi =new MenuItem("用户管理");
	MenuItem pwdChange=new MenuItem("修改密码");
	MenuItem logoutMi=new MenuItem("注销");
	MenuItem emQueryMi=new MenuItem("员工信息查询");
	MenuItem addEmMi=new MenuItem("添加员工");
	MenuItem modifyEmMi = new MenuItem("修改员工信息");
	MenuItem wQueryMi=new MenuItem("工资查询");
	MenuItem addWageMi=new MenuItem("添加工资信息");
	MenuItem modifyWageMi = new MenuItem("修改工资信息");
	
	MenuItem dtmtInfoMi=new MenuItem("部门信息");
	MenuItem addDtmtMi=new MenuItem("编辑部门信息");
	MenuItem aboutMi=new MenuItem("关于...");
	
	
	//弹出式菜单
	PopupMenu popM=new PopupMenu();
	//MenuItem aveWageMi=new MenuItem("平均工资");
	
	public mainFrame(){
		setTitle("欢迎使用工资管理系统...");
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
			//鼠标按键松开事件弹出菜单
			public void mouseReleased(MouseEvent me){
				//检查鼠标事件是否由弹出菜单引发
				if(me.isPopupTrigger())
					//将弹出菜单显示在用户鼠标单击的位置
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
		
		//添加窗口"关闭"按钮的关闭功能
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
			if(ae.getActionCommand().equals("用户管理")){
				userInfo user1=new userInfo();
				user1.show();
			}
			if(ae.getActionCommand().equals("修改密码")){
	            changePwd chaPwd = new changePwd();
	            chaPwd.show();
			}
			if(ae.getActionCommand().equals("注销")){
				logFrame logf1=new logFrame();
				dispose();
			}
			if(ae.getActionCommand().equals("员工信息查询")){
				uInfoQue uiq=new uInfoQue();
				uiq.show();
			}
			if(ae.getActionCommand().equals("添加员工")){
				addEmInfo aei=new addEmInfo();
				aei.show();
			}
			if(ae.getActionCommand().equals("修改员工信息")){
				modifyEmInfo mei=new modifyEmInfo();
				mei.show();
			}
			if(ae.getActionCommand().equals("工资查询")){
				wageQuery wq=new wageQuery();
				wq.show();
			}
			if(ae.getActionCommand().equals("添加工资信息")){
				addWage aw=new addWage();
				aw.show();
			}
			if(ae.getActionCommand().equals("修改工资信息")){
				modifyWage mw=new modifyWage();
				mw.show();
			}
			if(ae.getActionCommand().equals("部门信息")){
				dtmtInfoQue diq=new dtmtInfoQue();
				diq.show();
			}
			if(ae.getActionCommand().equals("编辑部门信息")){
				addDtmt ad=new addDtmt();
				ad.show();
			}
			if(ae.getActionCommand().equals("关于...")){
				about ab=new about();
				ab.show();
			}
			/*
			if(ae.getActionCommand().equals("平均工资")){
				aWage aw=new aWage();
				aw.show();			
			}
			*/
		}
}