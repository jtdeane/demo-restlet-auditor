package ws.cogito.auditing.application;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ws.cogito.auditing.resource.AuditEventResource;
import ws.cogito.auditing.resource.AuditEventsResource;

public final class AuditApplication extends Application {
	
	/**
     * Public Constructor to create an instance of AuditApplication
     * @param parentContext - the org.restlet.Context instance
     */
    public AuditApplication(Context parentContext) {
    	
        super(parentContext);
    }
	
    /**
     * Creates the router
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        
    	//create the router based on the context
        Router router = new Router(getContext());
        
        /*
         * Map relative the URI to a Resource. The relative URI is the remaining
         * part of the URI set in the web.xml. By default the web.xml is
         * configured to direct any call with /resource/ to the router. So the
         * URI below, in conical form, would be:
         *  
         * http://HOST/WEB APP ROOT/audit/event/{auditEventKey}
         * 
         * {auditEventKey} are URI Templates set to alphanumeric. They
         * can be extracted within the Resource. For example:
         * 
         * request.getAttributes().get("auditEventKey")
         */
        
        //GET, PUT, DELETE Audit Event
        router.attach("/audit/event/{auditEventKey}", AuditEventResource.class);
        
        //POST Audit Event Information
        router.attach("/audit/info", AuditEventResource.class);
        
        //GET Audit Events
        router.attach("/{application}/events", AuditEventsResource.class);

        return router;
    }
}