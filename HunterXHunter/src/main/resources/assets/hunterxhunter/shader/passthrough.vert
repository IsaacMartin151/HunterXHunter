#version 120

uniform int time;
uniform sampler2D thetexture;

out float vX, vY;
out vec3  vMCposition;
out float vLightIntensity;
out vec2 vST;
out vec4 color;

vec3 LIGHTPOS   = vec3( -2., 0., 10. );

void
main( )
{
    vec2 texcoord = vec2(gl_TexCoord[0]);
    vec4 color = texture2D(thetexture, texcoord);
    //gl_TexCoord[0] = gl_MultiTexCoord0;
	vST = texcoord.st;

	vec3 tnorm      = normalize( gl_NormalMatrix * gl_Normal );
	vec3 ECposition = vec3( gl_ModelViewMatrix * gl_Vertex );
	vLightIntensity  = abs( dot( normalize(LIGHTPOS - ECposition), tnorm ) );
    //vec4 color = gl_Color;
	vMCposition  = gl_Vertex.xyz;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}