#version 430 core
#extension GL_ARB_shading_language_420pack : require

layout(location = 0) out vec4 outColor;

layout (location = 0) in vec2 inTexCoord;

layout (binding = 0) uniform sampler2D texColor;
layout (binding = 1) uniform sampler2D texDistortionDirection;
layout (binding = 2) uniform sampler2D texDistortionRandom;

uniform vec2 uniformRandomOffset1;
uniform vec2 uniformRandomOffset2;

void main() {
    //original color
    vec3 color = texture(texColor, inTexCoord).rgb;

    //reflection
    vec4 refOffset = texture(texDistortionDirection, inTexCoord);
    float refRandomOffset = refOffset.b
    * texture(texDistortionRandom, inTexCoord + uniformRandomOffset1).r
    * texture(texDistortionRandom, inTexCoord + uniformRandomOffset2).r;
    vec3 refColor = texture(texColor, inTexCoord + (refOffset.xy * 2 - 1) + refRandomOffset).rgb * refOffset.a;
    if (refOffset.w > 0) {
        color = color * (1-refOffset.w) + refColor * refOffset.w;
    }

    outColor = vec4(color, 1);
}
