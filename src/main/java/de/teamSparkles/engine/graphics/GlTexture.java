package de.teamSparkles.engine.graphics;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

import de.teamSparkles.engine.texture.Texture;

import static org.lwjgl.opengl.GL45.*;

public class GlTexture {
	
	public final int width, height;
	public final int textureId;
	
	public GlTexture(int width, int height, GlTextureParam textureParam) {
		this(width, height, null, textureParam);
	}
	
	public GlTexture(Texture texture, GlTextureParam textureParam) {
		this(texture.width, texture.height, texture.buffer, textureParam);
	}
	
	public GlTexture(int width, int height, @Nullable ByteBuffer buffer, GlTextureParam textureParam) {
		this.textureId = glCreateTextures(GL_TEXTURE_2D);
		this.width = width;
		this.height = height;
		
		glTextureParameteri(textureId, GL_TEXTURE_WRAP_S, textureParam.wrap[0]);
		glTextureParameteri(textureId, GL_TEXTURE_WRAP_T, textureParam.wrap[1]);
		glTextureParameteri(textureId, GL_TEXTURE_WRAP_R, textureParam.wrap[2]);
		glTextureParameteri(textureId, GL_TEXTURE_MIN_FILTER, textureParam.filterMin);
		glTextureParameteri(textureId, GL_TEXTURE_MAG_FILTER, textureParam.filterMag);
		glTextureStorage2D(textureId, textureParam.mipMapCount, GL_RGBA8, width, height);
		if (buffer != null) {
			glTextureSubImage2D(textureId, 0, 0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			if (textureParam.genMipmaps)
				glGenerateTextureMipmap(textureId);
		}
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
