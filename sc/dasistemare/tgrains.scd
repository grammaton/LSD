// read sound
b = Buffer.read(s, "sounds/a11wlk01.wav");

(
{
	var trate, dur;
	trate = MouseY.kr(2,200,1);
	dur = 4 / trate;
	TGrains.ar(2, Impulse.ar(trate), b.bufnum, 1, MouseX.kr(0,BufDur.kr(b.bufnum)), dur, 0, 0.1, 2);
}.scope(2, zoom: 4);
)

(
{
	var trate, dur, clk, pos, pan;
	trate = MouseY.kr(8,120,1);
	dur = 12 / trate;
	clk = Impulse.kr(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum)) + TRand.kr(0, 0.01, clk);
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(2, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)

// 4 channels
(
{
	var trate, dur, clk, pos, pan;
	trate = MouseY.kr(8,120,1);
	dur = 12 / trate;
	clk = Impulse.kr(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum)) + TRand.kr(0, 0.01, clk);
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(4, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(4, zoom: 4);
)

(
{
	var trate, dur, clk, pos, pan;
	trate = MouseY.kr(8,120,1);
	dur = 4 / trate;
	clk = Dust.kr(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum)) + TRand.kr(0, 0.01, clk);
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(2, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)



(
{
	var trate, dur, clk, pos, pan;
	trate = LinExp.kr(LFTri.kr(MouseY.kr(0.1,2,1)),-1,1,8,120);
	dur = 12 / trate;
	clk = Impulse.ar(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum));
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(2, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)


(
{
	var trate, dur, clk, pos, pan;
	trate = 12;
	dur = MouseY.kr(0.2,24,1) / trate;
	clk = Impulse.kr(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum)) + TRand.kr(0, 0.01, clk);
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(2, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)

(
{
	var trate, dur, clk, pos, pan;
	trate = 100;
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = Integrator.kr(BrownNoise.kr(0.001));
	pan = WhiteNoise.kr(0.6);
	TGrains.ar(2, clk, b.bufnum, 1, pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)

(
{
	var trate, dur, clk, pos, pan;
	trate = MouseY.kr(1,400,1);
	dur = 8 / trate;
	clk = Impulse.kr(trate);
	pos = MouseX.kr(0,BufDur.kr(b.bufnum));
	pan = WhiteNoise.kr(0.8);
	TGrains.ar(2, clk, b.bufnum, 2 ** WhiteNoise.kr(2), pos, dur, pan, 0.1);
}.scope(2, zoom: 4);
)




(
{
	var trate, dur;
	trate = MouseY.kr(2,120,1);
	dur = 1.2 / trate;
	TGrains.ar(2, Impulse.ar(trate), b.bufnum, (1.2 ** WhiteNoise.kr(3).round(1)), MouseX.kr(0,BufDur.kr(b.bufnum)), dur, WhiteNoise.kr(0.6), 0.1);
}.scope(2, zoom: 4);
)

// free sound
b.free;