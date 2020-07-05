package tests;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.junit.jupiter.api.*;

import java.util.LinkedList;

public class GraphTests {
    Vertex vertex1;
    Vertex vertex2;
    Vertex vertex3;
    LinkedList<Vertex> vertexList;
    LinkedList<Edge> edgeList;
    Graph graph;
    Graph graph1;

    @BeforeAll
    public void test() {
        vertexList = new LinkedList<>();
        edgeList = new LinkedList<>();

        vertex1 = new Vertex(1);
        vertexList.add(vertex1);
        vertex2 = new Vertex(2);
        vertexList.add(vertex2);
        vertex3 = new Vertex(3);
        vertexList.add(vertex3);

        edgeList.add(new Edge(vertex1, vertex2));
        edgeList.add(new Edge(vertex2, vertex3));
        edgeList.add(new Edge(vertex3, vertex1));

        graph = new Graph(vertexList, edgeList);
    }

    @Test
    public void testGraph() {
        Assertions.assertEquals(vertexList, graph.getVertexList());
        Assertions.assertEquals(edgeList, graph.getEdgeList());
    }

    @BeforeEach
    public void BTestSetGetVertexList() {
        Vertex vertex4 = new Vertex(4);
        vertexList.add(vertex4);
        graph.setVertexList(vertexList);
    }

    @Test
    public void testSetGetVertexList() {
        Assertions.assertEquals(vertexList, graph.getVertexList());
    }

    @BeforeEach
    public void BTestSetGetEdgeList() {
        edgeList.add(new Edge(vertex1, vertex3));
        graph.setEdgeList(edgeList);
    }

    @Test
    public void testSetGetEdgeList() {
        Assertions.assertEquals(edgeList, graph.getEdgeList());
    }

    @BeforeEach
    public void BTestGetTransposedGraph() {
        graph1 = graph.getTransposedGraph();
    }

    @Test
    public void testGetTransposedGraph() throws Exception {
        Assertions.assertSame(graph1.getEdgeList().get(0).getTargetVertex(),
                graph.getEdgeList().get(0).getSourceVertex());
    }

    @AfterAll
    public void ATest() {

    }
}
