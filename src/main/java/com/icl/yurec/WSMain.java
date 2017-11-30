package com.icl.yurec;

import com.icl.yurec.datamodel.RequestContent;
import com.icl.yurec.util.ResponseBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by anton.petrov on 21.11.2017.
 */
@Path("/")
public class WSMain {
    private static final Logger logger = Logger.getLogger(WSMain.class.getName());

//    @POST
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response jsonFromString(String data) {
//        logger.log(Level.INFO, "GUMCID={0}", data);
//        JSONObject outputJsonObj = null;
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            RequestContent requestContent = mapper.readValue(data, RequestContent.class);
//            logger.log(Level.INFO, "GUMCID={0}", requestContent.getGumcid());
//            outputJsonObj = ResponseBuilder.createAnswer(requestContent.getList(), requestContent.getGumcid());
//            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(outputJsonObj).build();
//        } catch (IOException | SQLException | JSONException e) {
//            logger.log(Level.SEVERE, e.getMessage(), e);
//            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ResponseBuilder.createError(e.getMessage())).build();
//        }
//    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonFromString(String data) {
        logger.log(Level.INFO, "data={0}", data);
        JSONObject outputJsonObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestContent requestContent = mapper.readValue(data, RequestContent.class);
            logger.log(Level.INFO, "GUMCID={0}", requestContent.getGumcid());
            outputJsonObj = ResponseBuilder.createAnswer(requestContent.getList(), requestContent.getGumcid());
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(outputJsonObj).build();
        } catch (IOException | SQLException | JSONException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ResponseBuilder.createError(e.getMessage())).build();
        }
    }


    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title></title><body><h1></h1></body</html>";
    }


}
