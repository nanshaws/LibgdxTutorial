package com.badlogic.gdx.ai.tests.mypath;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
public class PathFindingExample {
    public static void main(String[] args) {
        MapGraph graph = new MapGraph();

        // 创建节点
        Node nodeA = new Node(0, 0, 0);
        Node nodeB = new Node(1, 1, 0);
        Node nodeC = new Node(2, 2, 0);
        Node nodeD = new Node(3, 1, 1);
        Node nodeE = new Node(4, 2, 1);

        // 添加节点到图中
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);

        // 连接节点
        graph.connectNode(nodeA, nodeB, 1);
        graph.connectNode(nodeB, nodeC, 1);
        graph.connectNode(nodeA, nodeD, 1.5f);
        graph.connectNode(nodeD, nodeE, 1);
        graph.connectNode(nodeC, nodeE, 1);

        // 创建路径查找器
        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(graph);
        DefaultGraphPath<Node> path = new DefaultGraphPath<>();
        pathFinder.searchNodePath(nodeA, nodeE, new LocationHeuristic(), path);

        // 打印路径
        for (Node node : path) {
            System.out.println("Node: " + node.index);
        }
    }
}