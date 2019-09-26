#version 430 core

layout(location = 0) out vec4 outColor;

layout (location = 0) in vec2 texCoord;

layout (location = 0) uniform sampler2D tex;

const int radius = 5;

void main() {
    vec4 color = vec4(0);
    for (int i = -radius; i <= radius; i++) {
        for (int j = -radius; j <= radius; j++) {
            color += texture(tex, texCoord + vec2(i, j) / 1080);
        }
    }
    float count = (radius+1) * (radius+1);
    outColor = color / count;
}
