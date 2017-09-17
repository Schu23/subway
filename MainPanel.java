package dataStructure;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

	int x;
	int y;// 获取点的坐标
	int x1, y1;// 边的终点
	int x0, y0;// 边的起点
	int micro_adapt = 10;
	int auto_adpat = 25;
	static boolean check1 = false, check2 = false, check3 = false;
	static int point_number = 0;
	int start, end, weight;

	static ArrayList<Point> points = new ArrayList<Point>();
	static ArrayList<Edge> edges = new ArrayList<Edge>();

	public MainPanel() {

		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.lightGray);
		setSize(300, 300);
		JButton clear = new JButton("clear");
		clear.setFont(new Font("Arial", Font.PLAIN, 15));
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				points.clear();
				edges.clear();
				check1 = false;
				check2 = false;
				check3 = false;
				point_number = 0;
				repaint();
				ShowPanel.count=0;
				ShowPanel.checkA=false;
				ShowPanel.checkB=false;
			}
		});
		add(clear);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Stroke stroke = new BasicStroke(3.0f);// 设置线宽为3.0
		g2d.setStroke(stroke);
		
		g2d.setFont(new Font("", Font.PLAIN, 15));
        g2d.drawString("请在灰色区域内绘制，双击左键画点，拖动右键画线", 30, 515);
		

		Iterator i1 = points.iterator();
		Iterator i2 = edges.iterator();
		g2d.setColor(Color.blue);
		while (i1.hasNext()) {
			Point p = (Point) i1.next();
			g2d.fillOval(p.x, p.y, 15, 15);
			g2d.setFont(new Font("", Font.BOLD, 15));
			g2d.drawString("v" + p.name, p.x, p.y);

		}

		if (check1 == true && check3 == true)
			g.drawLine(x0 + micro_adapt, y0 + micro_adapt, x1 + micro_adapt, y1
					+ micro_adapt);

		while (i2.hasNext()) {
			Edge e = (Edge) i2.next();
			int x1 = points.get(e.x).x + micro_adapt;
			int y1 = points.get(e.x).y + micro_adapt;
			int x2 = points.get(e.y).x + micro_adapt;
			int y2 = points.get(e.y).y + micro_adapt;
			g.setColor(Color.gray);
			g.drawLine(x1, y1, x2, y2);
			g.setColor(Color.red);
			g.setFont(new Font("", Font.ITALIC, 20));
			g.drawString("" + e.w, (x1 + x2) / 2, (y1 + y2) / 2);
			// 取出边集中每条边的两个顶点points.get(e.x-1)和points.get(e.y-1)
			// 点是从1开始添加,而取的索引从0开始
			// 然后取出每个顶点的在图中的横纵坐标
		}
	
	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getClickCount() >= 2 && evt.getButton() == MouseEvent.BUTTON1) {
			x = evt.getX();
			y = evt.getY();
			Point p = new Point(x, y, point_number);
			points.add(p);
			point_number++;// 双击一次 点数加1
			repaint();
		}
	}

	public void mousePressed(MouseEvent evt) {

		if (evt.getButton() != MouseEvent.BUTTON1) {
			x0 = evt.getX();
			y0 = evt.getY();
			for (Point p : points) {
				if ((p.x - x0) <= auto_adpat && (p.y - y0) <= auto_adpat
						&& (p.x - x0) >= -auto_adpat
						&& (p.y) - y0 >= -auto_adpat) {

					x0 = p.x;
					y0 = p.y;
					start = p.name;
					check1 = true;
					break;
				}

			}
		}
		repaint();

	}

	public void mouseDragged(MouseEvent evt) {
		if (evt.getButton() != MouseEvent.BUTTON1) {
			x1 = evt.getX();
			y1 = evt.getY();
			check3 = true;
			repaint();

		}
	}

	public void mouseReleased(MouseEvent evt) {
		x1 = evt.getX();
		y1 = evt.getY();
		if (evt.getButton() != MouseEvent.BUTTON1) {

			// System.out.println("释放了左键,坐标为" + x1 + "," + y1);
			for (Point p : points) {
//				System.out.println("检索" + p.name + "的坐标为" + p.x + "," + p.y);
				if ((p.x - x1) < auto_adpat && (p.y - y1) < auto_adpat
						&& (p.x - x1) > -auto_adpat && (p.y) - y1 > -auto_adpat) {
					x1 = p.x;
					y1 = p.y;
					check2 = true;
					end = p.name;
					// System.out.println("定位到终点" + p.name);
					break;
				}

			}

			if (check1 == true && check2 == true) {

				try {
					String inputValue = JOptionPane.showInputDialog("请输入该边权值:");
					Edge e = new Edge(start, end, Integer.parseInt(inputValue));
					edges.add(e);
				} catch (Exception e) {
					e.printStackTrace(); 
				}
			}
		}

		check1 = false;
		check2 = false;
		repaint();

	}

	public void mouseMoved(MouseEvent evt) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}

