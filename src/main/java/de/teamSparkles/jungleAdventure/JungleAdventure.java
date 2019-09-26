package de.teamSparkles.jungleAdventure;

import org.lwjgl.system.MemoryUtil;

import de.teamSparkles.engine.game.Game;
import de.teamSparkles.engine.game.GameBuilder;
import de.teamSparkles.engine.game.GameLoop;
import de.teamSparkles.engine.graphics.GlFramebuffer;
import de.teamSparkles.engine.graphics.GlFramebuffer.Attachment;
import de.teamSparkles.engine.graphics.GlTexture;
import de.teamSparkles.engine.graphics.GlTextureParam;
import de.teamSparkles.jungleAdventure.texture.Texture;

import static org.lwjgl.opengl.GL43.*;

public class JungleAdventure extends Game {
	
	public static final GlTextureParam TEXTURE_PARAM_NEAREST = new GlTextureParam()
			.setFilterMin(GL_NEAREST)
			.setFilterMag(GL_NEAREST);
	public static final GlTextureParam TEXTURE_PARAM_FBO_ATTACHMENT = new GlTextureParam()
			.setFilterMin(GL_LINEAR)
			.setFilterMag(GL_LINEAR);
	
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
	
	private final GameLoop gameLoop;
	
	//vaoEmpty
	private final int vaoEmpty;
	
	//images
	private final GlTexture textureEmoji;
	
	//shaders
	private final ShaderTextureRead shaderTextureRead;
	private final ShaderSimplePostprocess shaderSimplePostprocess;
	
	//framebuffer
	private final GlTexture fbo3dColor;
	private final GlFramebuffer fbo3d;
	
	public JungleAdventure(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
		
		//vaoEmpty
		vaoEmpty = glGenVertexArrays();
		glBindVertexArray(vaoEmpty);
		
		//images
		textureEmoji = new GlTexture(Texture.load(JungleAdventure.class.getResourceAsStream("emoji.png")), TEXTURE_PARAM_NEAREST);
		
		//shaders
		shaderTextureRead = ShaderTextureRead.create();
		shaderSimplePostprocess = ShaderSimplePostprocess.create();
		
		//framebuffer
		fbo3dColor = new GlTexture(gameLoop.width, gameLoop.height, TEXTURE_PARAM_FBO_ATTACHMENT);
		fbo3d = new GlFramebuffer(new Attachment(GL_COLOR_ATTACHMENT0, fbo3dColor));
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
		fbo3d.bind();
		shaderTextureRead.draw(new float[]{
				0, 0, 0, 0,
				0, 1, 0, 1,
				1, 1, 1, 1,
				0, 0, 0, 0,
				1, 0, 1, 0,
				1, 1, 1, 1,
		}, textureEmoji);
		GlFramebuffer.unbind();
		
		shaderSimplePostprocess.draw(fbo3dColor);
	}
}
