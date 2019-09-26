#version 430 core
#extension GL_ARB_shading_language_420pack : require

layout(location = 0) out vec4 outColor;

layout (location = 0) in vec2 inTexCoord;

layout (binding = 0) uniform sampler2D texColor;
layout (binding = 1) uniform sampler2D texDistortionDirection;
layout (binding = 2) uniform sampler2D texDistortionRandomNormal;

uniform vec2 uniformRandomOffset1;
uniform vec2 uniformRandomOffset2;

vec2 textureNormal(sampler2D sampler, vec2 texCoord) {
    return normalize(texture(sampler, texCoord).rgb * 2 - 1).rg;
}

void main() {
    //distortion offset
    vec4 reflectionOffset = texture(texDistortionDirection, inTexCoord);
    vec2 randomOffset = reflectionOffset.b
    * textureNormal(texDistortionRandomNormal, (inTexCoord + uniformRandomOffset1)/8)
    * textureNormal(texDistortionRandomNormal, (inTexCoord + uniformRandomOffset2)/8);

    //direct color
    vec3 color = texture(texColor, inTexCoord + randomOffset).rgb;

    //blending reflection
    vec3 reflectionColor = texture(texColor, inTexCoord + (reflectionOffset.xy * 2 - 1) + randomOffset).rgb * reflectionOffset.a;
    if (reflectionOffset.w > 0) {
        color = color * (1-reflectionOffset.w) + reflectionColor * reflectionOffset.w;
    }

    outColor = vec4(color, 1);
}
