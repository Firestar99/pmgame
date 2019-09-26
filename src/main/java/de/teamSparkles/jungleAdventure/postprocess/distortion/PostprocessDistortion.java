package de.teamSparkles.jungleAdventure.postprocess.distortion;

import de.teamSparkles.engine.graphics.GlTexture;
import de.teamSparkles.engine.graphics.GlTextureParam;
import de.teamSparkles.engine.texture.Texture;

import static org.lwjgl.opengl.GL11.*;

public class PostprocessDistortion {
	
	public static final GlTextureParam TEXTURE_PARAM = new GlTextureParam()
			.setFilterMin(GL_LINEAR)
			.setFilterMag(GL_LINEAR)
			.setWrap(GL_REPEAT, GL_REPEAT);
	
	//textures
	private final GlTexture textureDistortionDirection;
	private final GlTexture textureDistortionHeight;
	private final GlTexture textureDistortionRandom;
	
	//shaders
	private final ShaderPostprocessDistortion shaderDistortion;
	
	public PostprocessDistortion() {
		//textures
		textureDistortionDirection = new GlTexture(Texture.load(PostprocessDistortion.class.getResourceAsStream("distortionDirection.png")), TEXTURE_PARAM);
		textureDistortionHeight = new GlTexture(Texture.load(PostprocessDistortion.class.getResourceAsStream("distortionHeight.png")), TEXTURE_PARAM);
		textureDistortionRandom = new GlTexture(Texture.load(PostprocessDistortion.class.getResourceAsStream("distortionRandom.png")), TEXTURE_PARAM);
		
		//shaders
		shaderDistortion = ShaderPostprocessDistortion.create();
	}
	
	public void draw(GlTexture color, float[] randomOffset) {
		shaderDistortion.draw(color, textureDistortionDirection, textureDistortionHeight, textureDistortionRandom, randomOffset);
	}
	
}
