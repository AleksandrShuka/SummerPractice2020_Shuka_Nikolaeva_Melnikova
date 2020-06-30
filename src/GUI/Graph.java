package GUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import logger.Logs;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;


public class Graph extends mxGraph {

    Map<Integer, Object> cellsList;
    LinkedList<Integer> removedCells;
    int count;

    {
        removedCells = new LinkedList<>();
        cellsList = new HashMap<>();
        count = 0;
        setAllowDanglingEdges(false);
        setCellsMovable(false);
        setCellsEditable(false);
        setCellsEditable(false);
        setCellsResizable(false);

        getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_STROKECOLOR, "#000000");
        getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_STROKECOLOR, "#000000");
    }

    public void insertVertex() {
        if (removedCells.isEmpty()) {
            cellsList.put(count,insertVertex(getDefaultParent(), null, count, 0, 0, 40, 40));
            count++;
        }
        else {
            int index = removedCells.getFirst();
            removedCells.removeFirst();

            cellsList.put(index - 1, insertVertex(getDefaultParent(), null,
                    index, 0, 0, 40, 40));
        }
    }

    public void deleteVertex() {
        --count;
        removeCells(new Object[]{cellsList.get(count)});
        cellsList.remove(count);
    }

    public void deleteVertex(int index) {
        removeCells(new Object[]{cellsList.get(index)});
        cellsList.remove(index);
        removedCells.add(index);
    }

    public Object[] getAllVertex() {
        return cellsList.values().toArray();
    }
}
