package GUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import java.util.*;

/**
 * Класс, представляющий собой граф.
 * Наследуется от {@code mxGraph}.
 * <p>
 * Содержит в себе:
 *
 * @value savedCellStyles - словарь для сохранения исходных стилей вершин
 * @value savedEdges - словарь для сохранения исходных ребер
 * @value cells - словарь вершин
 * @value removedCells - множество удаленных вершин
 * <p>
 * Содержит в себе счетчик вершин {@code count}
 * @see mxGraph
 */

public class Graph extends mxGraph {
    private final Map<Integer, String> savedCellStyles;
    private final Map<Object, Object> savedEdges;
    private final Map<Integer, Object> cells;
    private final SortedSet<Integer> removedCells;

    /**
     * Счетчик вершин.
     */
    private int count;

    /**
     * Конструктор графа.
     * Инициализирует переменные, устанавливает свойства и задает стиль вершин и рёбер по умолчанию.
     */
    public Graph() {
        savedCellStyles = new HashMap<>();
        removedCells = new TreeSet<>();
        savedEdges = new HashMap<>();
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

    /**
     * Метод для добавления вершины в граф.
     * Добавление происходит следующим образом: если множество удаленных вершин пусто,
     * то добавляется новая вершина со значением {@code id} count, иначе добавляется первая среди удаленных.
     */
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

    /**
     * Метод для удаления вершины по индексу {@code index}.
     *
     * @param index - индекс вершины в графе.
     */
    public void deleteVertex(int index) {
        removeCells(new Object[]{cells.get(index)});
        cells.remove(index);
        removedCells.add(index);
    }

    /**
     * Метод для возвращения массива всех вершин в графе.
     *
     * @return массив всех вершин графа.
     */
    public Object[] getAllVertex() {
        return cells.values().toArray();
    }

    /**
     * Метод, транспонирующий исходный граф.
     * Изменяет направление всех ребер графа на противоположное.
     */
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

    /**
     * Метод для раскрашивания вершины с индексом {@code id} в цвет {@code color}
     *
     * @param id    - индекс вершины
     * @param color - строковое представление цвета для окрашивания вершины.
     */
    public void paintVertex(int id, String color) {
        ((mxCell) cells.get(id)).setStyle(mxConstants.STYLE_FILLCOLOR + "=" + color);
    }

    /**
     * Метод для раскрашивания ребра с индексом начальной вершины {@code idSource} и конечной
     * вершины {@code idTarget}. Ребро окрашивается в цвет {@code color}.
     *
     * @param idSource - индекс начальной вершины ребра
     * @param idTarget - индекс конечной вершины ребра
     * @param color    - строковое представление цвета для окрашивания ребра
     */
    public void paintEdge(int idSource, int idTarget, String color) {
        Object[] edges = getEdgesBetween(cells.get(idSource), cells.get(idTarget), true);
        for (Object edge : edges) {
            ((mxCell) edge).setStyle(mxConstants.STYLE_STROKECOLOR + "=" + color);
        }
    }

    /**
     * Метод для раскрашивания всех вершин в цвет, заданный по умолчанию.
     */
    public void paintDefault() {
        for (Object cell : cells.values()) {
            for (Map.Entry<String, Object> entry : getStylesheet().getDefaultVertexStyle().entrySet()) {
                ((mxCell) cell).setStyle(entry.getKey() + "=" + entry.getValue());
            }
        }
    }

    /**
     * Метод для отчистки графа. Удаляет все вершины графа.
     */
    public void clear() {
        removeCells(getAllVertex());
        count = 0;
        removedCells.clear();
        cells.clear();
    }

    /**
     * Метод, реализующий сохранение графа. В том числе: ребра графа и стиль вершин.
     */
    public void save() {
        savedEdges.clear();
        savedCellStyles.clear();

        for (Object cell : cells.values()) {
            for (Object edge : getOutgoingEdges(cell)) {
                savedEdges.put(cell, ((mxCell) edge).getTarget());
            }
            savedCellStyles.put((Integer) ((mxCell) cell).getValue(), ((mxCell) cell).getStyle());
        }
    }

    /**
     * Метод для восстановления исходного графа.
     *
     * @see Graph#save()
     */
    public void load() {
        for (Object cell : cells.values()) {
            removeCells(getOutgoingEdges(cell));
        }

        for (int key : savedCellStyles.keySet()) {
            ((mxCell) cells.get(key)).setStyle(savedCellStyles.get(key));
        }

        for (Object key : savedEdges.keySet()) {
            insertEdge(getDefaultParent(), null, null, key,
                    savedEdges.get(key));
        }
    }

    /**
     * Метод для создания графа по известному количеству вершин {@code count} и множеству
     * рёбер {@code edgeSet}.
     *
     * @param count   - количество вершин
     * @param edgeSet - множество рёбер
     */
    public void createGraph(int count, Set<Pair<Integer, Integer>> edgeSet) {
        clear();
        for (int i = 0; i < count; ++i) {
            insertVertex();
        }

        for (Pair<Integer, Integer> elem : edgeSet) {
            insertEdge(getDefaultParent(), null, null, cells.get(elem.first),
                    cells.get(elem.second));
        }
    }
}
