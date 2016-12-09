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
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.wicketstuff.rest.lambda.request.mapper.RestResourceMapper;
import org.wicketstuff.rest.lambda.resource.SimpleLambdaResource;
import org.wicketstuff.rest.lambda.resource.TextOutputLambdaResource;
import org.wicketstuff.rest.utils.http.HttpMethod;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;

public class LambdaRestMounter
{
	private final WebApplication application;
	
	public LambdaRestMounter(WebApplication application) 
	{
		this.application = application;
	}

	public void post(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.POST, path, respondFunction, outputFunction);
	}
	
	public void post(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer) 
	{		
		mountRestResource(HttpMethod.POST, path, respondConsumer);
	}
	
	public void get(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.GET, path, respondFunction, outputFunction);
	}
	
	public void get(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer) 
	{		
		mountRestResource(HttpMethod.GET, path, respondConsumer);
	}
	
	public void put(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.PUT, path, respondConsumer);
	}
	
	public void put(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.PUT, path, respondFunction, outputFunction);
	}
	
	public void delete(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.DELETE, path, respondConsumer);
	}
	
	public void delete(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.DELETE, path, respondFunction, outputFunction);
	}
	
	public void options(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.OPTIONS, path, respondConsumer);
	}
	
	public void options(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.OPTIONS, path, respondFunction, outputFunction);
	}
	
	public void patch(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.PATCH, path, respondConsumer);
	}

	public void patch(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.PATCH, path, respondFunction, outputFunction);
	}
	
	public void head(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.HEAD, path, respondConsumer);
	}

	public void head(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.HEAD, path, respondFunction, outputFunction);
	}
	
	public void trace(final String path, final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		mountRestResource(HttpMethod.TRACE, path, respondConsumer);
	}

	public void trace(final String path, final SerializableFunction<AttributesWrapper, Object> respondFunction,
			final SerializableFunction<Object, String> outputFunction) 
	{		
		mountRestResource(HttpMethod.TRACE, path, respondFunction, outputFunction);
	}
	
	public ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path, 
											final SerializableFunction<AttributesWrapper, Object> respondFunction,
											final SerializableFunction<Object, String> outputFunction)
	{
		TextOutputLambdaResource resource = new TextOutputLambdaResource(respondFunction, outputFunction);
		return mountRestResource(httpMethod, path, resource);
	}

	public ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path, 
			final SerializableConsumer<AttributesWrapper> respondConsumer)
	{
		SimpleLambdaResource resource = new SimpleLambdaResource(respondConsumer);
		return mountRestResource(httpMethod, path, resource);
	}

	protected ResourceMapper mountRestResource(final HttpMethod httpMethod, final String path,
			IResource resource) 
	{
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
