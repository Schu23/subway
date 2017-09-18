import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

public class Station extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//站点名称
	private int x,y;//站点坐标
	private int [] line;//换乘线路
	private int linenum;//换乘线路数
	final private int width =10;
	final private int height =10;
	public boolean pointing=false;
	Shape shape;
	Color bgColor = SystemColor.control;
	boolean draggable = false;
	
	Station(){
		super();
		}
	
	Station(String name,int x,int y,int linenum,int[] line) {
		super();
		super.setUI(new BasicButtonUI());
		super.setContentAreaFilled(false);
		Dimension size = this.getPreferredSize();
	    size.width = size.height = Math.max(size.width, size.height);
	    this.setPreferredSize(size); 
	    this.setContentAreaFilled(false); 
	    this.setBorderPainted(false); 
	    this.setFocusPainted(false); 
	    this.setBackground(bgColor);
	    this.setOpaque(false);
	    this.setVisible(false);
	    setStation(name,x,y,linenum,line);
		}
	
	protected void paintComponent(Graphics g) {
	    if (this.getModel().isArmed()) {
	      g.setColor(java.awt.SystemColor.controlHighlight);
	    } else {
	      g.setColor(this.bgColor); // 设置背景颜色
	    }
	    g.fillOval(0, 0, this.getSize().width - 1, this.getSize().height - 1); // 绘制圆形背景区域
	    g.setColor(java.awt.SystemColor.controlShadow); // 设置边框颜色
	    g.drawOval(0, 0, this.getSize().width - 1, this.getSize().height - 1); // 绘制边框线
	    super.paintComponent(g);
	}
	
	//车站显示时覆盖的区域
	public boolean contains(int x, int y) {
		if ((shape == null) || (!shape.getBounds().equals(this.getBounds()))) {
			this.shape = new Ellipse2D.Float(0, 0, this.getWidth(), this.getHeight());
	    	}
	    return shape.contains(x, y);
	}
	
	public String getstname(){
		return name; 
		}
	
	public int getstx(){
		return x+8;
		}
	
	public int getsty(){
		return y+50;
		}
	
	public int getstlinenum(){
		return linenum;
		}
	
	public int[] getstline(){
		return line;
		}
	
	public void setStation(String name,int x,int y,int linenum,int []line){
		this.name=name;
		this.line=line;
		this.x=x;
		this.y=y;
		super.setLocation(this.x, this.y-25);
		this.linenum=linenum;
		this.line=new int[linenum];
		this.line=line.clone();
		setColor();
		this.setBounds(this.x, this.y-25, width, height);
		}
	
	public void setPosition(int x,int y){
		this.x=x;
		this.y=y;
		super.setLocation(this.x, this.y-25);
		}
	
	public void setLine(int linenum,int []line){
		this.linenum=linenum;
		this.line=new int[linenum];
		this.line=line.clone();
		setColor();
		}
	
	private void setColor() {
		bgColor=Color.white;
		}

}

class Way {
	private Station station1,station2;//两端车站
	private int x1,x2,y1,y2;
	private int time;
	private int linenum;//涉及线路数目
	private int [] line;//涉及线路
	private Color [] color;//涉及线路颜色
	public boolean chosen=true;
	Way(Station s1,Station s2,int t){
		station1=s1;
		station2=s2;
		x1=s1.getstx();
		y1=s1.getsty();
		x2=s2.getstx();
		y2=s2.getsty();
		
		time=t;
		linenum=0;
		line = new int[s1.getstlinenum()];
		for(int i=0;i<s1.getstlinenum();i++){
			for(int j=0;j<s2.getstlinenum();j++){
				if(s1.getstline()[i]==s2.getstline()[j]){
					line[linenum]=s1.getstline()[i];
					linenum++;
					}
				}
			}
		setColor();
		}
	
	public int getwline(){
		return line[0];
		}
	
	public int gettime(){
		return time;
		}
	
	public String getwname1(){
		return station1.getstname();
		}
	
	public String getwname2(){
		return station2.getstname();
		}
	
	public void setLine(int xx1,int yy1){
		x1=xx1;
		y1=yy1;
		String name=station1.getstname();
		int linenum=station1.getstlinenum();
		int [] line=station1.getstline();
		station1=new Station(name,x1-8,y1-31,linenum,line);
		}
	
	public int getwx1(){
		return x1;
		}
	
	public int getwx2(){
		return x2;
		}
	
	public int getwy1(){
		return y1;
		}
	
	public int getwy2(){
		return y2;
		}
	
	public Station getwstation1(){
		return station1;
		}
	
	public Station getwstation2(){
		return station2;
		}
	
	public Color getwcolor(){
		return color[0];
		}
	
	//设置每条线路颜色
	public void setColor(){
		color=new Color[1];
		if(line[0]==1)
			color[0]=new Color(235,50,40);
		else if(line[0]==2)
			color[0]=new Color(55,185,85);
		else if(line[0]==3)
			color[0]=new Color(255,215,35);
		else if(line[0]==4)
			color[0]=new Color(50,1,120);
		else if(line[0]==5)
			color[0]=new Color(130,50,150);
		else if(line[0]==6)
			color[0]=new Color(200,5,120);
		else if(line[0]==7)
			color[0]=new Color(240,85,15);
		else if(line[0]==8)
			color[0]=new Color(0,140,195);
		else if(line[0]==9)
			color[0]=new Color(145,200,220);
		else if(line[0]==10)
			color[0]=new Color(200,175,210);
		else if(line[0]==11)
			color[0]=new Color(140,35,35);
		else if(line[0]==12)
			color[0]=new Color(0,125,100);
		else if(line[0]==13)
			color[0]=new Color(240,150,210);
		else if(line[0]==16)
			color[0]=new Color(50,210,200);
		else if(line[0]==14)
			color[0]=Color.BLACK;
		else if(line[0]==15)
			color[0]=Color.GREEN;
		else if(line[0]==17)
			color[0]=Color.MAGENTA;
		else if(line[0]==18)
			color[0]=Color.PINK;
		
		
		}
	public void setway(Station s1,Station s2){
		station1=s1;
		station2=s2;
		}
}
