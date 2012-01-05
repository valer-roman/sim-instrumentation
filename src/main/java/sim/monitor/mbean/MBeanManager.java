/**
 * 
 */
package sim.monitor.mbean;

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

import sim.monitor.Data;
import sim.monitor.naming.Container;

/**
 * @author valer
 *
 */
public class MBeanManager {

	private static MBeanManager mBeanManager = new MBeanManager();
	private MBeanServer mbServer;
	
	private Map<Container, DynamicMBean> mbeans = new HashMap<Container, DynamicMBean>();
	//private DynamicMBean dynMBean = new DynamicMBean();
	
	public static MBeanManager instance() {
		return mBeanManager;
	}
	
	private MBeanManager() {
		mbServer = ManagementFactory.getPlatformMBeanServer();
	}
	
	public void update(Collection<Data> datas) {
		//FIXME
		for (Data data : datas) {
			DynamicMBean dynMBean = null;
			if (!mbeans.containsKey(data.getName().getContainer())) {
				dynMBean = new DynamicMBean();
				mbeans.put(data.getName().getContainer(), dynMBean);
			} else {
				dynMBean = mbeans.get(data.getName().getContainer());
			}
			boolean containsAttribute = dynMBean.getAttributes().containsKey(data.getName().getName());
			
			ObjectName objectName = null;
			try {
				objectName = new ObjectName(data.getName().getContainer().toString());
			} catch (MalformedObjectNameException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Set<ObjectInstance> instances = mbServer.queryMBeans(objectName, null);
			ObjectInstance mb = null;
			
			if (!containsAttribute && !instances.isEmpty()) {
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
			
			
			dynMBean.getAttributes().put(data.getName().getName(), new AttributeData(data.getName().getDescription(), data.getValue().toString(), data.getValue().getClass().getName()));
			
			if (!containsAttribute || instances.isEmpty()) {
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
		}
	}

	/*
	private ObjectName fromMonitorName(Domain domain) {
		Hashtable<String, String> ns = domain.getCategories();
		try {
			return new ObjectName(domain.getDomain(), ns);
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/
}
