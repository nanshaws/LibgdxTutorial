package com.mygdx.game.wayFinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PathfindingDemo extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private GridGraph gridGraph;
    private PathFinderExample pathFinderExample;
    private GraphPath<Node> path;

    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    private static final int CELL_SIZE = 40;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE, camera);
        viewport.apply();

        gridGraph = new GridGraph(GRID_WIDTH, GRID_HEIGHT);
        pathFinderExample = new PathFinderExample(gridGraph);

        Node startNode = gridGraph.getNode(0, 0);
        Node endNode = gridGraph.getNode(GRID_WIDTH - 1, GRID_HEIGHT - 1);
        path = pathFinderExample.findPath(startNode, endNode);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 1);
                shapeRenderer.rect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        shapeRenderer.setColor(0, 1, 0, 1);
        for (Node node : path) {
            shapeRenderer.rect(node.x * CELL_SIZE, node.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}