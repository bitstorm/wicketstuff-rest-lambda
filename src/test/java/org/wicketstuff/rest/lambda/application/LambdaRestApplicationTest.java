/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.lambda.application;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class LambdaRestApplicationTest extends WicketTestCase 
{
	final Map<String, Object> map = new HashMap<>();

	public LambdaRestApplicationTest() 
	{
		map.put("integer", 123);
		map.put("string", "message");
	}
	
	@Override
	protected WicketTester newWicketTester(WebApplication app) 
	{
		WicketTester tester = super.newWicketTester(app);

		LambdaRestMounter restMounter = new LambdaRestMounter(app);
		
		restMounter.get("/testget", (attributes) -> "hello!", Object::toString);
		restMounter.post("/testjson", (attributes) -> map, JSONObject::valueToString);

		restMounter.options("/testparam/${id}", (attributes) -> {
				PageParameters pageParameters = attributes.getPageParameters();
				return pageParameters.get("id");
			}
		, Object::toString);
		
		return tester;
	}
	
	@Test
	public void testResources() throws Exception 
	{
		tester.getRequest().setMethod("POST");
		tester.executeUrl("./testget");
		
		assertTrue(tester.getLastResponseAsString().isEmpty());
		
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./testget");
		
		assertEquals("hello!", tester.getLastResponseAsString());

		tester.getRequest().setMethod("POST");
		tester.executeUrl("./testjson");
		
		assertEquals(JSONObject.valueToString(map), 
				tester.getLastResponseAsString());	
		
		tester.getRequest().setMethod("OPTIONS");
		tester.executeUrl("./testparam/45");
		
		assertEquals("45", tester.getLastResponseAsString());
	}
}
