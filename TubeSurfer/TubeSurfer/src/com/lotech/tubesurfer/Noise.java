package com.lotech.tubesurfer;

import java.util.Random;

public class Noise
{
	private static double noise[];
	
	public static double Noisei(int ind)
	{
		return noise[ind%100000];
	}
	
	private static double Interpolate(double a,double b,double x)
	{
		double ft = x*Math.PI;
		double f = (1 - Math.cos(ft))*0.5;

	    return a*(1-f) + b*f;
	}
	
	public static void Init()
	{
		Random rand = new Random();
		rand.setSeed(12345678);
		noise = new double[100000];
		for(int i=0;i<100000;i++)
			noise[i]=rand.nextDouble()*2.0-1.0;
	}
	
	public static double Noise2(double s,double t,int seed,double frequency,boolean btile)
	{
		double x = s*frequency;
		double y = t*frequency;
	    int ix = (int)x;
	    int iy = (int)y;
	    double fx = x-ix;
	    double fy = y-iy;
	    double ix1 = ix+1;
	    double iy1 = iy+1;
	    if(btile && ix+1>=frequency)
	        ix1 = 0;
	    if(btile && iy+1>=frequency)
	        iy1 = 0;
	    double v1 = Noisei((int)(ix+iy*frequency+seed));
	    double v2 = Noisei((int)(ix1+iy*frequency+seed));
	    double v3 = Noisei((int)(ix+iy1*frequency+seed));
	    double v4 = Noisei((int)(ix1+iy1*frequency+seed));
	    double i1 = Interpolate(v1,v2,fx);
	    double i2 = Interpolate(v3,v4,fx);
	    return Interpolate(i1,i2,fy);
	}
	
	public static double Fractal2(float s,float t,FractalParams fnps,boolean btile)
	{
		double total = 0;
		double maxamp = 0;
	    int nseed = fnps.seed;
	    for(int k=fnps.firstoctave;k<fnps.octaves+fnps.firstoctave;k++)
	    {
	    	double freq = Math.pow(fnps.fe,k);
	        nseed = (int)(fnps.seed+freq*freq*k);
	        double amp = Math.pow(fnps.persistance,k);
	        if(amp<0.001)
	        {
	            break;
	        }
	        maxamp += amp;
	        double n = Noise2(s,t,nseed,freq,btile);
	        total += n*amp;
	    }
	    return total/maxamp;
	}

	public class FractalParams
	{
	    public double persistance = 1.0;
	    public double fe = 2.0;
	    public int firstoctave = 1;
	    public int octaves = 6;
	    public int seed = 5000;
	    public double kblend = 1.0;
	}
}
