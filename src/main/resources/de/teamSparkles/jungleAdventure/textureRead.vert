#version 430 core
#extension GL_ARB_shading_language_420pack : require

uniform vec4[6] inputs;

layout (location = 0) out vec2 texCoord;

void main() {
    vec4 i = inputs[gl_VertexID % 6];
    texCoord = i.zw;
    gl_Position = vec4(i.xy, 0, 1);
}
