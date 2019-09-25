package de.teamSparkles.engine.game;


import org.lwjgl.opengl.GL;

import static de.teamSparkles.engine.graphics.GlErrorCheck.checkError;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop implements Runnable {
	
	private final GameBuilder b;
	
	public GameLoop(GameBuilder b) {
		this.b = b.clone();
	}
	
	@Override
	public void run() {
		glfwInit();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
		long windowPointer = glfwCreateWindow(b.width, b.height, b.title, b.fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (windowPointer == 0)
			throw new RuntimeException("Could not create Window! glfwCreateWindow returned null");
		glfwMakeContextCurrent(windowPointer);
		GL.createCapabilities(false);
		
		Game game = b.starter.apply(windowPointer);
		while (!glfwWindowShouldClose(windowPointer)) {
			game.update();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
	
}
