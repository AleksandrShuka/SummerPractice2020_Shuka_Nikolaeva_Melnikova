package GUI;

import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleDirectedGraph;


public class ListenableGraph extends DefaultListenableGraph<Integer, EmptyEdge> {
    private int countOfVertex;
   public ListenableGraph() {
       super(new SimpleDirectedGraph<>(EmptyEdge.class));
       countOfVertex = 0;
   }

   public void newVertex() {
       addVertex(countOfVertex);
       countOfVertex++;
   }

   public void deleteVertex(int index) {
       boolean isRemove = removeVertex(index);

       if (isRemove) {
           --countOfVertex;
       }
   }

   public void deleteVertex() {
       deleteVertex(countOfVertex - 1);
   }
}
