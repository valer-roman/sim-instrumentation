/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author val
 *
 */
public final class Context extends HashSet<ContextEntry> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Context() {
	}

	Context(Map<String, Object> context) {
		if (context == null) {
			return;
		}
		for (Entry<String, Object> entry : context.entrySet()) {
			add(new ContextEntry(entry.getKey(), entry.getValue()));
		}
	}

	Context(Context context) {
		if (context == null) {
			return;
		}
		for (ContextEntry entry : context) {
			add(entry);
		}
	}

	Context withUndefinedKey() {
		Context context = new Context(this);
		context.add(ContextEntry.UNDEFINED);
		return context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (ContextEntry contextEntry : this) {
			if (sb.length() > 1) {
				sb.append(", ");
			}
			sb.append(contextEntry.toString());
		}
		sb.append("]");
		return sb.toString();
	}

}
