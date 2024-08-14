package com.mygdx.game.wayFinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

class GridGraph implements IndexedGraph<Node> {
    private Array<Node> nodes;
    private int width, height;

    public GridGraph(int width, int height) {
        this.width = width;
        this.height = height;
        nodes = new Array<>(width * height);
        createNodes();
        createConnections();
    }

    private void createNodes() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                nodes.add(new Node(x, y, Math.random() > 0.2, y * width + x));
            }
        }
    }

    private void createConnections() {
        for (Node node : nodes) {
            if (!node.walkable) continue; // 不为不可步行的节点创建连接
            int x = node.x;
            int y = node.y;
            if (x > 0) node.addConnection(getNode(x - 1, y));
            if (x < width - 1) node.addConnection(getNode(x + 1, y));
            if (y > 0) node.addConnection(getNode(x, y - 1));
            if (y < height - 1) node.addConnection(getNode(x, y + 1));
        }
    }

    public Node getNode(int x, int y) {
        return nodes.get(y * width + x);
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    @Override
    public int getIndex(Node node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }
}