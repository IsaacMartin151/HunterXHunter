#version 330 compatibility

varying vec4 v_color;

void main() {
    v_color = gl_Color;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}