package controllers;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("appointment")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AppointmentController {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/welcome")
	public String hello()
	{
		// http://localhost:8080/BarberShopScheduleWeb/barberShopScheduleAPI/appointment/welcome
		return "Hello World";
	}
	
}