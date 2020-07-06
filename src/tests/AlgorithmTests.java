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
    Vertex vertex1;
    Vertex vertex2;
    Vertex vertex3;


    public void test() {
        LinkedList<Vertex> vertexList = new LinkedList<>();
        LinkedList<Edge> edgeList = new LinkedList<>();

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
        algorithm.run();
        algorithm.offDelay();
        Assertions.assertEquals(1, algorithm.getCount());
    }

    @Test
    public void testDoInBackGround2() { //без ребер
        test();
        algorithm.getGraph().getEdgeList().clear();
        algorithm.run();
        algorithm.offDelay();
        Assertions.assertEquals(3, algorithm.getCount());
    }

    @Test
    public void testDoInBackGround3() { //пустой
        test();
        algorithm.getGraph().getEdgeList().clear();
        algorithm.getGraph().getVertexList().clear();
        algorithm.run();
        algorithm.offDelay();
        Assertions.assertEquals(0, algorithm.getCount());
    }

    public void test4(){
        test();
        Vertex vertex4 = new Vertex(4);
        algorithm.getGraph().getVertexList().add(vertex4);
        algorithm.getGraph().getEdgeList().add(new Edge(vertex3, vertex4));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex4, vertex1));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex2, vertex4));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex4, vertex3));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex1, vertex3));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex1, vertex4));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex2, vertex1));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex3, vertex2));
    }

    @Test
    public void testDoInBackGround4() { //полностью связный
        test4();
        algorithm.run();
        algorithm.offDelay();
        Assertions.assertEquals(1, algorithm.getCount());
    }

    public void test5(){
        test();
        Vertex vertex4 = new Vertex(4);
        algorithm.getGraph().getVertexList().add(vertex4);
        Vertex vertex5 = new Vertex(5);
        algorithm.getGraph().getVertexList().add(vertex5);
        Vertex vertex6 = new Vertex(6);
        algorithm.getGraph().getVertexList().add(vertex6);
        algorithm.getGraph().getEdgeList().add(new Edge(vertex4, vertex5));
        algorithm.getGraph().getEdgeList().add(new Edge(vertex5, vertex4));
    }

    @Test
    public void testDoInBackGround5() { //случайный
        test5();
        algorithm.run();
        algorithm.offDelay();
        Assertions.assertEquals(3, algorithm.getCount());
    }

}
