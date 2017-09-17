package dataStructure;
import java.util.ArrayList;

class Vertex {
	private int data;
	private ArcEdge firstArc;
	
	Vertex(){};
	
	Vertex(int d,ArcEdge f){
		setData(d);
		setFirstArc(f);
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public ArcEdge getFirstArc() {
		return firstArc;
	}

	public void setFirstArc(ArcEdge firstArc) {
		this.firstArc = firstArc;
	}
	
}

class Edge {

	int x, y;
	int w;

	Edge(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.w = w;
	}

}

class ArcEdge {
	private int adjVex;
	private ArcEdge nextArc;
	private int w;
	
	ArcEdge(){};
	
	ArcEdge(int a,ArcEdge n,int we){
		adjVex=a;
		nextArc=n;
		w=we;
	}
	
	public int getAdjVex() {
		return adjVex;
	}
	public void setAdjVex(int adjVex) {
		this.adjVex = adjVex;
	}
	public ArcEdge getNextArc() {
		return nextArc;
	}
	public void setNextArc(ArcEdge nextArc) {
		this.nextArc = nextArc;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	
}

class Graph {

	ArrayList<ArcEdge> AdjList;
	int[][] Matrix;//图的邻接矩阵
	int vertix_number;//图的顶点个数
	int edge_number;//图的边的个数
	int noEdge=Integer.MAX_VALUE;

	Graph(int v) {
		this.vertix_number = v;
		Matrix = new int[v][v];
		//默认不使用0这个顶点，从1开始
		for (int i = 0; i <vertix_number; i++) {
			for (int j = 0; j <vertix_number; j++) {
				Matrix[i][j]=noEdge;
			}
		}

	}

	public void add(Edge e) {

		Matrix[e.x][e.y] = e.w;
		Matrix[e.y][e.x] = e.w;
		edge_number++;
	}
	
	public boolean isExist(int i,int j){
		if(Matrix[i][j]==noEdge)
			return false;
			else
				return true;	
	}

	public ArrayList<Edge> getEdgeByVertix(int v) {
		ArrayList<Edge> getEdge = new ArrayList<Edge>();
		for (int i = 0; i <vertix_number; i++) {
			if (Matrix[v][i] != noEdge)
				getEdge.add(new Edge(v, i, Matrix[v][i]));
		}

		return getEdge;

	}

	public ArrayList<Integer> getLinkedVertix(int v) {
		ArrayList<Integer> getVertix = new ArrayList<Integer>();
		for (int i = 0; i <vertix_number; i++) {
			if (Matrix[v][i] != noEdge)
				getVertix.add(new Integer(i));
		}

		return getVertix;

	}

	public void delete(int x, int y) {

		Matrix[x][y]=noEdge;
		Matrix[y][x]=noEdge;
		edge_number--;
	}
	public Edge getMin(){
		Edge min=new Edge(0,0,Matrix[0][0]);
		for (int i = 0; i <vertix_number; i++) {
			for (int j =0; j < vertix_number; j++) {
				if(Matrix[i][j]==noEdge){
					continue;
				}
				if (Matrix[i][j]<min.w){
					min.x=i;
					min.y=j;
					min.w=Matrix[i][j];
				}
					
			}
		}
		return min;	
	}
	

	public void output() {
		for (int i = 0; i <vertix_number; i++) {
			for (int j = 0; j < vertix_number; j++) {
				if (Matrix[i][j] != noEdge&& j>i)//避免重复输出
					System.out.println(i + "——" + j + "距离为" + Matrix[i][j]);
			}
		}
	}
	
	public ArrayList<ArcEdge> setList(){
		ArcEdge p=new ArcEdge();
		ArrayList<ArcEdge> AdjList=new ArrayList<ArcEdge>();
		//int[] visit=new int[vertix_number];
		
		for(int i=0;i<vertix_number;i++){
			AdjList.add(new ArcEdge(i+1,null,0));
			p=AdjList.get(i);
			for(int j=0;j<vertix_number;j++){
				if(Matrix[i][j]!=noEdge){
					p.setNextArc(new ArcEdge(j,null,Matrix[i][j]));
					p=p.getNextArc();
				}
			}
			p.setNextArc(null);
		}
		return AdjList;
	}

}
