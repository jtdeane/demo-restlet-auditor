package ws.cogito.auditing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ws.cogito.auditing.model.AuditEvent;
import ws.cogito.auditing.model.AuditEventURL;
import ws.cogito.auditing.model.AuditEvents;

/**
 * Services for storing and retrieving application audit events
 * @author jeremydeane
 */
public final class AuditingServices {
	
	//in memory storage of application audits
	private static ConcurrentHashMap<String, AuditEvent> audits = 
			new ConcurrentHashMap<String, AuditEvent>();
	
	/**
	 * Store an application audit event
	 * @param auditEvent
	 * @return AuditEvent
	 */
	public static AuditEvent storeAuditEvent (AuditEvent auditEvent) {
		
		return audits.putIfAbsent(auditEvent.getAuditEventKey(), auditEvent);
	}
	
	/**
	 * Store an application audit event
	 * @param auditEvent
	 * @param auditEventKey
	 * @return AuditEvent
	 */
	public static AuditEvent storeAuditEvent (AuditEvent auditEvent, String auditEventKey) {
		
		return audits.putIfAbsent(auditEventKey, auditEvent);
	}	

	/**
	 * Retrieve a specific audit event
	 * @param auditEventKey
	 * @return AuditEvent
	 */
	public static AuditEvent retrieveAuditEvent(String auditEventKey) {
		 
		return audits.get(auditEventKey);
	}
	
	/**
	 * Delete an audit event
	 * @param auditEventKey
	 */
	public static void deleteAuditEvent(String auditEventKey) {
		
		audits.remove(auditEventKey);
	}
	
	/**
	 * Retrieve all audit events for a given application
	 * @param application
	 * @param host
	 * @param port
	 * @param context
	 * @return AuditEvents
	 */
	public static AuditEvents retrieveAuditEvents (String application, String host, 
			int port, String context) {
		
		List<AuditEventURL> auditEvents = new ArrayList<AuditEventURL>();
		
		for (Map.Entry<String,AuditEvent> entry : audits.entrySet()) {
		    
		    if (entry.getKey().contains(application)) {
		    	
		    	auditEvents.add(new AuditEventURL(host, port, context, 
		    			entry.getKey()));
		    }	    
		}
		
		return new AuditEvents (application, auditEvents);
	}
}