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
        Assertions.assertEquals(true, start.getId() == edge.getSourceVertex().getId() && end.getId() == edge.getTargetVertex().getId());
        return edge;
    }

    @Test
    public void testSetGetSourceVertex() {
        Vertex v1 = new Vertex(5);
        Vertex v2 = new Vertex(6);
        Vertex source = new Vertex(3);
        Edge edge = new Edge(v1, v2);
        edge.setSourceVertex(source);
        Assertions.assertEquals(true, source == edge.getSourceVertex());
    }

    @Test
    public void tesSetGetTargetVertex() {
        Vertex v1 = new Vertex(5);
        Vertex v2 = new Vertex(6);
        Vertex vertex = new Vertex(3);
        Edge edge = new Edge(v1, v2);
        edge.setTargetVertex(vertex);
        Assertions.assertEquals(true, vertex == edge.getTargetVertex());
    }

    @Test
    public void testToString() {
        Vertex v1 = new Vertex(5);
        Vertex v2 = new Vertex(6);
        Edge edge = new Edge(v1, v2);
        Assertions.assertEquals(true, edge.toString() == (edge.getSourceVertex().toString() + edge.getTargetVertex().toString()));
    }
}
