#version 430 core
#extension GL_ARB_shading_language_420pack : require

layout(location = 0) out vec4 outColor;

layout (location = 0) in vec2 inTexCoord;

layout (binding = 0) uniform sampler2D color;
layout (binding = 1) uniform sampler2D distortionDirection;
layout (binding = 2) uniform sampler2D distortionHeight;
layout (binding = 3) uniform sampler2D distortionRandom;

uniform vec2 randomOffset;

const int radius = 5;

//vec2 heightToNormal(sampler2D sampler, vec2 texCoord, int offset) {
//    float middle = texture(sampler, texCoord).x;
//    float xp = textureOffset(sampler, texCoord, vec2(-offset, 0)).x;
//    float xn = textureOffset(sampler, texCoord, vec2(offset, 0)).x;
//    float yp = textureOffset(sampler, texCoord, vec2(0, -offset)).x;
//    float yn = textureOffset(sampler, texCoord, vec2(0, offset)).x;
//
//    return vec2(xp-xn, yp-yn);
//}

void main() {
    vec4 direction = texture(distortionDirection, inTexCoord);
    //    float height = direction.b * texture(distortionHeight, inTexCoord).r * texture(distortionRandom, inTexCoord + randomOffset).r;
    float height = direction.b * texture(distortionHeight, inTexCoord + randomOffset).r;
    outColor = texture(color, inTexCoord + (direction.xy * 2 - 1) + height);
}
