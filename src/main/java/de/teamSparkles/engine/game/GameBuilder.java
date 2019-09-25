package de.teamSparkles.engine.game;

import java.util.function.Function;

public class GameBuilder implements Cloneable {
	
	public int width, height;
	public String title;
	public boolean fullscreen;
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
	
	public GameLoop build() {
		return new GameLoop(this);
	}
	
	@Override
	public GameBuilder clone() {
		try {
			return (GameBuilder) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
