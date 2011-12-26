/**
 * 
 */
package sim.monitor.mbean;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.DoubleValueType;
import sim.monitor.internal.data.LongValueType;
import sim.monitor.internal.observer.MonitorProcessorObserver;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class MBeanManager implements MonitorProcessorObserver {

	private static MBeanManager mBeanManager = new MBeanManager();
	private MBeanServer mbServer;
	
	private DynamicMBean dynMBean = new DynamicMBean();
	
	public static MBeanManager instance() {
		return mBeanManager;
	}
	
	private MBeanManager() {
		mbServer = ManagementFactory.getPlatformMBeanServer();
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.observer.MonitorProcessorObserver#update(sim.monitor.naming.Name, sim.monitor.internal.data.Data)
	 */
	public void update(Name name, Data data) {
		ObjectName objectName = fromMonitorName(name);
		Set<ObjectInstance> instances = mbServer.queryMBeans(objectName, null);
		ObjectInstance mb = null;
		if (!instances.isEmpty()) {
			mb = instances.iterator().next();
			try {
				mbServer.unregisterMBean(mb.getObjectName());
			} catch (MBeanRegistrationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstanceNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mbServer.unregisterMBean(mb.getObjectName());
			//mbServer.registerMBean(object, name)MBean(mb.getObjectName());
		}
		//FIXME
		if (data.getValue().isLongType()) {
			dynMBean.getAttributes().put(name.getName(), new AttributeData(name.getDescription(), String.valueOf(((LongValueType) data.getValue()).getValue())));
		} else {
			dynMBean.getAttributes().put(name.getName(), new AttributeData(name.getDescription(), String.valueOf(((DoubleValueType) data.getValue()).getValue())));
		}
		try {
			mb = mbServer.registerMBean(dynMBean, objectName);
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private ObjectName fromMonitorName(Name name) {
		String domain = name.getDomain().getDomain();
		Hashtable<String, String> ns = name.getDomain().getCategories();
		try {
			return new ObjectName(domain, ns);
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
