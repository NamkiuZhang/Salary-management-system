import java.awt.event.*;
import javax.swing.JEditorPane; 
import javax.swing.JFrame; 
import javax.swing.event.HyperlinkEvent; 
import javax.swing.event.HyperlinkListener; 

class about extends JFrame{ 

	public about(){ 
		setTitle("���ڹ��ʹ���ϵͳ");
		this.setSize(500,220);
		//this.setBackground(Color.LIGHT_GRAY);
		this.setResizable(false);
    this.setLocationRelativeTo(this.getParent());
    
		JEditorPane jEditorPane = new JEditorPane(); 
		jEditorPane.setEditable(false); 
		jEditorPane.setContentType("text/html"); 
		jEditorPane.setText("<html><body bgcolor=white align=center><br><br><br><font>���ʹ���ϵͳ 1.0 ��,��ʲô����,�ٶ�һ��,���֪��</font><br><br><br>��Ȩ����(C) ������ѧ���ѧԺ</body></html>"); 
		/*jEditorPane.addHyperlinkListener(new HyperlinkListener(){
			public void hyperlinkUpdate(HyperlinkEvent e){
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
					try{
						String command = "explorer.exe " + e.getURL().toString(); 
						Runtime.getRuntime().exec(command); 
					}
					catch (Exception ex){
					ex.printStackTrace(); 
					System.err.println("connection error"); 
					} 
				} 
			} 
		});*/
		this.getContentPane().add(jEditorPane); 
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter(){
			public void closingWindow(WindowEvent e){
				dispose();
				//setVisible(false);
			}
		});
		
		this.setVisible(true);
	}
}