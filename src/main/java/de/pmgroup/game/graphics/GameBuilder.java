package de.pmgroup.game.graphics;

public class GameBuilder implements Cloneable {
	
	public int width, height;
	public String title;
	public boolean fullscreen;
	
	public GameBuilder() {
	}
	
	public GameBuilder(int width, int height, String title, boolean fullscreen) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.fullscreen = fullscreen;
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
	
	@Override
	public GameBuilder clone() {
		try {
			return (GameBuilder) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
