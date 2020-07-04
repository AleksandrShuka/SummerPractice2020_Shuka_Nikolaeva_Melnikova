package GUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import logger.Logs;

import java.util.*;
import java.util.logging.Level;


public class Graph extends mxGraph {
    private final Map<Integer, String> savedCellStyles;
    private final List<Object> savedEdges;
    private final Map<Integer, Object> cells;
    private final SortedSet<Integer> removedCells;
    private int count;

    public Graph() {
        savedCellStyles = new HashMap<>();
        removedCells = new TreeSet<>();
        savedEdges = new ArrayList<>();
        cells = new HashMap<>();
        count = 0;

        setAllowDanglingEdges(false);
        setCellsMovable(false);
        setMultigraph(false);
        setCellsEditable(false);
        setCellsResizable(false);

        Map<String, Object> vertexStyle = getStylesheet().getDefaultVertexStyle();
        Map<String, Object> edgeStyle = getStylesheet().getDefaultEdgeStyle();

        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        vertexStyle.put(mxConstants.STYLE_FONTSIZE, 16);
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");

        mxConstants.DEFAULT_MARKERSIZE = 15;
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN);
    }

    public void insertVertex() {
        if (removedCells.isEmpty()) {
            cells.put(count, insertVertex(getDefaultParent(), null,
                    count, 0, 0, 40, 40));
            ++count;
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
    }

    public void paintVertex(int id, String color) {
        ((mxCell) cells.get(id)).setStyle(mxConstants.STYLE_FILLCOLOR + "=" + color);
    }

    public void paintEdge(int idSource, int idTarget, String color) {
        Object[] edges = getEdgesBetween(cells.get(idSource), cells.get(idTarget), true);
        for (Object edge : edges) {
            ((mxCell) edge).setStyle(mxConstants.STYLE_STROKECOLOR + "=" + color);
        }
    }

    public void clear() {
        removeCells(getAllVertex());
        count = 0;
        removedCells.clear();
        cells.clear();
    }

    public void save() {
        savedEdges.clear();
        savedCellStyles.clear();

        for (Object cell : cells.values()) {
            savedEdges.addAll(Arrays.asList(getOutgoingEdges(cell)));
            savedCellStyles.put((Integer) ((mxCell) cell).getValue(), ((mxCell) cell).getStyle());
        }
    }

    public void load() {
        for (Object cell : cells.values()) {
            removeCells(getOutgoingEdges(cell));
        }

        for (int key : savedCellStyles.keySet()) {
            ((mxCell) cells.get(key)).setStyle(savedCellStyles.get(key));
        }

        addCells(savedEdges.toArray());
    }
}
