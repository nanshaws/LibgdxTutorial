package com.mygdx.game.wayFinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

public class PathFinderExample {
    private GridGraph gridGraph;
    private IndexedAStarPathFinder<Node> pathFinder;
    private Heuristic<Node> heuristic;

    public PathFinderExample(GridGraph gridGraph) {
        this.gridGraph = gridGraph;
        this.pathFinder = new IndexedAStarPathFinder<>(gridGraph);

        // 使用曼哈顿距离作为启发式函数，可以更换为其他合适的启发式算法
        this.heuristic = (node, endNode) -> Math.abs(node.x - endNode.x) + Math.abs(node.y - endNode.y);
    }

    public GraphPath<Node> findPath(Node startNode, Node endNode) {
        GraphPath<Node> path = new DefaultGraphPath<>();
        pathFinder.searchNodePath(startNode, endNode, heuristic, path);
        return path;
    }
}