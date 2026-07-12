package com.jelo.api.menu.content;

/**
 * Represents object as position of a content.
 *
 * @param x The horizontal position
 * @param y The vertical position
 */
public record Position(int x, int y) {

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position(int x1, int y1))) return false;
        return x == x1 && y == y1;
    }
}
