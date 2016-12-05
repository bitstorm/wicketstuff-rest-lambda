package org.wicketstuff.rest.lambda.resource;

import org.apache.wicket.request.resource.IResource;
import org.danekja.java.util.function.serializable.SerializableConsumer;
import org.wicketstuff.rest.utils.wicket.AttributesWrapper;

public class SimpleLambdaResource implements IResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514142899864801685L;
	
	protected final SerializableConsumer<AttributesWrapper> respondConsumer;

	public SimpleLambdaResource(SerializableConsumer<AttributesWrapper> respondConsumer) {
		this.respondConsumer = respondConsumer;
	}

	@Override
	public void respond(Attributes attributes) {
		AttributesWrapper attributesWrapper = new AttributesWrapper(attributes);
		respondConsumer.accept(attributesWrapper);
	}
}