package com.badlogic.gdx.ai.tests.mypath;

import com.badlogic.gdx.ai.pfa.Connection;

class MapConnection implements Connection<Node> {
    private final Node fromNode;
    private final Node toNode;
    private final float cost;

    public MapConnection(Node fromNode, Node toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}