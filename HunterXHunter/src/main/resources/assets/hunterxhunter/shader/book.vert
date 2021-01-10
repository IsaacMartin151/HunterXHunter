#version 330 compatibility

uniform int timer;

out  vec3  vN;		// normal vector
out  vec3  vL;		// vector from point to light
out  vec3  vE;		// vector from point to eye
out  vec4 uColor;
out  vec3 vert;

vec3 LightPosition = vec3(  0., 5., 1. );

void
main( )
{ 
	
	vert = gl_Vertex.xyz;

	if( abs(vert.x) < .1) {
		vert.y = vert.y - (vert.y * (cos(vert.x * 2)));
	}

	uColor = gl_Color;

	vec4 ECposition = gl_ModelViewMatrix * vec4( vert, 1. );
	vN = normalize( gl_NormalMatrix * gl_Normal );	// normal vector
	vL = LightPosition - ECposition.xyz;		// vector from the point
							// to the light position
	vE = vec3( 0., 0., 0. ) - ECposition.xyz;	// vector from the point
							// to the eye position 
	gl_Position = gl_ModelViewProjectionMatrix * vec4( vert, 1. );
}