class ShowPanel extends JPanel {

	ArrayList<Edge> EdgeGroup1 = new ArrayList<Edge>();
	ArrayList<Edge> EdgeGroup2 = new ArrayList<Edge>();
	int countWeight;
	static boolean checkA = false, checkB = false;

    boolean PrimTrue, KruskalTrue;

	static int count = 0; // 用于逐渐画点，判断需要画几个点，rapaint一次加一个;
	int micro_adapt = 10;
	final int x_pos=50;

	public void paintComponent(Graphics g2d) {

		super.paintComponent(g2d);

		Stroke stroke = new BasicStroke(3.0f);// 设置线宽为3.0
		((Graphics2D) g2d).setStroke(stroke);
		ArrayList<Edge> use;

		if (checkA == true || checkB == true) {
			g2d.setFont(new Font("",Font.PLAIN,15));

			if (PrimTrue == true || KruskalTrue == true) {
				g2d.drawString("有最小生成树,权重为:" + countWeight, 170, 60);
				count++;

				if (checkA == true)
					use = EdgeGroup1;

				else
					use = EdgeGroup2;

				for (int i = 0; i < count && i < use.size(); i++) {
					Edge e = use.get(i);
					Point p1 = new Point(x_pos+MainPanel.points.get(e.x).x,
							MainPanel.points.get(e.x).y, e.x);
					Point p2 = new Point(x_pos+MainPanel.points.get(e.y).x,
							MainPanel.points.get(e.y).y, e.y);
					g2d.setColor(Color.red);
					g2d.setFont(new Font("", Font.BOLD, 15));
					g2d.fillOval(p1.x, p1.y, 15, 15);
					g2d.drawString("v" + p1.name, p1.x, p1.y);
					g2d.fillOval(p2.x, p2.y, 15, 15);
					g2d.drawString("v" + p2.name, p2.x, p2.y);

					int x1 = p1.x + micro_adapt;
					int y1 = p1.y + micro_adapt;
					int x2 = p2.x + micro_adapt;
					int y2 = p2.y + micro_adapt;
					g2d.setColor(Color.gray);
					g2d.drawLine(x1, y1, x2, y2);
					g2d.setColor(Color.blue);
					g2d.setFont(new Font("", Font.ITALIC, 20));
					g2d.drawString("" + e.w, (x1 + x2) / 2, (y1 + y2) / 2);

				}

			}

			else{
				JOptionPane.showMessageDialog(null, "该图无法生成最小生成树", "alert", JOptionPane.ERROR_MESSAGE);
				g2d.drawString("该图无法生成最小生成树，失败。", 150, 60);
			}
		}

	}

