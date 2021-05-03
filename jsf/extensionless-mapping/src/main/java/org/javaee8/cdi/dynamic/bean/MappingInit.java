package org.javaee8.cdi.dynamic.bean;

import static jakarta.faces.application.ViewVisitOption.RETURN_AS_MINIMAL_IMPLICIT_OUTCOME;

import java.util.Map;

import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;
import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

/**
 * 
 * @author Arjan Tijms
 */
public class MappingInit implements SystemEventListener {
    
 
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		
		FacesContext facesContext = event.getFacesContext();
        ServletContext sc = (ServletContext) facesContext.getExternalContext().getContext();
        
        if (Boolean.valueOf((String) sc.getAttribute("mappingsAdded"))) {
        	return;
        }

        Map<String, ? extends ServletRegistration> servletRegistrations = (Map<String, ? extends ServletRegistration>) sc.getAttribute("mappings");
        
        if (servletRegistrations == null) {
        	return;
        }

        MappingServletContextListener.addServletMappings(servletRegistrations, facesContext);
    }
		

	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof Application;
	}

}
