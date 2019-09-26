package de.teamSparkles.engine.game;


import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

import java.util.function.Function;

import static de.teamSparkles.engine.graphics.GlErrorCheck.checkError;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DONT_CARE;
import static org.lwjgl.opengl.GL43.*;

public class GameLoop implements Runnable {
	
	public final int width, height;
	public final String title;
	public final boolean fullscreen;
	public final int openglVersionMayor, openglVersionMinor;
	public final boolean openglForwardCompatible;
	public final GLDebugMessageCallbackI openglDebugCallback;
	public final Function<GameLoop, Game> starter;
	
	private long windowPointer;
	
	public GameLoop(GameBuilder b) {
		this.width = b.width;
		this.height = b.height;
		this.title = b.title;
		this.fullscreen = b.fullscreen;
		this.openglVersionMayor = b.openglVersionMayor;
		this.openglVersionMinor = b.openglVersionMinor;
		this.openglForwardCompatible = b.openglForwardCompatible;
		this.openglDebugCallback = b.openglDebugCallback;
		this.starter = b.starter;
	}
	
	@Override
	public void run() {
		glfwInit();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, openglVersionMayor);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, openglVersionMinor);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, toGlfwBoolean(openglForwardCompatible));
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, toGlfwBoolean(openglDebugCallback != null));
		windowPointer = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (windowPointer == 0)
			throw new RuntimeException("Could not create Window! glfwCreateWindow returned null");
		glfwMakeContextCurrent(windowPointer);
		GL.createCapabilities(openglForwardCompatible);
		
		if (openglDebugCallback != null) {
			nglDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DONT_CARE, 0, 0, true);
			glDebugMessageCallback(openglDebugCallback, 0);
		}
		
		Game game = starter.apply(this);
		while (!glfwWindowShouldClose(windowPointer)) {
			game.update();
			checkError("pre-render");
			game.render();
			checkError("post-render");
			
			
			glfwSwapBuffers(windowPointer);
			glfwPollEvents();
		}
		game.release();
		
		glfwDestroyWindow(windowPointer);
		glfwTerminate();
	}
	
	private static int toGlfwBoolean(boolean b) {
		return b ? GLFW_TRUE : GLFW_FALSE;
	}
	
	public long windowPointer() {
		return windowPointer;
	}
}
