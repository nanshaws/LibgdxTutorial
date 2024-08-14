package com.mygdx.game.wayFinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;

class Node {
    int x, y;
    boolean walkable;
    private int index;
    private Array<Connection<Node>> connections;

    public Node(int x, int y, boolean walkable, int index) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.index = index;
        this.connections = new Array<>();
    }

    public void addConnection(Node target) {
        connections.add(new DefaultConnection<>(this, target));
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public int getIndex() {
        return index;
    }
}