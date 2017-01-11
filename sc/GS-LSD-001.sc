//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------- MONO - DUALMONO -
//------------------------------------------------------------------------------------------------------

// questo non è un tutorial su supercollider
// references
"open https://www.youtube.com/watch?v=6iFS22yyXj8".unixCmd
"open https://jahya.net/blog/quickref-for-supercollider/#Stereo".unixCmd
"open http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/Tutorials/Getting-Started/Getting%20Started%20With%20SC.html".unixCmd
"open http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/Language/Literals.html".unixCmd
"open http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/Tutorials/Getting-Started/Functions%20and%20Sound.html".unixCmd
"open http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/UGens/UGens.html".unixCmd
"open http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/UGens/Tour_of_UGens.html".unixCmd
"open https://www.youtube.com/watch?v=PZhqZAVGTqw".unixCmd

// nonostante ciò immagino che per voi sia la prima volta che lo vedete quindi ciao
"Hello World!".postln;

// iterazione e reiterazione
7.rand;

// sto in blocco!
// qui dovresti spiegare la differenza tra chiamare una riga e chiamare il blocco
(
"Chiamatemi, ".post;
"Ismaele.".postln;
)

// funzione sinusoidale 440Hz 0.2 di ampiezza
{ SinOsc.ar(440, 0, 0.2) }.play;
{ SinOsc.ar(69.midicps, 0, 0.2) }.play;

// dichiarato
{
	SinOsc.ar(
		mul:0.7,
		freq:261.26,
		phase:pi)
}.play;

// fasi
{ SinOsc.ar(440, 0, 0.2) }.plot;
{ SinOsc.ar(440, 0.5pi, 0.2) }.plot;
{ SinOsc.ar(440, pi, 0.2) }.plot;
{ SinOsc.ar(440, 1.5pi, 0.2) }.plot;

// non suona? forse dovresti controllare la finestra post
// accendi il motore e riprova
s.boot;

// per spegnere il motore
s.quit;

// s is just an interpreter variable, which is pre-assigned the value from Server.local:
// Server.local.boot;
// Interpreter variables (a - z) are pre-declared when you start up SC, and have global scope.

// chi a destra e chi a sinistra
{ [0, SinOsc.ar(440, 0, 0.2)] }.play;

// lascia o raddoppia
{ [SinOsc.ar(440, 0, 0.2), SinOsc.ar(440, 0, 0.2)] }.play;

// i nostri amati…
{ [SinOsc.ar(440, 0, 0.2), SinOsc.ar(442, 0, 0.2)] }.play;
{ SinOsc.ar([440, 442], 0, 0.2) }.play;

// equal power stereo pan a mono source arguments: in, pan position, level pan controls typically range from -1 to +1
{ Pan2.ar(PinkNoise.ar(0.2), -0.3) }.play; //fixed at -0.3
{ Pan2.ar(PinkNoise.ar(0.2), SinOsc.kr(0.5)) }.play; //moving
{ Pan2.ar(BrownNoise.ar, MouseX.kr(-1,1), 0.3) }.scope(2);

// linear pan a mono source (not equal power) arguments: in, pan position, level
{ LinPan2.ar(BrownNoise.ar, MouseX.kr(-1,1), 0.3) }.scope(2);

// balance a stereo source arguments: left in, right in, pan position, level
{ Balance2.ar(BrownNoise.ar, BrownNoise.ar, MouseX.kr(-1,1), 0.3) }.scope(2);

// equal power quad panner
{ Pan4.ar(BrownNoise.ar, MouseX.kr(-1,1), MouseY.kr(1,-1), 0.3) }.scope(4);

// azimuth panner to any number of channels arguments: num channels, in, pan position, level, width
{ PanAz.ar(5, BrownNoise.ar, MouseX.kr(-1,1), 0.3, 2) }.scope(5);

// PanB2 and DecodeB2 - 2D ambisonics panner and decoder
(
{
	var w, x, y, p, lf, rf, rr, lr;

	p = BrownNoise.ar; // source

	// B-format encode
	#w, x, y = PanB2.ar(p, MouseX.kr(-1,1), 0.3);

	// B-format decode to quad. outputs in clockwise order
	#lf, rf, rr, lr = DecodeB2.ar(4, w, x, y);

	[lf, rf, lr, rr] // reorder to my speaker arrangement: Lf Rf Lr Rr
}.scope(4);
)

