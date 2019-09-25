package de.teamSparkles.engine.graphics;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

import de.teamSparkles.jungleAdventure.texture.Texture;

import static org.lwjgl.opengl.GL45.*;

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
		this.textureId = glCreateTextures(GL_TEXTURE_2D);
		this.width = width;
		this.height = height;
		
		glTextureParameteri(textureId, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTextureParameteri(textureId, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTextureParameteri(textureId, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTextureParameteri(textureId, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTextureStorage2D(textureId, 1, GL_RGBA8, width, height);
		if (buffer != null)
			glTextureSubImage2D(textureId, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}
	
	public void bind(int location) {
		glBindTextureUnit(location, textureId);
	}
	
	public void uploadTexture(Texture texture) {
		if (width != texture.width || height != texture.height)
			throw new RuntimeException("invalid size");
		glTextureSubImage2D(textureId, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, texture.buffer);
	}
	
	public void release() {
		glDeleteTextures(textureId);
	}
}
