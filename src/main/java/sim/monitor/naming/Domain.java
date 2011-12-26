/**
 * 
 */
package sim.monitor.naming;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author valer
 *
 */
public class Domain {

	private String domain;
	
	private List<DomainKey> categories = new ArrayList<DomainKey>();
	
	public Domain(String domain, String key, String value) {
		this.domain = domain;
		categories.add(new DomainKey(key, value));
	}

	private Domain(Domain otherMonitorNaming) {
		this.domain = otherMonitorNaming.domain;
		this.categories.addAll(otherMonitorNaming.categories);
	}
	
	public Domain add(String type, String value) {
		Domain monitorNaming = new Domain(this);
		monitorNaming.categories.add(new DomainKey(type, value));
		return monitorNaming;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	public Hashtable<String, String> getCategories() {
		Hashtable<String, String> result = new Hashtable<String, String>();
		for (DomainKey nsComp : categories) {
			result.put(nsComp.getKey(), nsComp.getValue());
		}
		return result;
	}
}
