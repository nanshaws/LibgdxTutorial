接下来，我将讲解如何将ashley和libgdx合理的融合起来

我将以之前的tutorial01项目为基础，在此项目添加其他的，随后会以文件夹tutorial02的形式上传github

# 第一步：创建要画的组件

```
package com.nanshanws.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimaComponent  implements Component {
    public Animation<TextureRegion> currentAnimation;
    public float stateTime;

    public AnimaComponent(Animation<TextureRegion> currentAnimation, float stateTime) {
        this.currentAnimation = currentAnimation;
        this.stateTime = stateTime;
    }
}
```

# 第二步：创建动画系统

```
package com.nanshanws.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nanshanws.game.components.AnimaComponent;



public class AnimationSystem extends EntitySystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ImmutableArray<Entity> entities;

    public AnimationSystem(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
    }


    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimaComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {

        for (Entity entity : entities) {
            AnimaComponent animationComponent = ComponentMapper.getFor(AnimaComponent.class).get(entity);
            if (animationComponent.currentAnimation != null) {
                animationComponent.stateTime += deltaTime;
                TextureRegion keyFrame = animationComponent.currentAnimation.getKeyFrame(animationComponent.stateTime,true);
                batch.begin();
                batch.draw(keyFrame, Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                batch.end();
            }
        }
    }
}
```

# 第三步：与libgdx交互

```
package com.nanshanws.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nanshanws.game.components.AnimaComponent;
import com.nanshanws.game.systems.AnimationSystem;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Engine entityEngine;
	private AnimationSystem animationSystem;
	private Animation<TextureRegion> currentAnimation;
	private AnimaComponent animaComponent;
	private Animation animation;
	private Entity entity;
	private TextureAtlas textureAtlas;
	@Override
	public void create () {

		textureAtlas = new TextureAtlas(Gdx.files.internal("t23.atlas"));
		Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();
		animation = new Animation<>(0.02f, frames);

		TextureAtlas.AtlasRegion[] atlasRegions = frames.toArray(TextureAtlas.AtlasRegion.class);
        // 创建 Animation 对象并设置到 currentAnimation
		currentAnimation = new Animation<>(0.02f, atlasRegions);
		// 初始化SpriteBatch和OrthographicCamera
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// 初始化EntityEngine和AnimationSystem
		entityEngine = new Engine();
		animationSystem = new AnimationSystem(batch, camera);
		entityEngine.addSystem(animationSystem);

		animaComponent=new AnimaComponent(currentAnimation,0);

		//创建实体
		entity = entityEngine.createEntity();
		entity.add(animaComponent);
		entityEngine.addEntity(entity);
        
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		entityEngine.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}
}

```

# 想说的话

这样子做的话，MyGdxGame类的代码太繁杂了，可以搞个类，将下面属性全放到一个animation的类中

```
	private Animation<TextureRegion> currentAnimation;
	private Animation animation;
	private TextureAtlas textureAtlas;
```

在提供访问的方法，即可
