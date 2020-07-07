package tests;

import graph.Algorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class AlgorithmTests {
    private Graph graph;
    private Algorithm algorithm;

    public void test() {
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

        Vertex vertex1 = new Vertex(1);
        vertexList.add(vertex1);
        Vertex vertex2 = new Vertex(2);
        vertexList.add(vertex2);
        Vertex vertex3 = new Vertex(3);
        vertexList.add(vertex3);

        edgeList.add(new Edge(vertex1, vertex2));
        edgeList.add(new Edge(vertex2, vertex3));
        edgeList.add(new Edge(vertex3, vertex1));

        graph = new Graph(vertexList, edgeList);
        algorithm = new Algorithm(graph);
    }

    @Test
    public void testAlgorithmAndGetGraph() {
        test();
        Assertions.assertSame(algorithm.getGraph(), graph);
    }

    @Test
    public void testDoInBackGround1() {
        test();
        algorithm.offDelay();
        algorithm.run();
        Assertions.assertEquals(1, algorithm.getCount());
    }

    @Test
    public void testDoInBackGround2() {
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

        graph = new Graph(vertexList, edgeList);
        algorithm = new Algorithm(graph);

        Vertex vertex1 = new Vertex(1);
        vertexList.add(vertex1);
        Vertex vertex2 = new Vertex(2);
        vertexList.add(vertex2);
        Vertex vertex3 = new Vertex(3);
        vertexList.add(vertex3);

        algorithm.offDelay();
        algorithm.run();
        Assertions.assertEquals(3, algorithm.getCount());
    }

    @Test
    public void testDoInBackGround3() {
        test();
        algorithm.getGraph().getEdgeList().clear();
        algorithm.getGraph().getVertexList().clear();
        algorithm.offDelay();
        algorithm.run();
        Assertions.assertEquals(0, algorithm.getCount());
    }

    public void test4() {
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

        Vertex vertex1 = new Vertex(1);
        vertexList.add(vertex1);
        Vertex vertex2 = new Vertex(2);
        vertexList.add(vertex2);
        Vertex vertex3 = new Vertex(3);
        vertexList.add(vertex3);
        Vertex vertex4 = new Vertex(4);
        vertexList.add(vertex4);

        edgeList.add(new Edge(vertex1, vertex2));
        edgeList.add(new Edge(vertex2, vertex3));
        edgeList.add(new Edge(vertex3, vertex1));
        edgeList.add(new Edge(vertex3, vertex4));
        edgeList.add(new Edge(vertex4, vertex1));
        edgeList.add(new Edge(vertex2, vertex4));
        edgeList.add(new Edge(vertex4, vertex3));
        edgeList.add(new Edge(vertex1, vertex3));
        edgeList.add(new Edge(vertex1, vertex4));
        edgeList.add(new Edge(vertex2, vertex1));
        edgeList.add(new Edge(vertex3, vertex2));

        graph = new Graph(vertexList, edgeList);
        algorithm = new Algorithm(graph);
    }

    @Test
    public void testDoInBackGround4() {
        test4();
        algorithm.offDelay();
        algorithm.run();
        Assertions.assertEquals(1, algorithm.getCount());
    }

    public void test5() {
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

        Vertex vertex1 = new Vertex(1);
        vertexList.add(vertex1);
        Vertex vertex2 = new Vertex(2);
        vertexList.add(vertex2);
        Vertex vertex3 = new Vertex(3);
        vertexList.add(vertex3);
        Vertex vertex4 = new Vertex(4);
        vertexList.add(vertex4);
        Vertex vertex5 = new Vertex(5);
        vertexList.add(vertex5);
        Vertex vertex6 = new Vertex(6);
        vertexList.add(vertex6);

        edgeList.add(new Edge(vertex1, vertex2));
        edgeList.add(new Edge(vertex2, vertex3));
        edgeList.add(new Edge(vertex3, vertex1));

        edgeList.add(new Edge(vertex4, vertex5));
        edgeList.add(new Edge(vertex5, vertex4));

        graph = new Graph(vertexList, edgeList);
        algorithm = new Algorithm(graph);
    }

    @Test
    public void testDoInBackGround5() {
        test5();
        algorithm.offDelay();
        algorithm.run();
        Assertions.assertEquals(3, algorithm.getCount());
    }
}
