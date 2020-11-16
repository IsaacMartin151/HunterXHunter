#version 120

in vec2 texCoords;

out vec2  vST;
out vec3  vMCposition;
out vec4 bruh;

out  vec3  vN;		// normal vector
out  vec3  vL;		// vector from point to light
out  vec3  vE;		// vector from point to eye

vec3 LightPosition = vec3(  0., 0., 0. );

void main() {
    vec4 ECposition = gl_ModelViewMatrix * vec4( gl_Vertex.xyz, 1. );
    vN = normalize( gl_NormalMatrix * gl_Normal );	// normal vector
    vL = LightPosition - ECposition.xyz;		// vector from the point
												// to the light position
    vE = vec3( 0., 0., 0. ) - ECposition.xyz;	// vector from the point
    												// to the eye position
	vST = gl_MultiTexCoord0.st;
	vMCposition  = gl_Vertex.xyz;
	vec4 bruh = gl_FrontColor;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}