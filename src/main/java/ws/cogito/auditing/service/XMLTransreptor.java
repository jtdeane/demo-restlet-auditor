package ws.cogito.auditing.service;

import ws.cogito.auditing.model.AuditEvent;
import ws.cogito.auditing.model.AuditEventURL;
import ws.cogito.auditing.model.AuditEvents;

import com.thoughtworks.xstream.XStream;

/**
 * Services for conversion between XML and POJOs using XStream
 * @author jeremydeane
 *
 */
public final class XMLTransreptor {
	
	private static XStream xstream;
	
	static {
		
		xstream = new XStream();
		
    	xstream.alias("event", AuditEventURL.class);
    	xstream.alias("audit-event", AuditEvent.class);
    	xstream.alias("audit-events", AuditEvents.class);
	}
	
	/**
	 * Convert XML to POJO
	 * @param xml
	 * @return Object
	 * @throws Exception
	 */
	public static Object toPOJO (String xml) throws Exception {
		return xstream.fromXML(xml);
	}
	
	/**
	 * Convert POJO to XML
	 * @param object
	 * @return String
	 * @throws Exception
	 */
	public static String toXML (Object object) throws Exception {
		return xstream.toXML(object);
	}
}