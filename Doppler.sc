Doppler {
	var listenerDist, time, dist, type;

	*new { | listenerDist, time, dist, type|
		^super.newCopyArgs(listenerDist, time, dist, type);
	}

	src {
		case
		{ type == "saw" } { ^LFSaw.kr(time, 1, dist) }
		{ type == "noise"} { ^LFNoise2.kr(time, dist) }
	}

	distance {
		^hypot(listenerDist, this.src)
	}

	velocity {
		^Slope.kr(this.distance)
	}

	pitchRatio {
		^(344 - this.velocity) / 344
	}

	amplitude {
		^1/ this.distance.squared
	}

	azimuth {
		^atan2(this.src, listenerDist).linlin(-pi/2, pi/2, -1, 1)
	}

	rotation {
		^MulAdd(this.azimuth, pi, pi)
	}

	mix {
		^this.src.linlin(dist.neg, dist, this.amplitude, 1)
	}

	delay {|in|
		^DelayL.ar(in, 1, this.distance/344, this.amplitude)
	}
}

