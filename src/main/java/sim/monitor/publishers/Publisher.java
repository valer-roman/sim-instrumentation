/**
 * 
 */
package sim.monitor.publishers;

import java.util.Collection;
import java.util.ServiceLoader;

import sim.monitor.Data;
import sim.monitor.naming.Name;
import sim.monitor.subscribers.Subscriber;

/**
 * @author val
 * 
 */
public abstract class Publisher {

	private Name name;
	protected boolean publishedWithSuffix;

	public Publisher(Name name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * @return the publishedWithSuffix
	 */
	public boolean isPublishedWithSuffix() {
		return publishedWithSuffix;
	}

	/**
	 * @param publishedWithSuffix
	 *            the publishedWithSuffix to set
	 */
	public void setPublishedWithSuffix(boolean publishedWithSuffix) {
		this.publishedWithSuffix = publishedWithSuffix;
	}

	public void hit(Collection<Data> datas) {
		Collection<Data> newDatas = processHit(datas);
		ServiceLoader<Subscriber> subscribers = ServiceLoader
				.load(Subscriber.class);
		for (Subscriber s : subscribers) {
			s.update(newDatas);
		}
	}

	protected abstract Collection<Data> processHit(Collection<Data> datas);

	public abstract String getSuffix();

}
