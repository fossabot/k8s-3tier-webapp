package webapp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webapp.tier.cache.MemcachedService;

@Path("/memcached")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemcachedResource {

	@POST
	@Path("/set")
	public Response set() {
		MemcachedService svc = new MemcachedService();
		try {
			return Response.ok().entity(svc.set()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/get")
	public Response get() {
		MemcachedService svc = new MemcachedService();
		try {
			return Response.ok().entity(svc.get()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}
