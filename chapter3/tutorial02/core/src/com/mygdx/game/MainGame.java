package com.mygdx.game;
		
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
	
/**
 * 游戏主程序的启动入口类
 */
public class MainGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	
	private Texture walkSheetTexture;
	
	// 行走动画
	private Animation walkAnimation;
	
	private TextureRegion currentFrame;
	
	// 状态时间, 渲染时间步 delta 的累加值
	private float stateTime;
	private int loop=4;
	@Override
	public void create() {
		// 创建 Batch
		batch = new SpriteBatch();
		
		// 创建纹理, animation_sheet.png 图片的宽高为 256 * 256, 由 5 行 6列 个不同状态的小人单元格组成
		walkSheetTexture = new Texture(Gdx.files.internal("t1.png"));
		
		int frameRows = 5;	// 小人单元格的行数
		int frameCols = 6;	// 小人单元格的列数
		
		int perCellWidth = walkSheetTexture.getWidth() / frameCols;		// 计算每一个小人单元格的宽度
		int perCellHeight = walkSheetTexture.getHeight() / frameRows;	// 计算每一个小人单元格的高度
		
		// 按照指定的宽高作为一个单元格分割大图纹理, 分割后的结果为一个 5 * 6 的纹理区域二维数组, 数组中的元素是分割出来的小人单元格
		TextureRegion[][] cellRegions = TextureRegion.split(walkSheetTexture, perCellWidth, perCellHeight);
		
		// 把二维数组变为一维数组, 因为 Animation 只能接收一维数组作为关键帧序列, 数组中的一个元素（小人单元格的纹理区域）表示一个关键帧
		TextureRegion[] walkFrames = new TextureRegion[frameRows * frameCols];
		int index = 0;
		for (int row = 0; row < frameRows; row++) {
			for (int col = 0; col < frameCols; col++) {
				walkFrames[index++] = cellRegions[row][col];
			}
		}
		
		// 使用关键帧（纹理区域）数组 walkFrames 创建一个动画实例, 每一帧（一个小人单元格/纹理区域）播放 0.05 秒
		walkAnimation = new Animation(0.05F, walkFrames);


		/*
		 * 设置播放模式:
		 * 
		 * Animation.PlayMode.NORMAL: 正常播放一次（默认）
		 * Animation.PlayMode.REVERSED: 倒序播放一次
		 * 
		 * Animation.PlayMode.LOOP: 正常循环播放
		 * Animation.PlayMode.LOOP_REVERSED: 倒序循环播放
		 * 
		 * Animation.PlayMode.LOOP_RANDOM: 随机循环播放
		 * Animation.PlayMode.LOOP_PINGPONG: 开关式（先正序再倒序）循环播放
		 */
		walkAnimation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
	}
	
	@Override
	public void render() {
		// 黑色清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // 累加时间步（stateTime 也可表示游戏的运行时间）
        stateTime += Gdx.graphics.getDeltaTime();
        
        // 根据当前 播放模式 获取当前关键帧, 就是在 stateTime 这个时刻应该播放哪一帧
        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime);
        int loopNumber= (int) (stateTime/walkAnimation.getAnimationDuration());

        batch.begin();
        boolean flag=false;
		if (loopNumber<=loop){
			flag=true;
			batch.draw(currentFrame, 50, 100);
		}
        // 绘制当前关键帧

        
        batch.end();
	}
	
	@Override
	public void dispose() {
		// 释放资源
		if (walkSheetTexture != null) {
			walkSheetTexture.dispose();
		}
		if (batch != null) {
			batch.dispose();
		}
	}

}
