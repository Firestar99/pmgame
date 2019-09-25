package de.teamSparkles.engine.graphics;

import org.jetbrains.annotations.Nullable;

import static org.lwjgl.opengl.GL11.*;

public class GlErrorCheck {
	
	/**
	 * maps error -500 => String name
	 */
	public static final String[] errors = {
			"GL_INVALID_ENUM",
			"GL_INVALID_VALUE",
			"GL_INVALID_OPERATION",
			"GL_STACK_OVERFLOW",
			"GL_STACK_UNDERFLOW",
			"GL_OUT_OF_MEMORY"
	};
	
	public static String getErrorName(int error) {
		int index = error - 0x500;
		if (index < 0 || index >= errors.length)
			return "Unknown";
		return errors[index];
	}
	
	public static void checkError() {
		checkError(null);
	}
	
	public static void checkError(@Nullable String prefix) {
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println((prefix != null ? prefix + " " : "") + "GL ERROR " + Integer.toHexString(error) + ": " + getErrorName(error));
	}
	
}
