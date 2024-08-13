package com.mygdx.game.steering;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SteeringActor extends Sprite implements Steerable<Vector2> {
    private Vector2 position;
    private Vector2 linearVelocity;
    private float angularVelocity;
    private float boundingRadius;
    private boolean tagged;
    
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;

    private boolean independentFacing;

    private SteeringBehavior<Vector2> steeringBehavior;
    private SteeringAcceleration<Vector2> steeringOutput;

    public SteeringActor(Texture texture) {
        super(texture);
        this.position = new Vector2(getX(), getY());
        this.linearVelocity = new Vector2();
        this.boundingRadius = Math.max(getWidth(), getHeight()) / 2;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
    }

    public SteeringActor(Texture texture, Vector2 initialPosition) {
        this(texture);
        this.position.set(initialPosition);
        setPosition(initialPosition.x, initialPosition.y);
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    public void applySteering(float deltaTime) {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringOutput);

            // Apply acceleration to velocity
            linearVelocity.mulAdd(steeringOutput.linear, deltaTime).limit(getMaxLinearSpeed());

            // Update position and angle
            position.mulAdd(linearVelocity, deltaTime);
            setPosition(position.x, position.y);

            if (independentFacing) {
                float newOrientation = vectorToAngle(linearVelocity);
                setOrientation(newOrientation);
                setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getOrientation() {
        return (float) Math.toRadians(getRotation());
    }

    @Override
    public void setOrientation(float orientation) {
        setRotation((float) Math.toDegrees(orientation));
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.1f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        // no-op
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    public Vector2 newVector() {
        return new Vector2();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null; // Implement if needed
    }
}