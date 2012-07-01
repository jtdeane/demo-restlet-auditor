package ws.cogito.auditing.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * An audit event URL
 * @author jeremydeane
 */
@XStreamAlias("event")
public final class AuditEventURL {

	@XStreamAsAttribute
	private final String url;

	public AuditEventURL (String host, int port, String context, String key) {
		
		StringBuffer uri = new StringBuffer ("http://");
		uri.append(host);
		uri.append(":");
		uri.append(port);
		uri.append(context);
		uri.append("/audit/event/");
		uri.append(key);
		
		this.url = uri.toString();
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return this.url;
	}	
}