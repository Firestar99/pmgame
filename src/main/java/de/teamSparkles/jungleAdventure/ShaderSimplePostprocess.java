package de.teamSparkles.jungleAdventure;

import java.io.IOException;

import de.teamSparkles.engine.graphics.GlShader;
import de.teamSparkles.engine.graphics.GlTexture;

import static org.lwjgl.opengl.GL20.*;

public class ShaderSimplePostprocess extends GlShader {
	
	public static ShaderSimplePostprocess create() {
		try {
			return new ShaderSimplePostprocess(
					readFromResource(ShaderSimplePostprocess.class, "simplePostprocess.vert"),
					readFromResource(ShaderSimplePostprocess.class, "simplePostprocess.frag")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private ShaderSimplePostprocess(String srcVertex, String srcFragment) {
		super(srcVertex, srcFragment);
	}
	
	public void draw(GlTexture tex) {
		bind();
		tex.bind(0);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		unbind();
	}
	
}
