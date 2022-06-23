#version 330 compatibility

uniform int timer;

in  vec2  vST;			// texture coords
in  vec3  vN;			// normal vector
in  vec3  vL;			// vector from point to light
in  vec3  vE;			// vector from point to eye
in  vec4  uColor;
in  vec3 vert;

void
main( )
{
	
	float uKa = .8;
	float uKd = .2;
	float uKs = .3;
	float uShininess = .3;

	vec3 uSpecularColor = vec3(.2, .2, .2);

	vec3 Normal = normalize(vN);
	vec3 Light     = normalize(vL);
	vec3 Eye        = normalize(vE);

	vec3 myColor = vec3(uColor[0], uColor[1], uColor[2]);

	//if( abs(vert.x) < .1)
	//{
	//	myColor = vec3(uColor[0]+.2, uColor[1], uColor[2]);
	//}

	vec3 ambient = uKa * myColor;

	float d = max( dot(Normal,Light), 0. );       // only do diffuse if the light can see the point
	vec3 diffuse = uKd * d * myColor;

	float s = 0.;
	if( dot(Normal,Light) > 0. )	          // only do specular if the light can see the point
	{
		vec3 ref = normalize(  reflect( -Light, Normal )  );
		s = pow( max( dot(Eye,ref),0. ), uShininess );
	}
	vec3 specular = uKs * s * uSpecularColor;
	gl_FragColor = vec4( ambient + diffuse + specular,  uColor[3] );
}
