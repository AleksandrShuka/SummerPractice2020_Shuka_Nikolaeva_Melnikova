package tests;

import graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VertexTest {
    @BeforeEach
    public static void BTestVertex() {
        Vertex vertex = new Vertex(5);
    }

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

    @Test
    public void testSetGetAdjacencyList() {
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        /*List<Vertex> adjacencyList = new List<>();
        adjacencyList.add(v1);
        adjacencyList.add(v3);
        v2.setAdjacencyList(adjacencyList);
        Assertions.assertEquals(adjacencyList, v2.getAdjacencyList());
         */
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
        Assertions.assertEquals(v, v.getAdjacencyList().get(v.getAdjacencyList().size()));
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
