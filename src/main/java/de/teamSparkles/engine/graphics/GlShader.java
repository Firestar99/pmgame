package de.teamSparkles.engine.graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public class GlShader {
	
	public final int shaderProgram;
	
	public GlShader(String srcVertex, String srcFragment) {
		int vertex = createShader(GL_VERTEX_SHADER, srcVertex);
		int fragment = createShader(GL_FRAGMENT_SHADER, srcFragment);
		
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertex);
		glAttachShader(shaderProgram, fragment);
		glLinkProgram(shaderProgram);
		String log = glGetProgramInfoLog(shaderProgram);
		if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == 0)
			throw new RuntimeException("Program link failed: " + log);
		else
			System.out.println("Program link log: \n" + log);
		
		glDeleteShader(vertex);
		glDeleteShader(fragment);
	}
	
	private static int createShader(int type, String src) {
		int shader = glCreateShader(type);
		glShaderSource(shader, src);
		glCompileShader(shader);
		String log = glGetShaderInfoLog(shader);
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
			throw new RuntimeException("Shader compile failed: \n" + log);
		else
			System.out.println("Shader compile log: \n" + log);
		return shader;
	}
	
	public void bind() {
		glUseProgram(shaderProgram);
	}
	
	public static void unbind() {
		glUseProgram(0);
	}
	
	protected static String readFromResource(Class relative, String name) throws IOException {
		InputStream source = relative.getResourceAsStream(name);
		if (source == null)
			throw new FileNotFoundException(relative.getPackageName() + "." + name);
		return new String(source.readAllBytes(), StandardCharsets.UTF_8);
	}
	
	public void release() {
		glDeleteProgram(shaderProgram);
	}
}