	ShowPanel() {
		JButton bt1 = new JButton("Prim");
		JButton bt2 = new JButton("Kruskal");
		bt1.setFont(new Font("Arial", Font.PLAIN, 15));
		bt2.setFont(new Font("Arial", Font.PLAIN, 15));
		MyListener listener = new MyListener();
		bt1.addActionListener(listener);
		bt2.addActionListener(listener);

		add(bt1);
		add(bt2);
		setSize(300, 300);
		setBackground(Color.white);

		// 1.5s重新画一次界面...使得动态显示
		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		};
		t.schedule(task, 1500, 1500);

	}

	class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			// 每次都重新判断一下能否生成最小生成树
			PrimTrue = false;
			KruskalTrue = false;

			Algorithm al = new Algorithm();
			Graph graph = new Graph(MainPanel.point_number);
			Graph graphforCopy = new Graph(MainPanel.point_number);// 一个复制的图。防止一个对象两次算法相互影响

			for (Edge edge : MainPanel.edges) {
				graph.add(edge); // 将变集加到图中 转换成图
				graphforCopy.add(edge);
			}
			
			graph.setList();

			if (e.getActionCommand().equals("Prim")) {

				if (checkA || checkB)
					count = 0;
				PrimTrue = al.Prim(graph);// 进行普林算法
				countWeight = al.countWeight();
				EdgeGroup1 = al.EdgeGroup1;
				checkA = true;
				checkB = false;
			}
			if (e.getActionCommand().equals("Kruskal")) {
				if (checkA || checkB)// 如果之前已经点过两个按钮中的一个，那么这次点要重新开始画
					count = 0;
				al.Prim(graphforCopy);// 预先进行普林算法,把已选边集加入

				KruskalTrue = al.Kruskal(graph);// 进行克鲁斯卡尔算法
				countWeight = al.countWeight();
				EdgeGroup2 = al.EdgeGroup2;
				checkB = true;
				checkA = false;
			}
			repaint();

		}

	}

}

class Point {
	int x;
	int y;
	int name;

	Point(int x, int y, int code) {
		this.x = x;
		this.y = y;
		this.name = code;
	}
	
}


class ListPanel extends JPanel {

	ArrayList<ArcEdge> EdgeGroup = new ArrayList<ArcEdge>();

	//configurations
	int micro_adapt = 10;
	final int rec_width=60;
	final int rec_height=30;
	final int x_interval=30;
	final int y_interval=0;
	final int x_pos=20;
	final int y_pos=20;

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		ArcEdge p=new ArcEdge();
		
		Graph graph = new Graph(MainPanel.point_number);
		
		for (Edge edge : MainPanel.edges) 
			graph.add(edge); // 将变集加到图中 转换成图
		EdgeGroup=graph.setList();


		Font f=new Font("",Font.BOLD,14);
		g.setFont(f);
		FontMetrics fm=g.getFontMetrics();

		for(int i=0;i<EdgeGroup.size();i++){
			int cnt=0;
			p=EdgeGroup.get(i);
			g.drawRect(x_pos, y_pos+(rec_height+y_interval)*i, rec_width, rec_height);//边框
			g.drawLine(x_pos+rec_width/2, y_pos+(rec_height+y_interval)*i, x_pos+rec_width/2, y_pos+(rec_height+y_interval)*i+rec_height);//分隔线
			g.drawString("v"+(p.getAdjVex()-1), x_pos+rec_width/4-fm.stringWidth("v1")/2, y_pos+(rec_height+y_interval)*i+rec_height-fm.getAscent()/2);//顶点名字
			
			while(p.getNextArc()!=null){
				//绘制直线
				g.drawLine((int)(x_pos+rec_width*0.8)+cnt*(rec_width+x_interval), y_pos+(rec_height+y_interval)*i+rec_height/2, 
						x_pos+rec_width+cnt*(rec_width+x_interval)+x_interval,y_pos+(rec_height+y_interval)*i+rec_height/2);
				//绘制箭头
				GeneralPath triangle = new GeneralPath();  
			    triangle.moveTo(x_pos+rec_width+cnt*(rec_width+x_interval)+x_interval-8, y_pos+(rec_height+y_interval)*i+rec_height/2-4);  
			    triangle.lineTo(x_pos+rec_width+cnt*(rec_width+x_interval)+x_interval-8, y_pos+(rec_height+y_interval)*i+rec_height/2+4);  
			    triangle.lineTo(x_pos+rec_width+cnt*(rec_width+x_interval)+x_interval, y_pos+(rec_height+y_interval)*i+rec_height/2);  
			    triangle.closePath();  
			    //实心箭头  
			    ((Graphics2D) g).fill(triangle);
			    
				p=p.getNextArc();
				cnt++;
				g.drawRect(x_pos+cnt*(rec_width+x_interval),y_pos+(rec_height+y_interval)*i, rec_width, rec_height);//边框
				g.drawLine(x_pos+rec_width/2+cnt*(rec_width+x_interval), y_pos+(rec_height+y_interval)*i, 
						x_pos+rec_width/2+cnt*(rec_width+x_interval), y_pos+(rec_height+y_interval)*i+rec_height);//分隔线
				g.drawString(String.valueOf(p.getAdjVex()), x_pos+cnt*(rec_width+x_interval)+rec_width/4-fm.stringWidth("1")/2,
						y_pos+(rec_height+y_interval)*i+rec_height-fm.getAscent()/2);//顶点序号
			}
			
			g.drawLine((int)(x_pos+rec_width*0.6+cnt*(rec_width+x_interval)), (int)(y_pos+(rec_height+y_interval)*i+rec_height*0.2), 
					(int)(x_pos+rec_width*0.85+cnt*(rec_width+x_interval)), (int)(y_pos+(rec_height+y_interval)*i+rec_height*0.8));//斜线
		}

	}

	ListPanel() {
		setBackground(Color.white);
		repaint();
	}
}

