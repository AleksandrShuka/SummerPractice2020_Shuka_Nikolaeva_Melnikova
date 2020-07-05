package GUI;

import java.util.Objects;

/**
 * Класс-контейнер, представляющий собой пару объектов.
 */

public class Pair<T, U> {
    public final T first;
    public final U second;

    /**
     * Конструктор, принимающий два объекта {@code first} и {@code second}.
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Перегруженный метод {@code equals}.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    /**
     * Перегруженный метод {@code hashCode}.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
