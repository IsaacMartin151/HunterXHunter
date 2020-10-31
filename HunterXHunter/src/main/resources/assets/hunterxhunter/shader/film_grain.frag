#version 120

uniform int time;
uniform sampler3D noisetexture;

in float vLightIntensity;
in vec3  vMCposition;
in vec2 vST;
in vec4 color;


const vec3 WHITE = vec3( 1., 1., 1. );
const vec3 GREEN = vec3( 0., 1., 0. );

void
main( )
{
    float coolio = float(cos(time))/10;
    float uAd = .1 + coolio;
    float uBd = .1 + float((sin(time))/10);
    float uTol = .7 + coolio;
    float uNoiseAmp = .7;
    float uNoiseFreq = 4.;
    float uAlpha = 0.;
	vec4 nv  = texture3D( noisetexture, uNoiseFreq * vMCposition );
	float n = nv.r + nv.g + nv.b + nv.a;
	n = n - 2.;
	n *= uNoiseAmp;

	float Ar = uAd/2.;
	float Br = uBd/2.;

	float s = vST.s;
	float t = vST.t;

	int numins = int( s / uAd );
	int numint = int( t / uBd );

	float sc = float(numins) * uAd  +  Ar;
	float ds = s - sc;                   // wrt ellipse center
	float tc = float(numint) * uBd  +  Br;
	float dt = t - tc;                   // wrt ellipse center

	float oldDist = sqrt( ds*ds + dt*dt );
	float newDist = n + oldDist;
	float scale = newDist / oldDist;        // this could be < 1., = 1., or > 1.

	ds *= scale;                            // scale by noise factor
	ds /= Ar;                               // ellipse equation

	dt *= scale;                            // scale by noise factor
	dt /= Br;                               // ellipse equation

	float d = ds*ds + dt*dt;

	d = smoothstep( 1. - uTol, 1. + uTol, d);

	if( uAlpha == 0 && d == 0) {
		discard;
	}
	else {
		gl_FragColor = vec4( 1., 1., 1., 0. );
	}

	vec3 rgb = vLightIntensity * mix( WHITE, GREEN, d );
	if (d == 0) {
		gl_FragColor = vec4( rgb, uAlpha );
	}
	else {
		gl_FragColor = vec4( rgb, 1);
	}
}