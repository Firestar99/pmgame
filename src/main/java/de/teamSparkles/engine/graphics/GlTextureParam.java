package de.teamSparkles.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class GlTextureParam {
	
	public int[] wrap = new int[]{
			GL_CLAMP_TO_EDGE,
			GL_CLAMP_TO_EDGE,
			GL_CLAMP_TO_EDGE,
	};
	public int filterMin = GL_LINEAR;
	public int filterMag = GL_LINEAR;
	public int mipMapCount = 1;
	public boolean genMipmaps = true;
	
	public GlTextureParam() {
	}
	
	public GlTextureParam setWrap(int wrapS) {
		return setWrap(new int[]{wrapS, GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE});
	}
	
	public GlTextureParam setWrap(int wrapS, int wrapT) {
		return setWrap(new int[]{wrapS, wrapT, GL_CLAMP_TO_EDGE});
	}
	
	public GlTextureParam setWrap(int wrapS, int wrapT, int wrapQ) {
		return setWrap(new int[]{wrapS, wrapT, wrapQ});
	}
	
	public GlTextureParam setWrap(int[] wrap) {
		if (wrap.length != 3)
			throw new IllegalArgumentException("wrap.length != 3");
		this.wrap = wrap;
		return this;
	}
	
	public GlTextureParam setFilterMin(int filterMin) {
		this.filterMin = filterMin;
		return this;
	}
	
	public GlTextureParam setFilterMag(int filterMag) {
		this.filterMag = filterMag;
		return this;
	}
	
	public GlTextureParam setMipMapCount(int mipMapCount) {
		this.mipMapCount = mipMapCount;
		return this;
	}
	
	public GlTextureParam setGenMipmaps(boolean genMipmaps) {
		this.genMipmaps = genMipmaps;
		return this;
	}
}
