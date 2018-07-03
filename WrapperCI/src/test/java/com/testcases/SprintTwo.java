package com.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ci.Base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SprintTwo extends Base{

	@Parameters({ "browser" })
	@Test(description="Open cyclos and login into cyclos using valid credentials")
	public void cyclosRegister(String browser) throws Exception{
		int retValue=runTest("2",browser);
		assertThat(0, is(retValue));
	}
}
