package com.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ci.Base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SprintOne extends Base{

	@Parameters({ "browser" })
	@Test(description="Open cyclos banking application & do a new user registration")
	public void cyclosRegister(String browser) throws Exception{
		int retValue=runTest("1",browser);
		assertThat(0, is(retValue));
		assertThat(0, is(retValue));
	}
}
