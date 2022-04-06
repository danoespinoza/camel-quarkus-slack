package cl.danoespinoza.route;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.SlackResponse;
import domain.SendMessage;

import static org.apache.camel.model.rest.RestParamType.body;

@ApplicationScoped
public class SlackRoute extends RouteBuilder {

	@ConfigProperty(name = "slack.channel")
	String SLACK_CHANNEL;
	
	@ConfigProperty(name = "slack.webhook.endpoint")
	String SLACK_WEBHOOK_ENDPOINT;
	
	Gson gson = new GsonBuilder().create();

    @Override
    public void configure() throws Exception {
        restConfiguration()
	        .dataFormatProperty("prettyPrint", "true")
	        .enableCORS(true);
        
        rest("/slack")
	        .get("/health").description("Service to get health check")
	        	.produces(MediaType.APPLICATION_JSON)
	        	.bindingMode(RestBindingMode.json)
		        .to("direct:getHealth")

            .post("/send").description("Service to send a message to an Slack Channel")
            	.consumes(MediaType.APPLICATION_JSON)
            	.produces(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.json)
                .param().name("message").type(body).required(true).description("The message to send").dataType("string").endParam()
                .to("direct:sendMessage");
        
        from("direct:getHealth")
    		.process(exchange -> {
    			SlackResponse newBody = new SlackResponse(200, "Service available!");
        		
        		exchange.getIn().setBody(newBody);
        	});
        
        from("direct:sendMessage")
        	.marshal().json(JsonLibrary.Gson)
        	.process(exchange -> {
        		SendMessage newBody = gson.fromJson(exchange.getIn().getBody(String.class), SendMessage.class);
        		
        		exchange.getIn().setBody(newBody.getMessage());
        	})
        	.to("slack:#"+ SLACK_CHANNEL +"?webhookUrl=RAW(" + SLACK_WEBHOOK_ENDPOINT + ")")
        	.process(exchange -> {
        		SlackResponse newBody = new SlackResponse(200, "Mensaje enviado correctamente a #" + SLACK_CHANNEL);
        		
        		exchange.getIn().setBody(newBody);
        	});
    }
    
}
