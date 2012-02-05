package org.krams.tutorial.server;

import org.krams.tutorial.client.GreetingService;
import org.krams.tutorial.shared.FieldVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Service("greetingService")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  @Autowired
  private SpringService springService;
	  
  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid.
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);

    // Delegate to SpringService and return the result
    return springService.echo(input);
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
