package tests;

import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class VertexTest {
    LinkedList<Vertex> vertexList;
    Vertex v1;
    Vertex v2;
    Vertex v3;

    @BeforeEach
    public static void BTestVertex() {
        Vertex vertex = new Vertex(5);
    }

    @Test
    public void testVertex() throws Exception{
        Vertex vertex = new Vertex(5);
        Assertions.assertEquals(5, vertex.getId());
    }

    @Test
    public void testSetGetId() throws Exception{
        Vertex vertex = new Vertex(5);
        vertex.setId(1);
        Assertions.assertEquals(1, vertex.getId());
    }

    @Test
    public void testSetAndIsVisited() throws Exception{
        Vertex vertex = new Vertex(5);
        vertex.setVisited(true);
        Assertions.assertTrue(vertex.isVisited());
    }

    @BeforeEach
    public void BTestSetGetAdjacencyList(){
        v1 = new Vertex(1);
        v2 = new Vertex(2);
        v3 = new Vertex(3);
        vertexList = new LinkedList<>();
        vertexList.add(v1);
        vertexList.add(v3);
        v2.setAdjacencyList(vertexList);
    }

    @Test
    public void testSetGetAdjacencyList() throws Exception{
        Assertions.assertEquals(vertexList, v2.getAdjacencyList());
    }

    @Test
    public void testSetGetComponentId() throws Exception{
        Vertex v = new Vertex(1);
        v.setComponentId(5);
        Assertions.assertEquals(5, v.getComponentId());
    }

    @Test
    public void testAddNeighbour() throws Exception{
        Vertex v = new Vertex(1);
        v.addNeighbour(v);
        Assertions.assertEquals(v, v.getAdjacencyList().get(v.getAdjacencyList().size()));
    }

    @Test
    public void testToString() throws Exception{
        Vertex v = new Vertex(1);
        v.setVisited(true);
        v.setComponentId(5);
        Assertions.assertEquals("Vertex{id=1, isVisited=true," +
                " adjacencyList=[], componentId=5}", v.toString());
    }
}
