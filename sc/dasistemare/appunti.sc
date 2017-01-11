http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/Core/Kernel/Function.html
http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/ServerArchitecture/Server.html
http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/GUI/Cocoa-GUI/SCStethoscope.html
http://supercollider.svn.sourceforge.net/viewvc/supercollider/trunk/common/build/Help/Tutorials/Getting-Started/Groups.html


{ [SinOsc.ar(440, 0, 0.2), SinOsc.ar(442, 0, 0.2)] }.play;

(
"Call me,".postln;
"Ishmael.".postln;
)


{ Pan2.ar(PinkNoise.ar(0.2), SinOsc.kr(1.0)) }.play;


{ Pan2.ar(PinkNoise.ar(0.2), -0.3) }.play; // slightly to the left

// hear the difference, LinPan having a slight drop in the middle...
{LinPan2.ar(SinOsc.ar(440), Line.kr(-1,1,5))}.play

// ... whereas Pan2 is more smooth
{Pan2.ar(SinOsc.ar(440), Line.kr(-1,1,5))}.play

// other examples
SynthDef("help-Pan2", { Out.ar(0, Pan2.ar(PinkNoise.ar(0.4), FSinOsc.kr(2), 0.3)) }).play;

// combine two stereo arrays
(
{
    var a, b;
    a = [SinOsc.ar(440, 0, 0.2), Saw.ar(662, 0.2)];
    b = [SinOsc.ar(442, 0, 0.2), Saw.ar(660, 0.2)];
    Mix([a, b]).postln;
}.play;
)


// Look at the post window for frequencies and indices
(
    var n = 8;
    {
        Mix.fill(n, { arg index;
            var freq;
            index.postln;
            freq = 440 + index;
            freq.postln;
            SinOsc.ar(freq , 0, 1 / n)
        })
    }.play;
)


{ SinOsc.ar( MouseX.kr(1, 1000), mul:0.7) }.scope;

// Look at the post window for frequencies and indices
(
    var n = 8;
    {
        Mix.fill(n, { arg index;
            var freq;
            index.postln;
            freq = 440 + index;
            freq.postln;
            SinOsc.ar(freq , 0, 1 / n)
        })
    }.scope;
)

{ SinOsc.ar( Line.kr(1, 20000, 20), mul:0.7) }.scope;



{ [SinOsc.ar(440, 0, 0.2), SinOsc.ar(442, 0, 0.2)] }.play;



f = { "Function evaluated".postln; };
f.value;

f = { arg a, b; a - b; };
f.value(6, 3);
f = { |a, b| a - b; };


// example
(
var a, b, c;
a = { [100, 200, 300].choose };	// a Function
b = { 10.rand + 1 };	// another Function
c = a + b; 	// c is a Function.
c.value.postln;	// evaluate c and print the result
)

{ var ampOsc;
    ampOsc = SinOsc.kr(0.5, 1.5pi, 0.5, 0.5);
    SinOsc.ar(440, 0, ampOsc);
  }.play;


{ PinkNoise.ar(0.2) + Saw.ar(660, 0.2) }.plot; //default 0.01
  { PinkNoise.ar(0.2) + Saw.ar(660, 0.2) }.plot(1); // 1 sec



{
    [SinOsc.ar(440, 0, 0.2), SinOsc.ar(442, 0, 0.2)]
  }.play(Server.internal);
  Server.internal.scope; //or s.scope if internal is default


(

{ // Open the Function

SinOsc.ar(	// Make an audio rate SinOsc

440, // frequency of 440 Hz, or the tuning A

0, // initial phase of 0, or the beginning of the cycle

0.2) // mul of 0.2

}.play;	// close the Function and call 'play' on it

)

(

{ var ampOsc;

ampOsc = SinOsc.kr(0.5, 1.5pi, 0.5, 0.5);

SinOsc.ar(440, 0, ampOsc);

}.play;

)


SynthDef.new("tutorial-SinOsc",
    { Out.ar(0, SinOsc.ar(440, 0, 0.2)) }
  ).play;


x = { SinOsc.ar(660, 0, 0.2) }.play;
  y = SynthDef.new("myDef",
      { Out.ar(0, SinOsc.ar(440, 0, 0.2)) }).play;
  x.free; // free just x
  y.free; // free just y



 b = Buffer.alloc(s, 512, 1);
  b.cheby([1,0,1,1,0,1]);
  x = play({
    Shaper.ar(
      b,
      SinOsc.ar(300, 0, Line.kr(0,1,6)),
      0.5
    )
  });


Help.gui;

Array

{ Mix.new(
    [SinOsc.ar(440, 0, 0.2), Saw.ar(660, 0.2)]
  ).postln }.play;

 {
    var a, b;
    a = [SinOsc.ar(440, 0, 0.2), Saw.ar(662, 0.2)];
    b = [SinOsc.ar(442, 0, 0.2), Saw.ar(660, 0.2)];
    Mix([a, b]).postln;
  }.play;


  (
    var n = 8;
    {
      Mix.fill(n,
        { SinOsc.ar(500 + 500.0.rand, 0, 1 / n) }
      )
    }.play;
  )


 (
    var n = 8;
    {
      Mix.fill(n,
        {
          arg index;
          var freq;
          index.postln;
          freq = 440 + index;
          freq.postln;
          SinOsc.ar(freq , 0, 1 / n);
        }
      )
    }.play;
  )