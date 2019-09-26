package de.teamSparkles.jungleAdventure.postprocess.distortion;

import java.io.IOException;

import de.teamSparkles.engine.graphics.GlShader;
import de.teamSparkles.engine.graphics.GlTexture;

import static org.lwjgl.opengl.GL20.*;

public class ShaderPostprocessDistortion extends GlShader {
	
	public static ShaderPostprocessDistortion create() {
		try {
			return new ShaderPostprocessDistortion(
					readFromResource(ShaderPostprocessDistortion.class, "distortion.vert"),
					readFromResource(ShaderPostprocessDistortion.class, "distortion.frag")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final int uniformRandomOffset1;
	private final int uniformRandomOffset2;
	
	private ShaderPostprocessDistortion(String srcVertex, String srcFragment) {
		super(srcVertex, srcFragment);
		uniformRandomOffset1 = glGetUniformLocation(shaderProgram, "uniformRandomOffset1");
		uniformRandomOffset2 = glGetUniformLocation(shaderProgram, "uniformRandomOffset2");
	}
	
	public void draw(GlTexture color, GlTexture distortionDirection, GlTexture distortionRandom, float[] randomOffset1, float[] randomOffset2) {
		bind();
		glUniform2f(uniformRandomOffset1, randomOffset1[0], randomOffset1[1]);
		glUniform2f(uniformRandomOffset2, randomOffset2[0], randomOffset2[1]);
		color.bind(0);
		distortionDirection.bind(1);
		distortionRandom.bind(2);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		unbind();
	}
	
}
