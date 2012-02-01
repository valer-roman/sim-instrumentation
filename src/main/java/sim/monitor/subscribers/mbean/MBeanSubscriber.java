/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

import sim.monitor.ContextEntry;
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

	private Map<ObjectName, DynamicMBean> mbeans = new HashMap<ObjectName, DynamicMBean>();

	public MBeanSubscriber() {
		mbServer = ManagementFactory.getPlatformMBeanServer();
	}

	private static ObjectName fromTags(Tags tags, String monitorName) {
		StringBuilder sb = new StringBuilder(Tags.DOMAIN);
		sb.append(":");
		if (tags.getTags().length > 0) {
			sb.append("tags=");
			for (int i = 0; i < tags.getTags().length; i++) {
				if (i > 0) {
					sb.append(";");
				}
				String tag = tags.getTags()[i];
				sb.append(tag);
			}
			sb.append(",");
		}
		sb.append("name=" + toObjectName(monitorName));
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

	private static String toObjectName(String monitorName) {
		return monitorName;
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
			String monitorName, String monitorDescription, String name,
			String description) {
		logger.info("updating mbean for attribute " + monitorName);
		for (Hit hit : hits) {
			logger.info("Context:" + hit.getContext());

			List<String> attributes = new ArrayList<String>();
			attributes.add(name);
			for (ContextEntry entry : hit.getContext()) {
				attributes.add(name + "." + entry.getKey() + "."
						+ entry.getValue());
			}

			ObjectName objectName = fromTags(tags, monitorName);
			if (objectName == null) {
				return;
			}
			DynamicMBean dynMBean = null;
			if (!mbeans.containsKey(objectName)) {
				dynMBean = new DynamicMBean(monitorDescription);
				mbeans.put(objectName, dynMBean);
			} else {
				dynMBean = mbeans.get(objectName);
			}

			for (String attributeName : attributes) {
				boolean containsAttribute = dynMBean.getAttributes()
						.containsKey(attributeName);

				Set<ObjectInstance> instances = mbServer.queryMBeans(
						objectName, null);
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
						attributeName,
						new AttributeData(description, hit.getValue()
								.toString(), hit.getValue().getClass()
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
										+ objectName, e);
					}
				}
			}
		}
	}

}
