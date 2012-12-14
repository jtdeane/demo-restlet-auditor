package ws.cogito.auditing.resource;

import java.util.logging.Level;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import ws.cogito.auditing.model.AuditEvent;
import ws.cogito.auditing.service.AuditingServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Resource Provider for Audit Events providing ability to Create, Retrieve,
 * Update and Delete (CRUD). 
 * @author jeremydeane
 */
public class AuditEventResource extends ServerResource {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final XmlMapper xmlMapper = new XmlMapper();
	
	/**
	 * Returns an audit event
	 * @return Representation
	 * @throws Exception
	 */
	@Get
	public Representation getAuditEvent () throws Exception {
		
		//method varaible
		StringRepresentation stringRepresentation = null;
		
		//1. Retrieve the audit event
		String auditEventKey = (String) getRequest().getAttributes().get
				("auditEventKey");
		
		AuditEvent auditEvent = AuditingServices.retrieveAuditEvent(auditEventKey);
		
		
		if (auditEvent == null) {
			
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			
			return new EmptyRepresentation();
		}
		
		//2. Content Negotiation	
		Form requestHeaders = (Form) getRequest().getAttributes().get
				("org.restlet.http.headers");  
		
		String acceptHeader = requestHeaders.getFirstValue("accept");
		

		if (acceptHeader.contains("application/json")) {
			
			String json = objectMapper.writeValueAsString(auditEvent);
			
			stringRepresentation = new StringRepresentation(json,
					MediaType.APPLICATION_JSON);
			
			setStatus(Status.SUCCESS_OK);
			
		} else {
			
			//convert to XML representation
			String xml = xmlMapper.writeValueAsString(auditEvent);
			
			stringRepresentation = new StringRepresentation(xml,
					MediaType.APPLICATION_JSON);
			
			setStatus(Status.SUCCESS_OK);
		}
		
		//3. Return the representation
		return stringRepresentation;
	}
	
	/**
	 * Stores an audit event via PUT
	 * @param entity
	 * @throws Exception
	 * @return Representation
	 */
	@Put
	public Representation putAuditEvent(Representation entity) throws Exception {
		
		//1. Use the audit event key as the audit event identifier
		String auditEventKey = (String) getRequest().getAttributes().get
				("auditEventKey");
		
        //2. Convert to POJO
		MediaType entityType = entity.getMediaType();
		String entityText = entity.getText();
		
		AuditEvent auditEvent = convert(entityText, entityType);
        
        //3. Store the Audit Event
    	if (AuditingServices.storeAuditEvent(auditEvent, auditEventKey) == null) {
    		
    		//audit event was created
    		setStatus(Status.SUCCESS_CREATED);
    		
    		getLogger().log(Level.INFO, "PUT STATUS: " + Status.SUCCESS_CREATED);
    		
    	} else {
    		
    		//audit event was updated
    		setStatus(Status.SUCCESS_OK);
    		
    		getLogger().log(Level.INFO, "PUT STATUS: " + Status.SUCCESS_OK);
    	}
    	
    	return new StringRepresentation(entityText, entityType);
	}
	
	/**
	 * Stores an audit event via POST
	 * @param entity
	 * @return Representation
	 * @throws Exception
	 */
	@Post
	public Representation postAuditEvent(Representation entity) 
			throws Exception {
		
		//1. Convert to POJO
		MediaType entityType = entity.getMediaType();
		String entityText = entity.getText();
		
		AuditEvent auditEvent = convert(entityText, entityType);
		
		//2. Store the audit event using key within payload as the audit event identifier
    	if (AuditingServices.storeAuditEvent(auditEvent) == null) {
    		
    		//audit event was created
    		setStatus(Status.SUCCESS_CREATED);
    		
    		getLogger().log(Level.INFO, "PUT STATUS: " + Status.SUCCESS_CREATED);    		
    		
    	} else {
    		
    		//audit event was updated
    		setStatus(Status.SUCCESS_OK);
    		
    		getLogger().log(Level.INFO, "GET STATUS: " + Status.SUCCESS_OK);    		
    	}
    	
    	setLocationRef("http://localhost:8080/restlet-auditor/audit/event/"
    			+ auditEvent.getAuditEventKey());
		
    	return new StringRepresentation(entityText, entityType);
	}
	
	@Delete
	public void deleteAuditEvent() throws Exception {
		
		//1. Retrieve the audit event
		String auditEventKey = (String) getRequest().getAttributes().get
				("auditEventKey");
		
		//2. Delete the audit event
		AuditingServices.deleteAuditEvent(auditEventKey);
		
		//audit event was deleted
		setStatus(Status.SUCCESS_OK);
	}
	
	/**
	 * Convert Representation to POJO based on media type
	 * @param entity
	 * @return AuditEvent
	 * @throws Exception
	 */
	private AuditEvent convert(String entityText, MediaType entityType) 
			throws Exception {
	
		//method variables
		AuditEvent auditEvent = null;
		
        if (entityType.equals(MediaType.APPLICATION_JSON)) {
        	
        	auditEvent = (AuditEvent) objectMapper.readValue
        			(entityText, AuditEvent.class);

        } else {
        	
        	auditEvent = (AuditEvent) xmlMapper.readValue
        			(entityText, AuditEvent.class);
		}
        
        return auditEvent;
	}
}