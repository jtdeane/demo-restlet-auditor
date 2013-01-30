package ws.cogito.auditing.model;

import java.net.URL;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A set of audit events specific to an application
 * @author jeremydeane
 */
@XmlRootElement(name="audit-events")
public final class AuditEvents {

	@XmlAttribute
	private final String application;
	
	@XmlElement (name="event")	
	private final List<URL> events;

	@JsonCreator
	public AuditEvents (@JsonProperty("application") String application, 
			@JsonProperty("events") List<URL> events) {
		
		this.application = application;
		this.events = events;
	}
	
	/**
	 * Do not use - No-arg required by JAXB
	 */
    @SuppressWarnings("unused")
	private AuditEvents() {
	    this (null, null);
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