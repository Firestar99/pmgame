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
	
	private final int uniformRandomOffset;
	
	private ShaderPostprocessDistortion(String srcVertex, String srcFragment) {
		super(srcVertex, srcFragment);
		uniformRandomOffset = glGetUniformLocation(shaderProgram, "randomOffset");
	}
	
	public void draw(GlTexture color, GlTexture distortionDirection, GlTexture distortionHeight, GlTexture distortionRandom, float[] randomOffset) {
		bind();
		glUniform2f(uniformRandomOffset, randomOffset[0], randomOffset[1]);
		color.bind(0);
		distortionDirection.bind(1);
		distortionHeight.bind(2);
		distortionRandom.bind(3);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		unbind();
	}
	
}
