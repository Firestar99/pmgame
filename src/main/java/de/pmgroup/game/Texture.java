package de.pmgroup.game;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture {
	
	public static Texture load(InputStream in) {
		try {
			PNGDecoder dec = new PNGDecoder(in);
			int width = dec.getWidth();
			int height = dec.getHeight();
			ByteBuffer buffer = BufferUtils.createByteBuffer(height * width * 4);
			dec.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			return new Texture(buffer, width, height);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public final ByteBuffer buffer;
	public final int width, height;
	
	public Texture(ByteBuffer buffer, int width, int height) {
		this.buffer = buffer;
		this.width = width;
		this.height = height;
	}
	
	public byte[] getPixel(int x, int y) {
		byte[] ret = new byte[4];
		buffer.get(ret, y * height + x, 4);
		return ret;
	}
	
	public void setPixel(int x, int y, byte[] color) {
		buffer.put(color, y * height + x, 4);
	}
	
}