//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------- SCHROEDER REVERB -
//------------------------------------------------------------------------------------------------------

// references
"open http://www.openairlib.net/anechoicdb".unixCmd
"open https://en.wikipedia.org/wiki/Anechoic_chamber".unixCmd
"open https://en.wikibooks.org/wiki/Designing_Sound_in_SuperCollider/Schroeder_reverb".unixCmd
"open https://ccrma.stanford.edu/~jos/pasp/Schroeder_Reverberators.html".unixCmd
"open https://en.wikipedia.org/wiki/Reverberation".unixCmd
"open https://ccrma.stanford.edu/~jos/pasp04/Artificial_Reverberation.html".unixCmd

b = Buffer.read(s, "/Users/giuseppe/gscloud/gitrepo/didattica/LSD/sounds/anechoic/12667.aiff");
// Hear it raw:
b.play

// Now here's the code which creates the reverb in a single Synth, with four separate delay lines cross-fertilising each other:

(
Ndef(\verb, {
	var input, output, delrd, sig, deltimes;

	// Choose which sort of input you want by (un)commenting these lines:
	input = Pan2.ar(PlayBuf.ar(1, b, loop: 0), -0.5); // buffer playback, panned halfway left
	//input = SoundIn.ar([0,1]); // TAKE CARE of feedback - use headphones
	//input = Dust2.ar([0.1, 0.01]); // Occasional clicks

	// Read our 4-channel delayed signals back from the feedback loop
	delrd = LocalIn.ar(4);

	// This will be our eventual output, which will also be recirculated
	output = input + delrd[[0,1]];

	// Cross-fertilise the four delay lines with each other:
	sig = [output[0]+output[1], output[0]-output[1], delrd[2]+delrd[3], delrd[2]-delrd[3]];
	sig = [sig[0]+sig[2], sig[1]+sig[3], sig[0]-sig[2], sig[1]-sig[3]];
	// Attenutate the delayed signals so they decay:
	sig = sig * [0.4, 0.37, 0.333, 0.3];

	// Here we give delay times in milliseconds, convert to seconds,
	// then compensate with ControlDur for the one-block delay
	// which is always introduced when using the LocalIn/Out fdbk loop
	deltimes = [101, 143, 165, 177] * 0.001 - ControlDur.ir;

	// Apply the delays and send the signals into the feedback loop
	LocalOut.ar(DelayC.ar(sig, deltimes, deltimes));

	// Now let's hear it:
	Out.ar(0, output);

}).play
)

// And here's an alternative way of doing exactly the same thing, this time using a matrix to represent the
// cross-mixing of the delayed streams. The single matrix replaces all those plusses and minusses so it's
// a neat way to represent the mixing - see which you find most readable.

(
Ndef(\verb, {
	var input, output, delrd, sig, deltimes;

	// Choose which sort of input you want by (un)commenting these lines:
	input = Pan2.ar(PlayBuf.ar(1, b, loop: 0), -0.5); // buffer playback, panned halfway left
	//input = SoundIn.ar([0,1]); // TAKE CARE of feedback - use headphones
	//input = Dust2.ar([0.1, 0.01]); // Occasional clicks

	// Read our 4-channel delayed signals back from the feedback loop
	delrd = LocalIn.ar(4);

	// This will be our eventual output, which will also be recirculated
	output = input + delrd[[0,1]];

	sig = output ++ delrd[[2,3]];
	// Cross-fertilise the four delay lines with each other:
	sig = ([ [1,  1,  1,  1],
	         [-1, -1,  -1, -1],
	         [-1,  1, -1, 1],
	         [1, -1, 1,  -1]] * sig).sum;
	// Attenutate the delayed signals so they decay:
	sig = sig * [0.4, 0.37, 0.333, 0.3];

	// Here we give delay times in milliseconds, convert to seconds,
	// then compensate with ControlDur for the one-block delay
	// which is always introduced when using the LocalIn/Out fdbk loop
	deltimes = [101, 143, 165, 177] * 0.001 - ControlDur.ir;

	// Apply the delays and send the signals into the feedback loop
	LocalOut.ar(DelayC.ar(sig, deltimes, deltimes));

	// Now let's hear it:
	Out.ar(0, output);

}).play
)

// To stop it:
Ndef(\verb).free;