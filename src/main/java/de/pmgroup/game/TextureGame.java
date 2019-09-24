package de.pmgroup.game;

import de.pmgroup.game.graphics.AbstractGame;
import de.pmgroup.game.graphics.GameBuilder;
import de.pmgroup.game.graphics.Model;
import de.pmgroup.game.graphics.Texture;

import static org.lwjgl.opengl.GL11.*;

public class TextureGame extends AbstractGame {
	
	public static void main(String[] args) {
		new TextureGame(
				new GameBuilder()
						.setWidth(1980)
						.setHeight(1980)
						.setTitle("Triangle")
						.setFullscreen(false)
		).run();
	}
	
	public TextureGame(GameBuilder b) {
		super(b);
	}
	
	private Texture textureEmoji;
	private Model modelEmoji;
	private Model modelTriangle;
	
	@Override
	public void init() {
		textureEmoji = Texture.load(TextureGame.class.getResourceAsStream("emoji.png"));
		modelEmoji = new Model(
				GL_QUADS,
				new float[]{
						-1, -1,
						-1, 1,
						1, 1,
						1, -1,
				},
				new float[]{
						0, 0,
						0, 1,
						1, 1,
						1, 0,
				},
				textureEmoji
		);
		modelTriangle = new Model(
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