class DistancePanel extends JPanel {

	int v0;
	int[] d_dist;
	int[][] f_dist;
	boolean dijkstra;
	boolean floyd;

	int micro_adapt = 10;
	final int x_pos=50;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font f=new Font("",Font.BOLD,16);
		g.setFont(f);
		
		if(dijkstra==true){
			for(int i=0;i<d_dist.length;i++){
				if(i==v0)
					continue;
				g.drawString("(v"+v0+",v"+i+")="+(d_dist[i]<Integer.MAX_VALUE?String.valueOf(d_dist[i]):"INF"), 40, 80+i*20);
			}
		}
		
		if(floyd==true){
			for(int i=0;i<MainPanel.point_number;i++){
				g.drawString("v"+i, 50+30*i,80);
				g.drawString("v"+i, 20,110+30*i);
			}
			for(int i=0;i<MainPanel.point_number;i++){
				for(int j=0;j<MainPanel.point_number;j++)
					if(i==j)
						g.drawString("/", 50+30*i, 110+30*j);
					else
					g.drawString((f_dist[i][j]<Integer.MAX_VALUE?String.valueOf(f_dist[i][j]):"INF"), 50+30*i, 110+30*j);
			}
		}
	}

	DistancePanel() {
		JButton bt1 = new JButton("Dijkstra");
		JButton bt2 = new JButton("Floyd");
		bt1.setFont(new Font("Arial", Font.PLAIN, 15));
		bt2.setFont(new Font("Arial", Font.PLAIN, 15));
		MyListener listener = new MyListener();
		bt1.addActionListener(listener);
		bt2.addActionListener(listener);

		add(bt1);
		add(bt2);

		setBackground(Color.white);

	}

	class MyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Algorithm al = new Algorithm();
			Graph graph = new Graph(MainPanel.point_number);
			Graph graphforCopy = new Graph(MainPanel.point_number);// 一个复制的图。防止一个对象两次算法相互影响
			d_dist=new int[MainPanel.point_number];
			f_dist=new int[MainPanel.point_number][MainPanel.point_number];

			for (Edge edge : MainPanel.edges) {
				graph.add(edge); // 将变集加到图中 转换成图
				graphforCopy.add(edge);
			}

			if (e.getActionCommand().equals("Dijkstra")) {
				dijkstra=true;
				floyd=false;
				String inputValue = JOptionPane.showInputDialog("请输入起始顶点下标:");
				v0=Integer.parseInt(inputValue);
				d_dist=al.Dijkstra(graph, v0);
				repaint();
			}
			if (e.getActionCommand().equals("Floyd")) {
				floyd=true;
				dijkstra=false;
				f_dist=al.Floyd(graphforCopy);
				repaint();
			}
		
		}

	}

}
