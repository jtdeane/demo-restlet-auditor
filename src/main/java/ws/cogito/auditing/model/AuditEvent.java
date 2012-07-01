package ws.cogito.auditing.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Application audit event information
 * @author jeremydeane
 */
@XStreamAlias("audit-event")
@JsonIgnoreProperties({ "auditEventKey" })
public final class AuditEvent {
	
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

	@Override
	public String toString() {
		
		return application + "-" + time + "-" + message;
	}
}