package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.cyl.screen.BaseScreen;

public class StartGameScreen extends BaseScreen {
    private Skin skin;
    private Game game;
    private Stage stage;

    public StartGameScreen(int width, int height, Game game) {
        super(width, height);
        this.game = game;
    }


    private void handInput() {

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("screen/skin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "playerGame");
        image.setName("background");
        stack.addActor(image);

        Table table1 = new Table();

        image = new Image(skin, "wxchat_icon (9) (1)");
        image.setName("logo");
        table1.add(image).padBottom(5.0f);

        table1.row();
        TextButton textButton = new TextButton("Play", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new TextureAltaScreen(800,600));

            }
        });
        textButton.setName("PlayButton");
        textButton.padBottom(5.0f);
        table1.add(textButton).padBottom(5.0f);

        table1.row();
        textButton = new TextButton("continue", skin);
        textButton.setName("GoOnButton");
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("GoOn");
            }
        });
        textButton.padBottom(5.0f);
        table1.add(textButton).padBottom(5.0f);

        table1.row();
        textButton = new TextButton("Options", skin);
        textButton.setName("OptionsButton");
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Settings");
            }
        });
        table1.add(textButton).padBottom(5.0f);

        table1.row();
        textButton = new TextButton("Exit", skin);
        textButton.setName("ExitButton");
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.out.println("Exit");
            }
        });
        table1.add(textButton).padBottom(5.0f);
        stack.addActor(table1);
        table.add(stack);
        stage.addActor(table);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void renderChild(float dateTime) {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        handInput();
    }
}
