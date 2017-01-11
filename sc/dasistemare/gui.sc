s.boot;

(
SynthDef("sinewave", {arg myfreq=261.626, myvol=0.5;
  Out.ar(0, SinOsc.ar(myfreq,0,myvol))
  }).send(s);
)

(
var awindow, myslider, myknob, mybutton;

awindow=FlowView.new(windowTitle:"Example");
myslider=EZSlider(awindow, label:"Volume",controlSpec:[0,1], action:{|mv| x.set("myvol",mv.value)}, initVal:0.5);
myknob=EZKnob(awindow, label:"Pitch",controlSpec:\freq, action:{|mn| x.set("myfreq",mn.value)}, initVal:261.626);

mybutton=Button(awindow, Rect(20,20, 50, 25))
  .states_([["Off", Color.black, Color.grey],
    ["On",Color.black,Color.yellow]])
  .action_({ arg vbutton;
     if(vbutton.value==1,
      {(
        x=Synth("sinewave");
        myslider.value=0.5;
        myknob.value=261.262;
         )}
     ,
    {x.free});
    });

mybutton.value=0;
)

Quarks.gui