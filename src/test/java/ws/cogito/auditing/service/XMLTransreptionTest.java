package ws.cogito.auditing.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ws.cogito.auditing.model.AuditEvent;
import ws.cogito.auditing.model.AuditEvents;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Unit test the XML Transreption
 * @author jeremydeane
 */

public class XMLTransreptionTest {

	private final XmlMapper xmlMapper = new XmlMapper();
	private final String expectedXML = "<audit-event xmlns=\"\"><application>Claims</application><time>201110201650</time><message>Bodily Injury</message></audit-event>";
	private final AuditEvent expectedPOJO = new AuditEvent ("Billing", "201210201650", "Late Payment");

	@Test
	public void toXMLTest() throws Exception {
		
		//Test AuditEvent Transreption
		AuditEvent auditEvent = new AuditEvent
				("Claims", "201110201650", "Bodily Injury");
		
		String actaulXML = xmlMapper.writeValueAsString(auditEvent);
		

		assertEquals(expectedXML, actaulXML);
		
		//Test AuditEvets Transreption
		AuditEvent auditEvent2 = new AuditEvent
				("Claims", "201210201650", "Commercial Vehicle");			
		
		List<URL> auditEventLocations = new ArrayList<URL>();
		
		auditEventLocations.add (auditEvent.getAuditEventLocation("localhost",8080, "restlet-auditor"));
		auditEventLocations.add (auditEvent2.getAuditEventLocation("localhost",8080, "restlet-auditor"));
		
		AuditEvents auditEvents = new AuditEvents ("Claims", auditEventLocations);
		
		String auditEventsXML = xmlMapper.writeValueAsString(auditEvents);
		
		assertTrue(auditEventsXML.contains("<AuditEvents xmlns=\"\" application=\"Claims\"><events><event>"));
	}
	
	@Test
	public void toPOJOTest() throws Exception {
		
		String auditEvent = "<audit-event><application>Billing</application><time>201210201650</time><message>Late Payment</message></audit-event>";
		
		AuditEvent actualPOJO = (AuditEvent)xmlMapper.readValue(auditEvent, AuditEvent.class);
		
		assertEquals(expectedPOJO, actualPOJO);
	}
}