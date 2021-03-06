/**
 *
 */
package sim.monitor.subscribers.mbean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.ImmutableDescriptor;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

/**
 * @author valer
 *
 */
public class DynamicMBean implements javax.management.DynamicMBean {

	private Map<String, AttributeData> attributes = new HashMap<String, AttributeData>();

	private String description;

	DynamicMBean(String description) {
		this.description = description;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, AttributeData> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, AttributeData> attributes) {
		this.attributes = attributes;
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String key) throws AttributeNotFoundException,
			MBeanException, ReflectionException {
		return attributes.get(key);
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#getAttributes(java.lang.String[])
	 */
	public AttributeList getAttributes(String[] arg0) {
       AttributeList list = new AttributeList();
        for (Entry<String, AttributeData> entry : attributes.entrySet()) {
			Object dvt = entry.getValue().getValue();
            if (dvt != null) {
            	list.add(new Attribute(entry.getKey(), dvt));
            }
        }
        return list;
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#getMBeanInfo()
	 */
	public MBeanInfo getMBeanInfo() {
		MBeanAttributeInfo[] mbAttributeInfos = new MBeanAttributeInfo[attributes.size()];
        Iterator<String> it = attributes.keySet().iterator();
        for (int i = 0; i < mbAttributeInfos.length; i++) {
            String name = it.next();
            AttributeData attrData = attributes.get(name);
            String description = attrData.getDescription();
            //String dvt = attrData.getValue();
			Descriptor descriptor = new ImmutableDescriptor("openType="
					+ attrData.getSimpleType().toString(), "originalType="
					+ attrData.getOriginalType().getName());
			mbAttributeInfos[i] = new MBeanAttributeInfo(name, attrData
					.getOriginalType().getName(), // "java.lang.String",
                    description,
                    true,   // isReadable
					false, // isWritable
					false, descriptor); // isIs
        }
		Descriptor descriptor = new ImmutableDescriptor("immutableinfo=false",
				"mxbean=true");
		MBeanInfo mbInfo = new MBeanInfo(this.getClass().getName(),
				this.description == null ? "default description"
						: this.description, mbAttributeInfos, null, null, null,
				descriptor);
		return mbInfo;
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#invoke(java.lang.String, java.lang.Object[], java.lang.String[])
	 */
	public Object invoke(String arg0, Object[] arg1, String[] arg2)
			throws MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#setAttribute(javax.management.Attribute)
	 */
	public void setAttribute(Attribute arg0) throws AttributeNotFoundException,
			InvalidAttributeValueException, MBeanException, ReflectionException {
		// TODO Auto-generated method stub
		return;
	}

	/* (non-Javadoc)
	 * @see javax.management.DynamicMBean#setAttributes(javax.management.AttributeList)
	 */
	public AttributeList setAttributes(AttributeList arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
