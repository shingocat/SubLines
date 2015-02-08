package org.strasa.middleware.util;

public class Runtimer {
	
	long startTime = System.nanoTime();
	
	public long start()
	{
		startTime = System.nanoTime();
		return startTime;
	}
	
	public double end() {
		long endTime = System.nanoTime();
		double durationTime = (endTime - startTime) / 1000000000.0;
		System.out.println("Duration : " + durationTime);
		return durationTime;
	}
}
