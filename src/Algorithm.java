public class Algorithm {
    private int[] id;
    private int count;
    private boolean[] marked;

    public Algorithm(Graph graph) {
        DepthFirstOrder dfs = new DepthFirstOrder(graph.getTransposedGraph());

        marked = new boolean[graph.getVertexList().size()];
        id = new int[graph.getVertexList().size()];

        for (Vertex vertex : dfs.getReversePost()) {
            if (!marked[vertex.getId()]) {
                dfs(vertex);
                count++;
            }
        }
    }

    private void dfs(Vertex vertex) {
        marked[vertex.getId()] = true;
        id[vertex.getId()] = count;
        vertex.setComponentId(count);

        for (Vertex v : vertex.getAdjacenciesList()) {
            if (!marked[v.getId()]) {
                dfs(v);
            }
        }
    }

    public int getCount() {
        return this.count;
    }
}
