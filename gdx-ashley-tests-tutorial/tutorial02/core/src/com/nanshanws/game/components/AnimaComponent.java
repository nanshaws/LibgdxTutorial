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
