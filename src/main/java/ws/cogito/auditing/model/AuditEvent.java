package ws.cogito.auditing.model;

import java.io.Serializable;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

/**
 * Application audit event information
 * @author jeremydeane
 */
@JsonIgnoreProperties({ "auditEventKey", "AuditEventLocation" })
@JacksonXmlRootElement (localName="audit-event" )
public final class AuditEvent implements Comparable<AuditEvent>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private final String application;
	private final String time;	
	private final String message;
	
	@JsonCreator
	public AuditEvent(@JsonProperty("application") String application, 
			@JsonProperty("time") String time, @JsonProperty("message") String message) {
		this.application=application;
		this.time=time;
		this.message=message;
	}
	
	/**
	 * Generate a unqiue key based on the applicaiton and time
	 * @return String
	 */
	public String getAuditEventKey () {
		return application + "-" + time;
	}
	
	public String getApplication() {
		return application;
	}

	public String getMessage() {
		return message;
	}

	public String getTime() {
		return time;
	}
	
	public URL getAuditEventLocation(String host, int port, String context) throws Exception {
		
		StringBuffer uri = new StringBuffer ("http://");
		uri.append(host);
		uri.append(":");
		uri.append(port);
		uri.append(context);
		uri.append("/audit/event/");
		uri.append(getAuditEventKey());
		
		return new URL(uri.toString());
	}

	@Override
	public String toString() {
		
		return application + "-" + time + "-" + message;
	}
	
	@Override
	public int compareTo(AuditEvent that) {
		return ComparisonChain.start()
				.compare(this.application, that.application)
				.compare(this.time, that.time, Ordering.natural())
				.compare(this.message, that.message)
				.result();
	}	

	@Override
	public boolean equals(Object obj) {
		AuditEvent that = (AuditEvent) obj;
		return Objects.equal(this.hashCode(), that.hashCode());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(application + time + message);
	}	
}