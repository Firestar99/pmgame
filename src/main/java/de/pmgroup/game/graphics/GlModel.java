package de.pmgroup.game.graphics;

import org.jetbrains.annotations.Nullable;

import static org.lwjgl.opengl.GL11.*;

public class GlModel {
	
	private final int type;
	private final float[] vertex;
	private final @Nullable float[] color;
	private final @Nullable float[] textureCorrd;
	private final @Nullable GlTexture glTexture;
	
	public GlModel(int type, float[] vertex) {
		this(type, vertex, null, null, null);
	}
	
	public GlModel(int type, float[] color, float[] vertex) {
		this(type, vertex, color, null, null);
	}
	
	public GlModel(int type, float[] vertex, @Nullable float[] textureCorrd, @Nullable GlTexture glTexture) {
		this(type, vertex, null, textureCorrd, glTexture);
	}
	
	public GlModel(int type, float[] vertex, @Nullable float[] color, @Nullable float[] textureCorrd, @Nullable GlTexture glTexture) {
		this.type = type;
		this.vertex = vertex;
		this.color = color;
		this.textureCorrd = textureCorrd;
		this.glTexture = glTexture;
	}
	
	public void draw() {
		if (glTexture != null)
			glTexture.bind();
		if (color != null)
			glColor3f(color[0], color[1], color[2]);
		glBegin(type);
		for (int i = 0; i < vertex.length; i += 2) {
			if (textureCorrd != null)
				glTexCoord2f(textureCorrd[i], textureCorrd[i + 1]);
			glVertex2f(vertex[i], vertex[i + 1]);
		}
		glEnd();
		if (color != null)
			glColor3f(1, 1, 1);
		if (glTexture != null)
			GlTexture.unbind();
	}
	
	public void free() {
	
	}
	
}
