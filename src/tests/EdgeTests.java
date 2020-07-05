package tests;

import graph.Edge;
import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class EdgeTests {
    @Test
    public Edge testEdge() throws Exception {
        Vertex start = new Vertex(5);
        Vertex end = new Vertex(6);
        Edge edge = new Edge(start, end);
        Assertions.assertTrue(start.getId() == edge.getSourceVertex().getId() &&
                end.getId() == edge.getTargetVertex().getId());
        return edge;
    }

    @Test
    public void testSetGetSourceVertex() {
        Vertex v1 = new Vertex(5);
        Vertex v2 = new Vertex(6);
        Vertex source = new Vertex(3);
        Edge edge = new Edge(v1, v2);
        edge.setSourceVertex(source);

        Assertions.assertSame(source, edge.getSourceVertex());
    }

    @Test
    public void testSetGetTargetVertex() {
        Vertex v1 = new Vertex(5);
        Vertex v2 = new Vertex(6);
        Vertex vertex = new Vertex(3);
        Edge edge = new Edge(v1, v2);
        edge.setTargetVertex(vertex);

        Assertions.assertSame(vertex, edge.getTargetVertex());
    }
}
