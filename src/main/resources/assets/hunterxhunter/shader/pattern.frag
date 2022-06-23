#version 330 compatibility

uniform int	timer;		// "Time", from Animate( )

in vec3		vert;

in  vec2  vST;			// texture coords
in  vec3  vN;			// normal vector
in  vec3  vL;			// vector from point to light
in  vec3  vE;			// vector from point to eye


void main() {
	float uKa = 1.f;
	float uKd = .8f;
	float uKs = .8f;
	vec3 uColor = vec3(.5, 0., 1.);
	vec3 uSpecularColor = vec3(.5, 0., 1.);
	float uShininess = .5f;
	//uColor = vec3(1., .5, .5);
	vec3 Normal = normalize(vN);
	vec3 Light = normalize(vL);
	vec3 Eye = normalize(vE);

	vec3 myColor = uColor;
	if( int((vST.s + vST.t) * 10) % 3 != 2 )
	{
		//myColor = cos(uTime) * Light;
		//if (vert.x >= 0 && uTime != 12.) {
			//myColor = cos(uTime) * Normal;
		//}
	}

	float uh = smoothstep(.3, .7, 5);

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
	gl_FragColor = vec4( ambient + diffuse + specular,  1. );
}