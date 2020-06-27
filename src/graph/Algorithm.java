package graph;

import org.jetbrains.annotations.NotNull;

public class Algorithm {
    private final int[] id;
    private int count;
    private final boolean[] marked;

    public Algorithm(@NotNull Graph graph) {
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

    private void dfs(@NotNull Vertex vertex) {
        marked[vertex.getId()] = true;
        id[vertex.getId()] = count;
        vertex.setComponentId(count);

        for (Vertex v : vertex.getAdjacencyList()) {
            if (!marked[v.getId()]) {
                dfs(v);
            }
        }
    }

    public int getCount() {
        return this.count;
    }
}
