/**
 * 
 */
package sim.monitor.naming;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author valer
 *
 */
public class Container {

	private String domain;
	private Map<String, String> keys = new HashMap<String, String>();
	
	private Container(String domain, Map<String, String> keys) {
		this.domain = domain;
		this.keys = keys;
	}
	
	public static Container parse(String container) {
		int indexOfColon = container.indexOf(":");
		if (indexOfColon == -1) {
			return null;
		}
		String domain = container.substring(0, indexOfColon);
		String keysString = container.substring(indexOfColon + 1);
		StringTokenizer keysST = new StringTokenizer(keysString, ",");
		Map<String, String> keys = new HashMap<String, String>();
		while(keysST.hasMoreTokens()) {
			String keyElement = keysST.nextToken();
			int indexOfEqual = keyElement.indexOf("=");
			if (indexOfEqual == -1) {
				continue;
			}
			String key = keyElement.substring(0, indexOfEqual);
			String value = keyElement.substring(indexOfEqual+1);
			keys.put(key, value);
		}
		return new Container(domain, keys);
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @return the keys
	 */
	public Map<String, String> getKeys() {
		return keys;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null || !(arg0 instanceof Container)) {
			return false;
		}
		Container oc = (Container) arg0;
		if (!(new EqualsBuilder().append(this.domain, oc.getDomain()).isEquals())) {
			return false;
		}
		if (!(new EqualsBuilder().append(keys.size(), oc.getKeys().size()).isEquals())) {
			return false;
		}
		for (String key : getKeys().keySet()) {
			if (!oc.getKeys().containsKey(key)) {
				return false;
			}
			if (!(new EqualsBuilder().append(getKeys().get(key), oc.getKeys().get(key)).isEquals())) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder().append(domain);
		for (String key : getKeys().keySet()) {
			hcb.append(key).append(keys.get(key));
		}
		return new HashCodeBuilder().hashCode();
	}
	
	private String keysToString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : getKeys().keySet()) {
			if (first) {
				first = false;
			} else { 
				sb.append(",");
			}
			sb.append(key).append("=").append(keys.get(key));
		}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return domain + ":" + keysToString();
	}
}
