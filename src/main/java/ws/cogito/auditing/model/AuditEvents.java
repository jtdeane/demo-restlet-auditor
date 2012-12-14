package ws.cogito.auditing.model;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * A set of audit events specific to an application
 * @author jeremydeane
 */
public final class AuditEvents {

	@JacksonXmlProperty(isAttribute=true)
	private final String application;
	
	@JsonProperty("event")
	@JacksonXmlElementWrapper(localName = "events")
	private final List<URL> events;
	
	public AuditEvents (String application, List<URL> events) {
		this.application = application;
		this.events = events;
	}

	public String getApplication() {
		return application;
	}

	public List<URL> getEvents() {
		return events;
	}

	@Override
	public String toString() {
		
		StringBuffer output = new StringBuffer (application);
		output.append(" Application Events: \n");
		
		for (URL auditEventLocation : events) {
			output.append(auditEventLocation);
			output.append("\n");
		}
		
		return output.toString();
	}	
}