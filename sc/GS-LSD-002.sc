//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//---------------------------------------------------------------- 002 - SPAZIO -
//-------------------------------------------------------------------------------


// Sense of space
// Sensazione di spazialità o direzione attraverso le differenze di fase

{ var x; x = BrownNoise.ar(0.2); [x,x] }.scope(2); // correlated
{ {BrownNoise.ar(0.2)}.dup }.scope(2); // not correlated

// correlated
(
{ var x;
    x = Klank.ar(`[[200, 671, 1153, 1723], nil, [1, 1, 1, 1]], PinkNoise.ar(7e-3));
    [x,x]
}.scope(2))
// not correlated
{ Klank.ar(`[[200, 671, 1153, 1723], nil, [1, 1, 1, 1]], PinkNoise.ar([7e-3,7e-3])) }.scope(2);

// two waves mixed together coming out both speakers
{ var x; x = Mix.ar(VarSaw.ar([100,101], 0, 0.1, 0.2)); [x,x] }.scope(2);
// two waves coming out each speaker independently
{ VarSaw.ar([100,101], 0, 0.1, 0.2 * 1.414) }.scope(2); // * 1.414 to compensate for power

// delays as cues to direction
// mono
{ var x; x = LFTri.ar(1000,0,Decay2.ar(Impulse.ar(4,0,0.2),0.004,0.2)); [x,x]}.scope(2);

(
// inter-speaker delays
{ var x; x = LFTri.ar(1000,0,Decay2.ar(Impulse.ar(4,0,0.2),0.004,0.2));
    [DelayC.ar(x,0.01,0.01),DelayC.ar(x,0.02,MouseX.kr(0.02, 0))]
}.scope(2);
)

(
// mixing two delays together
// you hear a phasing sound but the sound is still flat.
{ var x; x = BrownNoise.ar(0.2);
    x = Mix.ar([DelayC.ar(x,0.01,0.01),DelayC.ar(x,0.02,MouseX.kr(0,0.02))]);
    [x,x]
}.scope(2);
)

(
// more spatial sounding. phasing causes you to perceive directionality
{ var x; x = BrownNoise.ar(0.2);
    [DelayC.ar(x,0.01,0.01),DelayC.ar(x,0.02,MouseX.kr(0.02, 0))]
}.scope(2);
)

//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//----------------------------------------------------- AMPIEZZA MEZZA BELLEZZA -
//----------------------------------------------------------------------- 10 CH -
//-------------------------------------------------------------------------------

s.boot;
(
{
    PanAz.ar(
        10,                                           // numChans
        ClipNoise.ar,                                // in
        LFSaw.kr(MouseX.kr(0.1, 8, 'exponential')), // pos
        0.2,                                       // level
        3                                         // width
    );
}.play;
s.scope(10);
)

//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//------------------------------------------------------------ AMBISONIC PANNER -
//----------------------------------------------------------------------- I ORD -
//-------------------------------------------------------------------------------

// PanB2 and DecodeB2 - 2D ambisonics panner and decoder
(
{
	var w, x, y, p, lf, rf, rr, lr;

	p = BrownNoise.ar; // source

	// B-format encode
	#w, x, y = PanB2.ar(p,
    //MouseX.kr(-1,1),
    LFSaw.kr(MouseX.kr(0.1, 8, 'exponential')),
    0.3

  );

	// B-format decode to quad. outputs in clockwise order
	// #lf, rf, rr, lr = DecodeB2.ar(4, w, x, y);

	[w, x, y, 0]
	//[lf, rf, lr, rr] // reorder to my speaker arrangement: Lf Rf Lr Rr
}.scope(4);
)

//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//----------------------------------------------------------- APPUNTI SULL'ARNO -
//-------------------------------------------------------------------------------

// one full cycle for PanAz: from 0 to 1
{ PanAz.ar(2, DC.ar(1), Line.ar(0, 1, 0.1)) }.plot(0.1)

// one full cycle for Pan2: from -1 to 1 and back to -1
{ Pan2.ar(DC.ar(1), EnvGen.kr(Env([-1, 1, -1], [0.05, 0.05]))) }.plot(0.1)

// in other words, while Pan2 makes one full transition
{ Pan2.ar(DC.ar(1), Line.ar(-1, 1, 0.1)) }.plot(0.1)

// we need only a half one in PanAz:
{ PanAz.ar(2, DC.ar(1), Line.ar(0, 1/2, 0.1)) }.plot(0.1)

//

(
var window;
window = Window.new("Pan Knob", Rect(640,630,270,70)).front;
k = Knob.new(window, Rect(20,10,36,36));
k.action_({|v,x,y,m| \pan.asSpec.map(v.value).postln; })
//  .mode_(\horiz)
    .centered_(false)
    .value_(\pan.asSpec.unmap(0)); // 0.5
//k.color[1] = Color.gray(alpha:0);
)

k.centered
k.centered = true
k.centered = false