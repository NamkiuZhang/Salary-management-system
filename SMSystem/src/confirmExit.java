import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class confirmExit extends JFrame implements ActionListener{
	JLabel desJLab=new JLabel("�Ƿ�ȷ���˳�������");  
  JButton okJBut=new JButton("ȷ��");
  JButton backJBut=new JButton("����");
    
  public confirmExit(){ 
  	
  	super("����");
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
		if(ae.getActionCommand().equals("ȷ��")){
     	dispose();
     	System.exit(0);
		}
    if(ae.getActionCommand().equals("����"))
      dispose();  
	} 
}