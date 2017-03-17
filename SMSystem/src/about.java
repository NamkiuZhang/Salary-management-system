import java.awt.event.*;
import javax.swing.JEditorPane; 
import javax.swing.JFrame; 
import javax.swing.event.HyperlinkEvent; 
import javax.swing.event.HyperlinkListener; 

class about extends JFrame{ 

	public about(){ 
		setTitle("关于工资管理系统");
		this.setSize(500,220);
		//this.setBackground(Color.LIGHT_GRAY);
		this.setResizable(false);
    this.setLocationRelativeTo(this.getParent());
    
		JEditorPane jEditorPane = new JEditorPane(); 
		jEditorPane.setEditable(false); 
		jEditorPane.setContentType("text/html"); 
		jEditorPane.setText("<html><body bgcolor=white align=center><br><br><br><font>工资管理系统 1.0 版,有什么问题,百度一下,你就知道</font><br><br><br>版权所有(C) 东北大学软件学院</body></html>"); 
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