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

import org.apache.wicket.core.request.mapper.ResourceMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.ResourceReference;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.wicketstuff.rest.lambda.request.mapper.RestResourceMapper;
import org.wicketstuff.rest.lambda.resource.SimpleLambdaResource;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;

public class LambdaRestMounter
{
	private final WebApplication application;
	
	public LambdaRestMounter(WebApplication application) 
	{
		this.application = application;
	}

	public void post(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.post(path, respondFunction, (object) -> object.toString());
	}
	
	public void post(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.POST, path, respondFunction, outputTextFunction);
	}

	public void get(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.get(path, respondFunction, (object) -> object.toString());
	}
	
	public void get(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.GET, path, respondFunction, outputTextFunction);
	}
	
	public void put(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.put(path, respondFunction, (object) -> object.toString());
	}
	
	public void put(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.PUT, path, respondFunction, outputTextFunction);
	}
	
	public void delete(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.delete(path, respondFunction, (object) -> object.toString());
	}
	
	public void delete(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.DELETE, path, respondFunction, outputTextFunction);
	}
	
	public void options(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.options(path, respondFunction, (object) -> object.toString());
	}
	
	public void options(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.OPTIONS, path, respondFunction, outputTextFunction);
	}
	
	public void patch(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.patch(path, respondFunction, (object) -> object.toString());
	}

	public void patch(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.PATCH, path, respondFunction, outputTextFunction);
	}
	
	public void head(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.head(path, respondFunction, (object) -> object.toString());
	}

	public void head(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.HEAD, path, respondFunction, outputTextFunction);
	}
	
	public void trace(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction)
	{
		this.trace(path, respondFunction, (object) -> object.toString());
	}

	public void trace(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputTextFunction) 
	{		
		mountRestResource(HttpMethod.TRACE, path, respondFunction, outputTextFunction);
	}
	
	public ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path, 
											final SerializableFunction<AttributesWrapper, Object> respondFunction,
											final SerializableFunction<Object, String> outputTextFunction)
	{
		SimpleLambdaResource resource = new SimpleLambdaResource(respondFunction, outputTextFunction);
		ResourceReference reference = ResourceReference.of(path + "_" + httpMethod.name(), () -> resource);
		
		if (reference.canBeRegistered())
		{
			application.getResourceReferenceRegistry().registerResourceReference(reference);
		}
		
		RestResourceMapper mapper = new RestResourceMapper(path, reference, httpMethod);
		application.mount(mapper);
		return mapper;
	}
}
