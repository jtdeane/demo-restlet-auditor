package ws.cogito.auditing.resource;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ws.cogito.auditing.model.AuditEvents;
import ws.cogito.auditing.service.AuditingServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Resource provider for Audit Events Resource providing ability to 
 * retrieve representations of Audit Event lists.
 * @author jeremydeane
 *
 */
public class AuditEventsResource extends ServerResource {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final XmlMapper xmlMapper = new XmlMapper();
	
	/**
	 * Returns a list of audit events for a given application
	 * @return Representation
	 * @throws Exception
	 */
	@Get
	public Representation getAuditEvents () throws Exception {
		
		//method varaible
		StringRepresentation stringRepresentation = null;
		
		//1. Retrieve the audit event
		String application = (String) getRequest().getAttributes().
				get("application");
		
		AuditEvents auditEvents = AuditingServices.retrieveAuditEvents
				(application,getOriginalRef().getHostDomain(), 
						getOriginalRef().getHostPort(), "/restlet-auditor");
		
		//2. Content Negotiation	
		Form requestHeaders = (Form) getRequest().getAttributes().get
				("org.restlet.http.headers");  
		
		String acceptHeader = requestHeaders.getFirstValue("accept");
		

		if (acceptHeader.contains("application/json")) {
			
			String json = objectMapper.writeValueAsString(auditEvents);
			
			stringRepresentation = new StringRepresentation(json,
					MediaType.APPLICATION_JSON);
			
			setStatus(Status.SUCCESS_OK);
			
		} else {
			
			//convert to XML representation
			String xml = xmlMapper.writeValueAsString(auditEvents);
			
			stringRepresentation = new StringRepresentation(xml,
					MediaType.APPLICATION_JSON);
			
			setStatus(Status.SUCCESS_OK);
		}
		
		//3. Return the representation
		return stringRepresentation;
	}
}