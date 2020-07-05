package tests;

import graph.Algorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class AlgorithmTests {
    Vertex vertex1;
    Vertex vertex2;
    Vertex vertex3;
    LinkedList<Vertex> vertexList;
    LinkedList<Edge> edgeList;
    Graph graph;
    Graph graph1;
    Algorithm algorithm;
    @BeforeAll
    public void test(){
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

        algorithm = new Algorithm(graph);
    }


    @Test
    public void testAlgorithmAndGetGraph()throws Exception{
        Assertions.assertEquals(true, algorithm.getGraph()==graph);
    }

    @BeforeEach
    public void BTestDoInBackGround(){

    }

    @Test
    public void testDoInBackGround(){

    }

    @Test
    public  void testUnSleep()throws Exception{

    }

    @BeforeEach
    public void BTestSetRun(){
        algorithm.setRun(false);
    }

    @Test
    public void testIncreaseDelay()throws Exception{

    }
    @Test
    public void testDecreaseDelay()throws Exception{

    }


}
