package de.teamSparkles.jungleAdventure;

import org.lwjgl.system.MemoryUtil;

import de.teamSparkles.engine.game.Game;
import de.teamSparkles.engine.game.GameBuilder;
import de.teamSparkles.engine.graphics.GlTexture;
import de.teamSparkles.jungleAdventure.texture.Texture;

import static org.lwjgl.opengl.GL43.*;

public class JungleAdventure extends Game {
	
	public static void main(String[] args) {
		new GameBuilder()
				.setWidth(1080)
				.setHeight(1080)
				.setFullscreen(false)
				.setTitle("Jungle Game")
				.setOpenglVersion(4, 3, false)
				.setOpenglDebugCallback((source, type, id, severity, length, message, userParam) -> System.out.println("OpenGL debug: " + MemoryUtil.memUTF8(message, length)))
				.setStarter(JungleAdventure::new)
				.build()
				.run();
	}
	
	private final long windowPtr;
	private final int vaoEmpty;
	private final GlTexture textureEmoji;
	private final ShaderTextureRead shaderTextureRead;
	
	public JungleAdventure(long windowPtr) {
		this.windowPtr = windowPtr;
		
		//vaoEmpty
		vaoEmpty = glGenVertexArrays();
		glBindVertexArray(vaoEmpty);
		
		//images
		textureEmoji = new GlTexture(Texture.load(JungleAdventure.class.getResourceAsStream("emoji.png")));
		
		//shaders
		shaderTextureRead = ShaderTextureRead.create();
	}
	
	@Override
	public void release() {
		//images
		textureEmoji.release();
		
		//shaders
		shaderTextureRead.release();
		
		//vaoEmpty
		glDeleteVertexArrays(vaoEmpty);
	}
	
	@Override
	public void render() {
		shaderTextureRead.draw(new float[]{
				0, 0, 0, 0,
				0, 1, 0, 1,
				1, 1, 1, 1,
				0, 0, 0, 0,
				1, 0, 1, 0,
				1, 1, 1, 1,
		}, textureEmoji);
	}
}
