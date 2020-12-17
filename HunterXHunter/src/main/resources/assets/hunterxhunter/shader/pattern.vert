#version 330 compatibility

uniform int timer;

out vec2  	vST;		// texture coords
out vec3 vert;

const float PI = 	3.14159265;
const float AMP = 	0.2;		// amplitude
const float W = 	2.;		// frequency

out  vec3  vN;		// normal vector
out  vec3  vL;		// vector from point to light
out  vec3  vE;		// vector from point to eye

vec3 LightPosition = vec3(  0., -1., .5 );

void
main() {
	vec3 vert = gl_Vertex.xyz;
	float yeet = vert.x + vert.y + vert.z;
	//vert.y = vert.y - (sin(uaTime * 2 * PI) * (vert.y/4));// - cos(yeet)/2 + sin(yeet)/2;
	vert.z = vert.z + 2 * cos(timer * 2 * PI/1000. + vert.y);

	vec4 ECposition = gl_ModelViewMatrix * vec4( vert, 1. );
	vN = normalize( gl_NormalMatrix * gl_Normal );	// normal vector
	vL = LightPosition - ECposition.xyz;		// vector from the point
												// to the light position
	vE = vec3( 0., 0., 0. ) - ECposition.xyz;	// vector from the point
												// to the eye position 

	vST = gl_MultiTexCoord0.st;
	
	gl_Position = gl_ModelViewProjectionMatrix * vec4( vert, 1. );
}