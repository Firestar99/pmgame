package de.pmgroup.game.graphics;

import org.jetbrains.annotations.Nullable;

import static org.lwjgl.opengl.GL11.*;

public class Model {
	
	private final int type;
	private final float[] vertex;
	private final @Nullable float[] color;
	private final @Nullable float[] textureCorrd;
	private final @Nullable Texture texture;
	
	public Model(int type, float[] vertex) {
		this(type, vertex, null, null, null);
	}
	
	public Model(int type, float[] color, float[] vertex) {
		this(type, vertex, color, null, null);
	}
	
	public Model(int type, float[] vertex, @Nullable float[] textureCorrd, @Nullable Texture texture) {
		this(type, vertex, null, textureCorrd, texture);
	}
	
	public Model(int type, float[] vertex, @Nullable float[] color, @Nullable float[] textureCorrd, @Nullable Texture texture) {
		this.type = type;
		this.vertex = vertex;
		this.color = color;
		this.textureCorrd = textureCorrd;
		this.texture = texture;
	}
	
	public void draw() {
		if (texture != null)
			texture.bind();
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
		if (texture != null)
			Texture.unbind();
	}
	
	public void free() {
	
	}
	
}
