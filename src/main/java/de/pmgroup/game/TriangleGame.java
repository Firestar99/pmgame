package de.pmgroup.game;

import de.pmgroup.game.graphics.AbstractGame;
import de.pmgroup.game.graphics.GameBuilder;

import static org.lwjgl.opengl.GL11.*;

public class TriangleGame extends AbstractGame {
	
	public static void main(String[] args) {
		new TriangleGame(
				new GameBuilder()
						.setWidth(1980)
						.setHeight(1980)
						.setTitle("Triangle")
						.setFullscreen(false)
		).run();
	}
	
	public TriangleGame(GameBuilder b) {
		super(b);
	}
	
	@Override
	public void render() {
		glPushMatrix();
		glRotatef(System.nanoTime() / 1_000_000_000f * 360f / 4, 0, 0, 1);
		
		glBegin(GL_TRIANGLES);
		glVertex2d(-0.5f, -0.5f);
		glVertex2d(0.5f, -0.5f);
		glVertex2d(0, 0.5f);
		glEnd();
		
		glPopMatrix();
	}
}
