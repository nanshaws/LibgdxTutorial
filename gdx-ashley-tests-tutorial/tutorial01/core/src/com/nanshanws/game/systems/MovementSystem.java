package com.nanshanws.game.systems;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.nanshanws.game.components.PositionComponent;
import com.nanshanws.game.components.VelocityComponent;

public class MovementSystem extends EntitySystem {
	public ImmutableArray<Entity> entities;
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
		System.out.println("移动引擎添加成功");
	}

	@Override
	public void removedFromEngine (Engine engine) {
		System.out.println("移动引擎移除成功");
		entities = null;
	}

	@Override
	public void update (float deltaTime) {
         //开始更改你的位置

		for (int i = 0; i < entities.size(); ++i) {
			Entity e = entities.get(i);

			PositionComponent p = pm.get(e);
			VelocityComponent m = vm.get(e);

			p.x += m.x * deltaTime;
			p.y += m.y * deltaTime;
			System.out.println("x="+p.x+"....y="+p.y);
		}

		System.out.println(entities.size() + "个实体已被执行，其位置都发生了改变");
	}
}
