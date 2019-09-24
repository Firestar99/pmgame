package de.pmgroup.game.graphics;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.pmgroup.game.TextureGame;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	
	public static Texture load(InputStream in) {
		try {
			PNGDecoder dec = new PNGDecoder(TextureGame.class.getResourceAsStream("emoji.png"));
			int width = dec.getWidth();
			int height = dec.getHeight();
			ByteBuffer buffer = BufferUtils.createByteBuffer(height * width * 4);
			dec.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			return new Texture(width, height, buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final int textureId;
	
	public Texture(int width, int height, ByteBuffer buffer) {
		this.textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void bind() {
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureId);
	}
	
	public static void unbind() {
		glDisable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void free() {
		glDeleteTextures(textureId);
	}
}
