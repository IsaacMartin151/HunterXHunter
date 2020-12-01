#version 330 compatibility

uniform sampler2D noise;
uniform int uColor1;
uniform int uColor2;
uniform int uColor3;
uniform int timer;

in vec3  vMCposition;
in vec2 vST;

in vec4 bruh;

in  vec3  vN;			// normal vector
in  vec3  vL;			// vector from point to light
in  vec3  vE;			// vector from point to eye

const vec3 WHITE = vec3( 1., 1., 1. );
const vec3 CYAN = vec3( 0., 1., 1. );
const vec3 ORANGE = vec3(1., .5, 0.);
const vec3 BLACK = vec3(0., 0., 0.);

void
main() {
    float uAd = .1;
        float uBd = .1;
        float uTol = .7;
        float uNoiseAmp = .5;
        float uNoiseFreq = 2.;
        float uAlpha = 0.;
    	vec4 nv  = texture2D( noise, uNoiseFreq *vST );
    	float n = nv.r + nv.g + nv.b + nv.a;
    	n = n - 2.;
    	n *= uNoiseAmp;

    	float Ar = uAd/2.;
    	float Br = uBd/2.;

    	float s = vST.s;
    	float t = vST.t;
    	int time = 1;

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

    vec3 Normal = normalize(vN);
	vec3 Light = normalize(vL);
	vec3 Eye = normalize(vE);

    vec4 color = texture2D(noise, vST);

    //if( int((vST.s + vST.t) * 10) % 3 != 2  ) {
	//	myColor = color;
	//}
	vec4 myColor = color;
	if (d == 0 || myColor.a == 0) {
	    discard;
	}
	if (myColor.r == 0 && myColor.g == 0 && myColor.b == 0) {
	    myColor = vec4(float(uColor1)/1000., float(uColor2)/1000., float(uColor3)/1000., .5);
	}
	myColor = myColor * d;
	gl_FragColor = vec4( myColor);
}