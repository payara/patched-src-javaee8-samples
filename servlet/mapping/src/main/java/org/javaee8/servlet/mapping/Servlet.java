package org.javaee8.servlet.mapping;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Arjan Tijms
 */
@WebServlet({"/path/*", "*.ext", "", "/", "/exact"})
public class Servlet extends HttpServlet {
     
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
        HttpServletMapping mapping = request.getHttpServletMapping();
        
        System.out.println(mapping);
        System.out.println(mapping.getMappingMatch());
         
        response.getWriter()
                .append("Mapping match:")
                .append(mapping.getMappingMatch().name())
                .append("\n")
                .append("Match value:'")
                .append(mapping.getMatchValue())
                .append("'")
                .append("\n")
                .append("Pattern:'")
                .append(mapping.getPattern())
                .append("'");
    }
 
}
