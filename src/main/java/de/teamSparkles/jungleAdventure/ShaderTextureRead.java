package de.teamSparkles.jungleAdventure;

import org.lwjgl.system.MemoryStack;

import java.io.IOException;

import de.teamSparkles.engine.graphics.GlShader;
import de.teamSparkles.engine.graphics.GlTexture;

import static org.lwjgl.opengl.GL20.*;

public class ShaderTextureRead extends GlShader {
	
	public static ShaderTextureRead create() {
		try {
			return new ShaderTextureRead(
					readFromResource(ShaderTextureRead.class, "textureRead.vert"),
					readFromResource(ShaderTextureRead.class, "textureRead.frag")
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final int uniformInputs;
	
	private ShaderTextureRead(String srcVertex, String srcFragment) {
		super(srcVertex, srcFragment);
		uniformInputs = glGetUniformLocation(shaderProgram, "inputs");
	}
	
	public void use(float[] input, GlTexture tex) {
		bind();
		
		try {
			MemoryStack.stackPush();
			glUniform4fv(uniformInputs, MemoryStack.stackFloats(input));
		} finally {
			MemoryStack.stackPop();
		}
		tex.bind(0);
	}
	
}
