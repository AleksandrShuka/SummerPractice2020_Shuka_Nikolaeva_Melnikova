package tests;

public class RunTests {
    public void run() {
        try {
            VertexTest vertexTest = new VertexTest();
            vertexTest.testVertex();
            vertexTest.testSetGetId();
            vertexTest.testSetAndIsVisited();
            vertexTest.testSetGetAdjacencyList();
            vertexTest.testSetGetComponentId();
            vertexTest.testAddNeighbour();
            vertexTest.testToString();

            EdgeTests edgeTests = new EdgeTests();
            edgeTests.testEdge();
            edgeTests.testSetGetSourceVertex();
            edgeTests.testSetGetTargetVertex();

            GraphTests graphTests = new GraphTests();
            graphTests.testGraph();
            graphTests.testSetGetEdgeList();
            graphTests.testSetGetVertexList();
            graphTests.testGetTransposedGraph();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
