package de.pmgroup.game;

import de.pmgroup.game.graphics.AbstractGame;
import de.pmgroup.game.graphics.GameBuilder;
import de.pmgroup.game.graphics.GlModel;
import de.pmgroup.game.graphics.GlTexture;

import static org.lwjgl.opengl.GL11.*;

public class TextureGame extends AbstractGame {
	
	public static void main(String[] args) {
		new TextureGame(
				new GameBuilder()
						.setWidth(1080)
						.setHeight(1080)
						.setTitle("Triangle")
						.setFullscreen(false)
		).run();
	}
	
	public TextureGame(GameBuilder b) {
		super(b);
	}
	
	private GlTexture textureEmoji;
	private GlModel modelEmoji;
	private GlModel modelTriangle;
	
	@Override
	public void init() {
		textureEmoji = GlTexture.load(TextureGame.class.getResourceAsStream("emoji.png"));
		modelEmoji = new GlModel(
				GL_QUADS,
				new float[]{
						-1, -1,
						-1, 1,
						1, 1,
						1, -1,
				},
				new float[]{
						0, 1,
						0, 0,
						1, 0,
						1, 1,
				},
				textureEmoji
		);
		modelTriangle = new GlModel(
				GL_TRIANGLES,
				new float[]{
						1, 0, 0
				},
				new float[]{
						0.5f, -0.5f,
						-0.5f, -0.5f,
						0, -1,
						0.5f, 0.5f,
						-0.5f, 0.5f,
						0, 1,
				}
		);
	}
	
	@Override
	public void release() {
		modelTriangle.free();
		modelEmoji.free();
		textureEmoji.free();
	}
	
	@Override
	public void render() {
		modelEmoji.draw();
		
		glPushMatrix();
		glRotatef(System.nanoTime() / 1_000_000_000f * 360f / 4, 0, 0, 1);
		modelTriangle.draw();
		glPopMatrix();
	}
}
