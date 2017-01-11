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