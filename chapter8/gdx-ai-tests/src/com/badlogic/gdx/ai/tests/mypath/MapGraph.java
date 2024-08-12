package com.badlogic.gdx.ai.tests.mypath;

import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.ai.pfa.Connection;

class MapGraph implements IndexedGraph<Node> {
    Array<Node> nodes = new Array<>();
    Array<Array<Connection<Node>>> connections = new Array<>();

    public void addNode(Node node) {
        nodes.add(node);
        connections.add(new Array<>());
    }

    public void connectNode(Node fromNode, Node toNode, float cost) {
        connections.get(fromNode.index).add(new MapConnection(fromNode, toNode, cost));
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return connections.get(fromNode.index);
    }
}
