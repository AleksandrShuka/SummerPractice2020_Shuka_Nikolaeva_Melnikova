package tests;

import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class VertexTest {
    private LinkedList<Vertex> vertexList;
    private Vertex v2;

    @Test
    public void testVertex() {
        Vertex vertex = new Vertex(5);
        Assertions.assertEquals(5, vertex.getId());
    }

    @Test
    public void testSetGetId() {
        Vertex vertex = new Vertex(5);
        vertex.setId(1);
        Assertions.assertEquals(1, vertex.getId());
    }

    @Test
    public void testSetAndIsVisited() {
        Vertex vertex = new Vertex(5);
        vertex.setVisited(true);
        Assertions.assertTrue(vertex.isVisited());
    }

    @BeforeEach
    public void BTestSetGetAdjacencyList() {
        Vertex v1 = new Vertex(1);
        v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        vertexList = new LinkedList<>();
        vertexList.add(v1);
        vertexList.add(v3);
        v2.setAdjacencyList(vertexList);
    }

    @Test
    public void testSetGetAdjacencyList() {
        Assertions.assertEquals(vertexList, v2.getAdjacencyList());
    }

    @Test
    public void testSetGetComponentId() {
        Vertex v = new Vertex(1);
        v.setComponentId(5);
        Assertions.assertEquals(5, v.getComponentId());
    }

    @Test
    public void testAddNeighbour() {
        Vertex v = new Vertex(1);
        v.addNeighbour(v);
        Assertions.assertEquals(v, v.getAdjacencyList().get(v.getAdjacencyList().size() - 1));
    }

    @Test
    public void testToString() {
        Vertex v = new Vertex(1);
        v.setVisited(true);
        v.setComponentId(5);
        Assertions.assertEquals("Vertex{id=1, isVisited=true," +
                " adjacencyList=[], componentId=5}", v.toString());
    }
}
