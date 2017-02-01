//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//--------------------------------------------------------------- 003 - SCRATCH -
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------
//---------------------------------------------------- PRINCIPI BASILARI DI WFS -
//--------------------------------------------------- A PARTIRE DAL SIGNIFICATO -
//-------------------------------------------------------- WAVE FIELD SYNTHESIS -
//-------------------------------------------------------------------------------

//-------------------------------------------------------------------------------
//- DEFINIRE UNA SORGENTE VIRTUALE (x,y)
//- DEFINIRE IL SISTEMA DI DIFFUSIONE n.speakers(x,y)
//- CALCOLARE LA DISTANZA TRA LA SORGENTE VIRTUALE E CIASCUN DIFFUSORE d
//- CALCOLARE IL TEMPO DI PERCORRENZA TRA SORGENTE E CIASCUN DIFFUSORE t = d/340
//- CALCOLARE IL DECADIMENTO DELL'AMPIEZZA A CIASCUNA DISTANZA a = 1/d
//- RITARDARE IL SUONO PER CIASCUN DIFFUSORE DEL RELATIVO TEMPO d E MOLTIPLICARE PER L'AMPIEZZA a
//-------------------------------------------------------------------------------

// array regolare di diffusori
p = 10.collect{ |i| Point(i.linlin(0, 9, 0, 324), 5) }.post;

(
SynthDef(\baseWFS,{
    var excitation = Dust.ar(20) * 0.2;
    var in = Klank.ar(`[20.collect{ exprand(100,3000) }], excitation);
    var x = MouseX.kr(-20,20);
    var y = MouseY.kr(-30,5); //this is in meters.
    var max = 2 ** ( (3 * 44100).log2.roundUp(1).max(0) );
    var buf = LocalBuf(max,1).clear;
    var dists = p.collect{ |point| Point(x,y).dist(point) };
    var delayTimes = dists / 340;
    var amps = dists.collect{ |d| d.reciprocal.min(1.0) };
    var delays = BufDelayL.ar(buf, in, delayTimes);
    Out.ar(0, delays * amps )
}).play
)