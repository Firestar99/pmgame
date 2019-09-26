package de.teamSparkles.engine.graphics;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.opengl.GL45.glCreateFramebuffers;

public class GlFramebuffer {
	
	private final int fboId;
	
	public GlFramebuffer(Attachment... attachments) {
		this.fboId = glCreateFramebuffers();
		
		bind();
		for (Attachment attachment : attachments)
			glFramebufferTexture(GL_FRAMEBUFFER, attachment.attachment, attachment.texture.textureId, 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			throw new RuntimeException("Framebuffer incomplete");
		unbind();
	}
	
	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);
	}
	
	public static void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void release() {
		glDeleteFramebuffers(fboId);
	}
	
	public static class Attachment {
		
		public final int attachment;
		public final GlTexture texture;
		
		public Attachment(int attachment, GlTexture texture) {
			this.attachment = attachment;
			this.texture = texture;
		}
	}
}
