package dataStructure;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MainFrame extends JFrame{
	MainPanel inputpanel=new MainPanel();
	ShowPanel showpanel=new ShowPanel();
	ListPanel listpanel=new ListPanel();
	DistancePanel distancepanel=new DistancePanel();
	JMenuBar menuBar = new JMenuBar();
	JMenuItem menu = new JMenuItem("绘图");
	JMenuItem menu_1 = new JMenuItem("邻接链表");
	JMenuItem menu_2 = new JMenuItem("最小生成树");
	JMenuItem menu_3 = new JMenuItem("最短路径");
	JMenuItem menu_4 = new JMenuItem("退出");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MainFrame(){
		setResizable(false);
		setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		JPanel back=new JPanel();
		inputpanel.setBounds(0, 24, 500, 541);
		inputpanel.setBorder(BorderFactory.createEtchedBorder());
		inputpanel.setVisible(false);
	    listpanel.setBounds(500, 24, 500, 541);
	    listpanel.setBorder(BorderFactory.createEtchedBorder());
	    listpanel.setVisible(false);
	    showpanel.setBounds(500, 24, 500, 541);
	    showpanel.setBorder(BorderFactory.createEtchedBorder());
	    showpanel.setVisible(false);
	    distancepanel.setBounds(500, 24, 500, 541);
	    distancepanel.setVisible(false);
	    distancepanel.setBorder(BorderFactory.createEtchedBorder());
	    
	    MyListener listener = new MyListener();
		back.setLayout(null);
		
		menuBar.setBounds(0, 0, 994, 24);
		menuBar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		back.add(menuBar);
		
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu);
		menu.addActionListener(listener);
		
		menu_1.setEnabled(false);
		menu_1.setHorizontalAlignment(SwingConstants.CENTER);
		menu_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu_1);
		menu_1.addActionListener(listener);		
		
		menu_2.setEnabled(false);
		menu_2.setHorizontalAlignment(SwingConstants.CENTER);
		menu_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu_2);
		menu_2.addActionListener(listener);		
		
		menu_3.setEnabled(false);
		menu_3.setHorizontalAlignment(SwingConstants.CENTER);
		menu_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu_3);
		menu_3.addActionListener(listener);	
		
		menu_4.setHorizontalAlignment(SwingConstants.CENTER);
		menu_4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		menuBar.add(menu_4);
		menu_4.addActionListener(listener);
		
		back.add(inputpanel);
		back.add(listpanel);
		back.add(showpanel);
		back.add(distancepanel);
		getContentPane().add(back);
		
		setTitle("\u7B97\u6CD5\u5B9E\u73B0\u98984(\u6700\u77ED\u8DEF\u5F84&\u6700\u5C0F\u751F\u6210\u6811)-1552494_\u8212\u777F\u742A");
		setSize(1000,600);
		setVisible(true);
	}
	
	class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="绘图"){
				inputpanel.setVisible(true);
				menu_1.setEnabled(true);
				menu_2.setEnabled(true);
				menu_3.setEnabled(true);
				listpanel.setVisible(false);
				showpanel.setVisible(false);
				distancepanel.setVisible(false);
			}
			else if(e.getActionCommand()=="邻接链表"){
				listpanel.setVisible(true);
				showpanel.setVisible(false);
				distancepanel.setVisible(false);
				listpanel.repaint();
			}
			else if(e.getActionCommand()=="最小生成树"){
				showpanel.setVisible(true);
				listpanel.setVisible(false);
				distancepanel.setVisible(false);
				showpanel.repaint();
			}
			else if(e.getActionCommand()=="最短路径"){
				listpanel.setVisible(false);
				showpanel.setVisible(false);
				distancepanel.setVisible(true);
				distancepanel.repaint();
			}
			else if(e.getActionCommand()=="退出")
				System.exit(0); 
		}

	}
}



