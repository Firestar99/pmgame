package de.pmgroup.game.graphics;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

import de.pmgroup.game.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GlTexture {
	
	private final int width, height;
	private final int textureId;
	
	public GlTexture(int width, int height) {
		this(width, height, null);
	}
	
	public GlTexture(Texture texture) {
		this(texture.width, texture.height, texture.buffer);
	}
	
	public GlTexture(int width, int height, @Nullable ByteBuffer buffer) {
		this.textureId = glGenTextures();
		this.width = width;
		this.height = height;
		
		bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		unbind();
	}
	
	public void bind() {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureId);
	}
	
	public static void unbind() {
		glDisable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void uploadTexture(Texture texture) {
		if (width != texture.width || height != texture.height)
			throw new RuntimeException();
		bind();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture.buffer);
		unbind();
	}
	
	public void free() {
		glDeleteTextures(textureId);
	}
}
