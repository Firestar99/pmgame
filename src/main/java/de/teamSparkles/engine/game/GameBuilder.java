package de.teamSparkles.engine.game;

import org.lwjgl.opengl.GLDebugMessageCallbackI;

import java.util.function.Function;

public class GameBuilder implements Cloneable {
	
	public int width = 1920, height = 1080;
	public String title = "Unnamed Game";
	public boolean fullscreen = false;
	public int openglVersionMayor = 2, openglVersionMinor = 1;
	public boolean openglForwardCompatible = false;
	public GLDebugMessageCallbackI openglDebugCallback;
	public Function<Long, Game> starter;
	
	public GameBuilder() {
	}
	
	public GameBuilder setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public GameBuilder setHeight(int height) {
		this.height = height;
		return this;
	}
	
	public GameBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public GameBuilder setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		return this;
	}
	
	public GameBuilder setStarter(Function<Long, Game> starter) {
		this.starter = starter;
		return this;
	}
	
	public GameBuilder setOpenglVersion(int versionMayor, int versionMinor, boolean forwardCompatible) {
		this.openglVersionMayor = versionMayor;
		this.openglVersionMinor = versionMinor;
		this.openglForwardCompatible = forwardCompatible;
		return this;
	}
	
	public GameBuilder setOpenglDebugCallback(GLDebugMessageCallbackI openglDebugCallback) {
		this.openglDebugCallback = openglDebugCallback;
		return this;
	}
	
	public GameLoop build() {
		if (starter == null)
			throw new IllegalArgumentException("starter is null");
		return new GameLoop(this);
	}
}
