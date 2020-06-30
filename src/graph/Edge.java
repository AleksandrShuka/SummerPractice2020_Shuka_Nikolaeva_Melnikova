package graph;


public class Edge {
    private Vertex sourceVertex;
    private Vertex targetVertex;

    public Edge(Vertex startVertex, Vertex targetVertex) {
        this.sourceVertex = startVertex;
        this.targetVertex = targetVertex;
    }

    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    public void setSourceVertex(Vertex sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + sourceVertex +
                ", target=" + targetVertex +
                '}';
    }
}
