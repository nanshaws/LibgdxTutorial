​     在libgdx中，一个完整的游戏应该包含舞台和演员，大家看过电视剧吧，一样的原理。

# 概念及代码：

​     `Actor`是一个可视化对象，它可以代表游戏中的任何元素，比如角色、按钮或其他交互对象。每个`Actor`都有自己的属性，如位置、大小和绘图方法。`Stage`则是一个容纳和管理多个`Actor`的容器，它负责处理`Actor`的布局和事件分发。

​     下面是一个简单的LibGDX程序，展示了`Actor`和`Stage`的基本用法：

```
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MyGdxGame extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        Label label = new Label("Hello, LibGDX World!", style);
        label.setPosition(100, 100);
        stage.addActor(label);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
```

​      在这段代码中，我们首先创建了一个`Stage`对象。然后，我们创建了一个`Label`对象，这是`Actor`的一个子类，设置了它的样式和位置，并将其添加到`Stage`中。在`render`方法中，我们首先清除屏幕，然后调用`stage.act()`来更新`Stage`中所有`Actor`的状态，最后调用`stage.draw()`来绘制`Stage`和它的`Actor`们。

**这里面的label其实就是一个actor，咱们可以自己定义一个actor的人物类**

# 完整思想：

在人物类里面可以实现某个人物的具体逻辑，然后通过stage.addActor();方法添加到舞台上即可，然后分别调用 stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));      stage.draw();

```
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;

public class CharacterActor extends Actor {
    private Texture texture;

    public CharacterActor(Texture texture) {
        this.texture = texture;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        // 在这里添加人物的行为逻辑
    }
}
```

# 思考：

这样子大家在想想，键盘输入控制人物怎么搞？

这个输入在后面的章节会介绍，其实就是在实现InputAdapter类里面传一个actor，按键让actor移动即可。由于在主类中new actor，所以不存在actor一致的情况



