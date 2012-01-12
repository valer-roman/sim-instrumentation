/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A container is used to group monitors.
 * 
 * Monitors doing different measurements on the same application resource should
 * be place on the same container. The container is a substitute, in the
 * instrumentation tool, for the monitored resource.
 * 
 * @author val
 */
public class Tags {

	public static final String DOMAIN = "sim.monitoring";

	private String[] tags;

	Tags(String... tags) {
		this.tags = tags;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Tags)) {
			return false;
		}
		Tags oc = (Tags) obj;
		return new EqualsBuilder().append(this.tags, oc.tags).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.tags).toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.tags.toString();
	}

}
