package ws.cogito.auditing.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * A set of audit events specific to an application
 * @author jeremydeane
 */
@XStreamAlias("audit-events")
public final class AuditEvents {

	@XStreamAsAttribute
	private final String application;
	
	@XStreamImplicit
	private final List<AuditEventURL> events;
	
	public AuditEvents (String application, List<AuditEventURL> events) {
		this.application = application;
		this.events = events;
	}

	public String getApplication() {
		return application;
	}

	public List<AuditEventURL> getEvents() {
		return events;
	}

	@Override
	public String toString() {
		
		StringBuffer output = new StringBuffer (application);
		output.append(" Application Events: \n");
		
		for (AuditEventURL auditEventURL : events) {
			output.append(auditEventURL);
			output.append("\n");
		}
		
		return output.toString();
	}	
}