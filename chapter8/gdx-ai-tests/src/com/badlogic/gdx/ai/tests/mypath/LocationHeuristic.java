package com.badlogic.gdx.ai.tests.mypath;

import com.badlogic.gdx.ai.pfa.Heuristic;

class LocationHeuristic implements Heuristic<Node> {
    @Override
    public float estimate(Node node, Node endNode) {
        // 使用欧几里得距离作为启发式估算
        return (float) Math.sqrt(Math.pow(node.x - endNode.x, 2) + Math.pow(node.y - endNode.y, 2));
    }
}