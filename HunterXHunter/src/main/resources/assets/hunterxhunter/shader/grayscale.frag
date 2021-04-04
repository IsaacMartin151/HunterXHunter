#version 330 compatibility

varying vec4 v_color;

void main() {
        vec3 color = v_color.rgb;
        vec3 grayscale = vec3(0.299 * color.r + 0.587 * color.g + 0.114 * color.b);

        gl_FragColor = vec4(grayscale, 1.0);
}