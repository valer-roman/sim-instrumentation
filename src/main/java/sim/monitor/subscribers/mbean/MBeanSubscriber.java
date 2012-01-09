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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sim.monitor.Data;
import sim.monitor.naming.Container;
import sim.monitor.subscribers.Subscriber;

/**
 * The hit values are published to JMX Server through this subscriber. For each
 * container a dynamic MBean is registered.
 * 
 * @author val
 * 
 */
public class MBeanSubscriber implements Subscriber {

	private static final Logger logger = LoggerFactory
			.getLogger(MBeanSubscriber.class);

	private MBeanServer mbServer;

	private Map<Container, DynamicMBean> mbeans = new HashMap<Container, DynamicMBean>();

	public MBeanSubscriber() {
		mbServer = ManagementFactory.getPlatformMBeanServer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.subscribers.Subscriber#update(java.util.Collection)
	 */
	public void update(Collection<Data> datas) {
		for (Data data : datas) {
			DynamicMBean dynMBean = null;
			if (!mbeans.containsKey(data.getName().getContainer())) {
				dynMBean = new DynamicMBean();
				mbeans.put(data.getName().getContainer(), dynMBean);
			} else {
				dynMBean = mbeans.get(data.getName().getContainer());
			}
			boolean containsAttribute = dynMBean.getAttributes().containsKey(
					data.getName().getName());

			Container container = data.getName().getContainer();
			ObjectName objectName = null;
			try {
				objectName = new ObjectName(container.toString());
			} catch (MalformedObjectNameException e) {
				logger.error("Could not createObjectName for container "
						+ container, e);
				return;
			} catch (NullPointerException e) {
				logger.error("Null container received!", e);
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
					data.getName().getName(),
					new AttributeData(data.getName().getDescription(), data
							.getValue().toString(), data.getValue().getClass()
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
