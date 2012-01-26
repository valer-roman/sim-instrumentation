/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import sim.monitor.Hit;
import sim.monitor.Tags;
import sim.monitor.subscribers.Subscriber;

/**
 * The hit values are published to JMX Server through this subscriber. For each
 * container a dynamic MBean is registered.
 *
 * @author val
 *
 */
public class MBeanSubscriber implements Subscriber {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(MBeanSubscriber.class);

	private MBeanServer mbServer;

	private Map<Tags, DynamicMBean> mbeans = new HashMap<Tags, DynamicMBean>();

	public MBeanSubscriber() {
		mbServer = ManagementFactory.getPlatformMBeanServer();
	}

	private static ObjectName fromTags(Tags tags) {
		StringBuilder sb = new StringBuilder(Tags.DOMAIN);
		if (tags.getTags().length > 0) {
			sb.append(":");
		} else {
			sb.append(":name=Monitor");
		}
		for (int i = 0; i < tags.getTags().length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append("tag" + i + "=" + tags.getTags()[i]);
		}
		try {
			return new ObjectName(sb.toString());
		} catch (MalformedObjectNameException e) {
			logger.error("Could not createObjectName for container " + tags, e);
			return null;
		} catch (NullPointerException e) {
			logger.error("Null container received!", e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.subscribers.Subscriber#update(java.util.Collection,
	 * sim.monitor.Tags, java.lang.String, java.lang.String)
	 *
	 * FIXME maybe we could avoid somehow the synchronized ...
	 */
	public synchronized void update(Collection<Hit> hits, Tags tags,
			String name,
			String description) {
		logger.info("updating mbean for attribute " + name);
		for (Hit hit : hits) {
			logger.info("Context:" + hit.getContext());
			DynamicMBean dynMBean = null;
			if (!mbeans.containsKey(tags)) {
				dynMBean = new DynamicMBean();
				mbeans.put(tags, dynMBean);
			} else {
				dynMBean = mbeans.get(tags);
			}
			boolean containsAttribute = dynMBean.getAttributes().containsKey(
					name);

			ObjectName objectName = fromTags(tags);
			if (objectName == null) {
				return;
			}
			Set<ObjectInstance> instances = mbServer.queryMBeans(objectName, null);
			ObjectInstance mb = null;

			if (!containsAttribute && !instances.isEmpty()) {
				mb = instances.iterator().next();
				try {
					mbServer.unregisterMBean(mb.getObjectName());
				} catch (MBeanRegistrationException e) {
					logger.error(
							"MBean could not be unregistered for ObjectName: "
									+ mb.getObjectName(), e);
				} catch (InstanceNotFoundException e) {
					logger.error(
							"Instance for objectName " + mb.getObjectName()
							+ " could not be found!", e);
				}
			}


			dynMBean.getAttributes().put(
					name,
					new AttributeData(description, hit
							.getValue().toString(),
							hit.getValue().getClass()
							.getName()));

			if (!containsAttribute || instances.isEmpty()) {
				try {
					mb = mbServer.registerMBean(dynMBean, objectName);
				} catch (InstanceAlreadyExistsException e) {
					logger.error("Instance for objectName " + objectName
							+ " already exists!", e);
				} catch (NotCompliantMBeanException e) {
					logger.error("The mbean for " + objectName
							+ " is not compliant!", e);
				} catch (MBeanRegistrationException e) {
					logger.error(
							"MBean could not be registered for ObjectName: "
									+ mb.getObjectName(), e);
				}
			}
		}
	}

}
