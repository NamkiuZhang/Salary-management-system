import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class confirmExit extends JFrame implements ActionListener{
	JLabel desJLab=new JLabel("是否确定退出本程序？");  
  JButton okJBut=new JButton("确认");
  JButton backJBut=new JButton("返回");
    
  public confirmExit(){ 
  	
  	super("结束");
    setSize(300,180);
    setBackground(Color.LIGHT_GRAY);
    this.setLocationRelativeTo(this.getParent());
    setResizable(false);
    Container cont=this.getContentPane();
    cont.setLayout(null);
    
    desJLab.setBounds(80,20,200,30);
    okJBut.setBounds(50,80,80,30);
    backJBut.setBounds(150,80,80,30);
    
    cont.add(desJLab);
    cont.add(okJBut);
    cont.add(backJBut);
    okJBut.addActionListener(this);
    backJBut.addActionListener(this);

    setVisible(true);

   }
 

	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("确认")){
     	dispose();
     	System.exit(0);
		}
    if(ae.getActionCommand().equals("返回"))
      dispose();  
	} 
}