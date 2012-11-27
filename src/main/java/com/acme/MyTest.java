package com.acme;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class MyTest implements JavaSamplerClient{

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		System.out.println("runTest called by " + Thread.currentThread().getId());
		return null;
	}

	@Override
	public void setupTest(JavaSamplerContext arg0) {
		System.out.println("SetupTest called by " + Thread.currentThread().getId());
		
	}

	@Override
	public void teardownTest(JavaSamplerContext arg0) {
		System.out.println("teardownTest called by " + Thread.currentThread().getId());
		
	}

}
