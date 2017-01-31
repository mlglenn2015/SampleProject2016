package prv.mark.project.stockticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import prv.mark.project.stockticker.config.StockTickerApplicationConfig;
import prv.mark.project.stockticker.config.StockTickerWsConfig;

import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/** TODO
 * Created by Owner on 1/27/2017.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppInitializer.class);

    public void onStartup(ServletContext servletContext) throws ServletException {
        LOGGER.info("WebAppInitializer: Returning new ServletContext...");
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(StockTickerApplicationConfig.class);
        ctx.setServletContext(servletContext);
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);  //Necessary to expose WSDL locations to reflect the URL of the incoming request
        Dynamic dynamic = servletContext.addServlet("dispatcher",servlet);
        dynamic.addMapping("/*");
        //dynamic.addMapping("/soapws/*");
        dynamic.setLoadOnStartup(1);
    }
}
