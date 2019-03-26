package com.myRetail.persistence.model;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * This is a custom ID generator based on a SEQUENCE created in the database. The main purpose is to retrieve the
 * new-generated ID before its object is persisted.
 * 
 * @author WenKai
 *
 */
public class ProductIdGenerator extends IdentityGenerator implements Configurable {
	private IdentifierGenerator defaultGenerator;

	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		// Generate a new id before the persistence.
		Long prePersistedId = (Long) defaultGenerator.generate(session, object);

		if (object instanceof Product) {
			Product entity = (Product) object;
			ProductDetail productDetail = entity.getProductDetail();

			if (productDetail != null) {
				productDetail.setProductId(prePersistedId);
			}
		}

		// prePersistedId is returned and will be assigned the entity id of this object.
		return prePersistedId;
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		DefaultIdentifierGeneratorFactory identifierGeneratorFactory = new DefaultIdentifierGeneratorFactory();
		identifierGeneratorFactory.injectServices((ServiceRegistryImplementor) serviceRegistry);
		defaultGenerator = identifierGeneratorFactory.createIdentifierGenerator("sequence", type, params);
	}
}
