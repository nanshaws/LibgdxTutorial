package com.mygdx.game.tutorial01.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MapUtil extends Sprite {
    TiledMap load;
    Color bgcolor;
    public float playx;
    public float playy;

    public TiledMap getTmxMapLoader(){
        load = new TmxMapLoader().load("map/mariomap/map05.tmx");
        bgcolor=new Color();
        return load;
    }

    public void createStatic(TiledMap map, World world, FixtureDef fixtureDef, BodyDef bodyDef) {
        MapLayer mapLayer = map.getLayers().get("objects");
        MapObjects objects = mapLayer.getObjects();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Array<RectangleMapObject> rmos = objects.getByType(RectangleMapObject.class);
        for (RectangleMapObject rmo : rmos) {
            if (rmo.getName() != null && rmo.getName().equals("mario")) {
                playx = rmo.getRectangle().x;
                playy = rmo.getRectangle().y;
                continue;
            }
            Rectangle rect = rmo.getRectangle();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = shape;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
            world.createBody(bodyDef).createFixture(fixtureDef);
        }
    }


    public void setColor(Color color) {
        bgcolor.set(color);
    }

    public Color getColor() {
        return bgcolor;
    }


}

