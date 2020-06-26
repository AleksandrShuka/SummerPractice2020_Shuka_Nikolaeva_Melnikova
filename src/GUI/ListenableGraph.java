package GUI;

import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.SimpleDirectedGraph;


public class ListenableGraph extends DefaultListenableGraph<Integer, EmptyEdge> {
   ListenableGraph() {
       super(new SimpleDirectedGraph<Integer, EmptyEdge>(EmptyEdge.class));
   }

   public void testBuild() {
       addVertex(1);
       addVertex(2);
       addVertex(3);
       addVertex(4);
       addVertex(5);
       addVertex(6);
       addVertex(7);
       addVertex(8);
       addVertex(9);

       addEdge(1, 3);
       addEdge(5, 6);
       addEdge(2, 9);
       addEdge(1, 6);
       addEdge(2, 5);
   }
}
