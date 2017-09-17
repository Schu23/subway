package dataStructure;
import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm {

	/**
	 * @param args
	 */
	ArrayList<Edge> EdgeGroup1 = new ArrayList<Edge>(); // 储存prim算法的添加边集及顺序
	ArrayList<Edge> EdgeGroup2 = new ArrayList<Edge>();// 储存kruskal算法的添加边集及顺序
	static ArrayList<Edge> EdgeGroupExtra = new ArrayList<Edge>();// 辅助作用

	public void Input() {
		// TODO Auto-generated method stub
		System.out.println("请输入图的顶点个数:");
		Scanner scan = new Scanner(System.in);
		int v_num = scan.nextInt();
		System.out.println("请输入录入的边的个数：");
		int v_edge = scan.nextInt();
		Graph graph = new Graph(v_num);

		for (int i = 0; i < v_edge; i++) {
			System.out.println("请按照\"顶点a,顶点b,权重\"的格式录入边:");
			int x = scan.nextInt();
			int y = scan.nextInt();
			int w = scan.nextInt();

			Edge e = new Edge(x, y, w);
			graph.add(e);
			System.out.println("已录入" + x + "————" + y + "=" + w);

		}
		System.out.println("您录入的图为：");

	}

	public boolean Kruskal(Graph g) {

		Graph base = g;
		Graph graph = new Graph(g.vertix_number);// 存储最小生树
		ArrayList<Integer> selected_vertix = new ArrayList<Integer>();
		int selected_edge = 0;
		int base_edge_num = base.edge_number;

		// 将所有边都试着加一次..
		for (int i = 0; i < base_edge_num; i++) {
			Edge min = base.getMin(); // 得到这些边中最小的边
			// System.out.println("最小边为" + min.x + "——" + min.y);

			// 如果新加入的边的产生环路,有点猥琐，因为在触发算法那里
			// 执行kruskal方法前先执行一次Prim算法，获得了所有的应该添加的边
			// 这个边很小，而又不选，证明是回路
			if (!hasCheck(EdgeGroupExtra, min)) {
				// System.out.println("加入有环路,舍弃");
				base.delete(min.x, min.y);// 从底图中删除这条边

			} else {

				graph.add(min);// 加入这条边
				EdgeGroup2.add(min);
				base.delete(min.x, min.y);// 底图中删除这条边
				// System.out.println("可以加入");
				if (!selected_vertix.contains(new Integer(min.x))) {

					selected_vertix.add(new Integer(min.x));// 生成图的点集中加入已选点

				}
				if (!selected_vertix.contains(new Integer(min.y))) {

					selected_vertix.add(new Integer(min.y));// 生成图的点集中加入已选点

				}
				selected_edge++;
			}

			if (selected_edge == base.vertix_number - 1)
				// 对于n个顶点的图，一直无环路添加所有边，如果能保证添加到n-1条边，必为树
			{
				break;
			}
		}

		if (selected_edge == base.vertix_number - 1)// 可以生成最小生成树
		{
			// System.out.println("成功,最小生成树为：");
			// graph.output();
			return true;
		} else {
			// System.out.println("无法生成");
			return false;
		}

	}

	public boolean Prim(Graph g) {
		Graph base = g;
		Graph graph = new Graph(g.vertix_number);// 存储最小生成树
		ArrayList<Integer> selected_vertix = new ArrayList<Integer>();
		int selected_edge_num = 1;
		int selected_vertix_num = 2;// 初始时会选一条边，即至少1个边，两个顶点
		int base_edge_num = base.edge_number;

		// 第一次，先将最小边加入树
		Edge min = base.getMin();
		graph.add(min);
		EdgeGroup1.add(min);
		selected_vertix.add(new Integer(min.x));// 生成图的点集中加入已选点
		selected_vertix.add(new Integer(min.y));// 生成图的点集中加入已选点
		base.delete(min.x, min.y);// 原图删除最小边
		// System.out.println("首先加入" + min.x + "——" + min.y);

		ArrayList<Edge> to_choose_edge = new ArrayList<Edge>();// 待选边合集
		to_choose_edge
				.addAll(base.getEdgeByVertix((int) selected_vertix.get(0))); // 初始时加入最短边的一个顶点的所有邻接边作为待选边

		int new_add = (int) selected_vertix.get(1); // 初始时将最短边的另一个顶点认为是新加的点
		int before_add = 0;// 用来判断是否有新的结点加入，防止重复加入边...

		for (int i = 0; i < base_edge_num; i++) {

			// 待选边集中加入新增顶点的相邻边
			if (new_add != before_add)// 判断new_add的值是否更改 即是否有新节点加入
				to_choose_edge.addAll(base.getEdgeByVertix(new_add));
			if (to_choose_edge.isEmpty())
				break;

			Edge choose = getMin(to_choose_edge);// 获取邻接边中的最小边
			// System.out.println("相邻最小边" + choose.x + "——" + choose.y);
			before_add = new_add;

			if (selected_vertix.contains(new Integer(choose.y))
					&& selected_vertix.contains(new Integer(choose.x))) // 如果新加入的边的产生环路
			{
				// System.out.println("加入有环路,舍弃");
				base.delete(choose.x, choose.y);// 从底图中删除这条边
				to_choose_edge.remove(choose);// 待选边中删除这条边

			} else {

				graph.add(choose);// 加入这条边
				EdgeGroup1.add(choose);
				base.delete(choose.x, choose.y);// 底图中删除这条边
				to_choose_edge.remove(choose);// 待选边中删除这条边
				// System.out.println("可以加入");
				if (!selected_vertix.contains(new Integer(choose.x))) {
					selected_vertix.add(new Integer(choose.x));// 生成图的点集中加入已选点
					new_add = choose.x;
				}
				if (!selected_vertix.contains(new Integer(choose.y))) {
					selected_vertix.add(new Integer(choose.y));// 生成图的点集中加入已选点
					new_add = choose.y;
				}

				selected_edge_num++;
			}
			if (selected_edge_num == base.vertix_number - 1)// 对于n个顶点的图，一直无环路添加所有边，如果能保证添加到n-1条边，必为树
			{
				break;
			}
		}

		EdgeGroupExtra = EdgeGroup1;

		if (selected_edge_num == base.vertix_number - 1)// 可以生成最小生成树
		{
			// System.out.println("成功,最小生成树为：");
			// graph.output();
			return true;
		} else {
			// System.out.println("无法生成");
			return false;
		}

	}
	
	//最短路径dijkstra算法，计算v0到其余各顶点的最短路径
	public int[] Dijkstra(Graph g,int v0){
		int n=g.vertix_number;
		int[] dist=new int[n];
		int[] prev=new int[n];
		boolean[] visited=new boolean[n];
		
		//初始化
		for(int i=0;i<n;i++){
			dist[i]=g.Matrix[v0][i];
			visited[i]=false;
			//直达情况下的最后经由点就是出发点
			if(i!=v0&&dist[i]<g.noEdge)
				prev[i]=v0;
			else
				prev[i]=-1;//无直达路径
		}
		
		////初始时源点v0∈visited集，表示v0 到v0的最短路径已经找到
		visited[v0]=true;
		
		//经由一个点中转到达其余各点
		int minDist;
		int v=0;
		for(int i=1;i<n;i++){
			minDist=g.noEdge;
			for(int j=0;j<n;j++)
				if((!visited[j])&&dist[j]<g.noEdge){
					v=j;
					minDist=dist[j];
				}
			visited[v]=true;
			
			//由v0到达v顶点的最短路径为min. 假定由v0到v，再由v直达其余各点，更新当前最后一个经由点及距离*/ 
			for(int j=0;j<n;j++){
				if((!visited[j])&&dist[j]<g.noEdge){
					dist[j]=minDist+g.Matrix[v][j];
					prev[j]=v;
					visited[j]=true;
				}
			}
		}
		return dist;
	}
	
	//最短路径floyd算法，求任意两点间最短距离
	public int[][] Floyd(Graph g){
		int n=g.vertix_number;
		int[][] d=new int[n][n];//保存从i到j的最小路径值
		
		//初始化
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++){
				d[i][j]=g.Matrix[i][j];
			}

		for(int k = 0; k < n; k++){  
            for(int i=0; i < n; i++ ){  
                for(int j=0; j < n; j++){
                	if(d[i][k]>=Integer.MAX_VALUE||d[k][j]>=Integer.MAX_VALUE)
                		continue;
                    if(d[i][j]>d[i][k]+d[k][j]){  
                        d[i][j]=d[i][k]+d[k][j];  
                    }  
                }  
            }  
        }  
		
		return d;
	}

	// 计算生成树的权值
	public int countWeight() {
		int weight = 0;

		if (!EdgeGroup1.isEmpty())
			for (Edge e : EdgeGroup1) {
				weight += e.w;
			}
		else
			for (Edge e : EdgeGroup2) {
				weight += e.w;
			}

		return weight;

	}

	public Edge getMin(ArrayList<Edge> egroup) {
		int length = egroup.size();
		Edge min_edge = egroup.get(0);// 默认第一条边为最小边
		for (int i = 1; i < length; i++) {
			if (egroup.get(i).w < min_edge.w) {
				min_edge = egroup.get(i);
			}
		}
		return min_edge;

	}

	boolean hasCheck(ArrayList<Edge> egroup, Edge e) {
		// 必须重新写一个判断边集里有没有边的方法
		// 因为这是无向图，x和y颠倒也是同一条边
		for (Edge edge : egroup) {
			if (edge.x == e.x && edge.y == e.y && edge.w == e.w)
				return true;
			else if (edge.x == e.y && edge.y == e.x && edge.w == e.w)
				return true;
		}
		return false;
	}

}
