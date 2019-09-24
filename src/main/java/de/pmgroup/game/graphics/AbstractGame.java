package de.pmgroup.game.graphics;


import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractGame implements Runnable {
	
	private final GameBuilder b;
	
	private long windowPointer;
	
	public AbstractGame(GameBuilder b) {
		this.b = b.clone();
	}
	
	@Override
	public void run() {
		glfwInit();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_FALSE);
		windowPointer = glfwCreateWindow(b.width, b.height, b.title, b.fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		glfwMakeContextCurrent(windowPointer);
		GL.createCapabilities(false);
		init();
		
		while (!glfwWindowShouldClose(windowPointer)) {
			update();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			render();
			
			glfwSwapBuffers(windowPointer);
			glfwPollEvents();
		}
		
		release();
		glfwDestroyWindow(windowPointer);
		windowPointer = 0;
		glfwTerminate();
	}
	
	public void init() {
	
	}
	
	public void release() {
	
	}
	
	public void update() {
	
	}
	
	public abstract void render();
	
}
