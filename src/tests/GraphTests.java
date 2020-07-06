package tests;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.junit.jupiter.api.*;

import java.util.LinkedList;


public class GraphTests {
    Vertex vertex1 = new Vertex(1);
    Vertex vertex2 = new Vertex(2);
    Vertex vertex3 = new Vertex(3);
    LinkedList<Vertex> vertexList = new LinkedList<>();
    LinkedList<Edge> edgeList = new LinkedList<>();
    Graph graph;
    Graph graph1;

    public void test() {
        vertexList.add(vertex1);
        vertexList.add(vertex2);
        vertexList.add(vertex3);

        edgeList.add(new Edge(vertex1, vertex2));
        edgeList.add(new Edge(vertex2, vertex3));
        edgeList.add(new Edge(vertex3, vertex1));

        graph = new Graph(vertexList, edgeList);
    }

    @Test
    public void testGraph() {
        test();
        Assertions.assertEquals(vertexList, graph.getVertexList());
        Assertions.assertEquals(edgeList, graph.getEdgeList());
    }

    @Test
    public void testSetGetVertexList() {
        test();
        Vertex vertex4 = new Vertex(4);
        vertexList.add(vertex4);
        graph.setVertexList(vertexList);

        Assertions.assertEquals(vertexList, graph.getVertexList());
    }

    @Test
    public void testSetGetEdgeList() {
        test();
        edgeList.add(new Edge(vertex1, vertex3));
        graph.setEdgeList(edgeList);
        Assertions.assertEquals(edgeList, graph.getEdgeList());
    }

    @Test
    public void testGetTransposedGraph() {
        test();
        graph1 = graph.getTransposedGraph();
        Assertions.assertSame(graph1.getEdgeList().get(0).getTargetVertex().getId(),
                graph.getEdgeList().get(0).getSourceVertex().getId());
    }
}
