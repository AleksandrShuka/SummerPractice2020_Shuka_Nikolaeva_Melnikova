package GUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class Graph extends mxGraph {
    private final Map<Integer, Object> cells;
    private final SortedSet<Integer> removedCells;
    private int count;

    public Graph() {
        removedCells = new TreeSet<>();
        cells = new HashMap<>();
        init();
    }

    private void init() {
        count = 0;

        setAllowDanglingEdges(false);
        setCellsMovable(false);
        setMultigraph(false);
        setCellsEditable(false);
        setCellsResizable(false);

        Map<String, Object> vertexStyle = getStylesheet().getDefaultVertexStyle();
        Map<String, Object> edgeStyle = getStylesheet().getDefaultEdgeStyle();

        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");

        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
    }

    public void insertVertex() {
        if (removedCells.isEmpty()) {
            cells.put(count, insertVertex(getDefaultParent(), null,
                    count, 0, 0, 40, 40));
            count++;
        } else {
            int index = removedCells.first();
            removedCells.remove(index);

            cells.put(index, insertVertex(getDefaultParent(), null,
                    index, 0, 0, 40, 40));
        }
    }

    public void deleteVertex(int index) {
        removeCells(new Object[]{cells.get(index)});
        cells.remove(index);
        removedCells.add(index);
    }

    public Object[] getAllVertex() {
        return cells.values().toArray();
    }

    public void transpose() {
        for (Object vertex : getAllVertex()) {
            for (Object edge : getOutgoingEdges(vertex)) {
                mxCell mxEdge = ((mxCell) edge);

                mxICell target = mxEdge.getTarget();
                mxEdge.setTarget(mxEdge.getSource());
                mxEdge.setSource(target);
            }
        }

        refresh();
        repaint();
    }

    public void paintVertex(int id, String color) {
        ((mxCell) cells.get(id)).setStyle("fillColor=" + color);

        refresh();
        repaint();
    }

    public void clear() {
        removeCells(getAllVertex());

        count = 0;

        removedCells.clear();
        cells.clear();
    }
}
