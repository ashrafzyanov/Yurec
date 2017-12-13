package com.icl.yurec;

import com.icl.yurec.datamodel.Answer;
import com.icl.yurec.datamodel.RequestContent;
import com.icl.yurec.dto.ServerAnswer;
import com.icl.yurec.service.Service;
import com.icl.yurec.service.impl.ServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/bpm")
public class WSMain {
    private static final Logger LOG = Logger.getLogger(WSMain.class.getName());

    private Service service;

    @POST
    @Path("/getComponentInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonFromString(RequestContent requestContent) {
        try {
            List<Answer> result = service.getComponentInfo(requestContent.getList(), requestContent.getGumcid());
            return Response.ok().entity(new ServerAnswer(true, result)).build();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return Response.serverError().entity(new ServerAnswer(false, e.getMessage())).build();
        }
    }

    public void setService(Service service) {
        this.service = service;
    }

}
