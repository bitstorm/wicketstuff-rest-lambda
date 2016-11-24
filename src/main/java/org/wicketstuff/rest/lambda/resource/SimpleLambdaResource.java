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
package org.wicketstuff.rest.lambda.resource;

import org.apache.wicket.request.resource.IResource;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;

public class SimpleLambdaResource implements IResource 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2761454069675861246L;

	final private SerializableFunction<AttributesWrapper, Object> respondFunction; 

	final private SerializableFunction<Object, String> outputTextFunction; 
	
	public SimpleLambdaResource(SerializableFunction<AttributesWrapper, Object> respondFunction,
			SerializableFunction<Object, String> outputTextFunction) 
	{
		this.respondFunction = respondFunction;
		this.outputTextFunction = outputTextFunction;
	}

	@Override
	public void respond(Attributes attributes) 
	{
		AttributesWrapper attributesWrapper = new AttributesWrapper(attributes);
		Object respondResult = respondFunction.apply(attributesWrapper);
		
		if (respondResult != null) 
		{
			attributesWrapper.getWebResponse()
			   .write(outputTextFunction.apply(respondResult));			
		}
	}
}
