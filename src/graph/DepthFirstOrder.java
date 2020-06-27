package graph;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;


public class DepthFirstOrder {
    private final Stack<Vertex> stack;

    public DepthFirstOrder(@NotNull Graph graph) {
        stack = new Stack<>();

        for (Vertex vertex : graph.getVertexList()) {
            if (vertex.isVisited()) {
                dfs(vertex);
            }
        }
    }

    private void dfs(@NotNull Vertex vertex) {
        vertex.setVisited(true);

        for (Vertex v : vertex.getAdjacencyList()) {
            if (v.isVisited()) {
                dfs(v);
            }
        }
        stack.push(vertex);
    }

    public Stack<Vertex> getReversePost() {
        return this.stack;
    }
}
