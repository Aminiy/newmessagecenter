
package com.allstar.nmsc;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.util.Methods;

import com.allstar.nmsc.handler.ExtMessageAppendHandler;
import com.allstar.nmsc.handler.ExtMessageUpdateHandler;
import com.allstar.nmsc.handler.ExtMessageUpdateOneHandler;
import com.allstar.nmsc.handler.ExtMessgeRemoveHandler;
import com.allstar.nmsc.handler.MessageGetHandler;
import com.allstar.nmsc.handler.MessageInsertHandler;
import com.allstar.nmsc.handler.MessageSessionUpdateHandler;
import com.networknt.server.HandlerProvider;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
//        .add(Methods.GET, "/v1/health", new HealthGetHandler())
//        .add(Methods.GET, "/v1/server/info", new ServerInfoGetHandler())
//        .add(Methods.POST, "/v1/pets", new PetsPostHandler())
//        .add(Methods.GET, "/v1/pets", new PetsGetHandler())
	        .add(Methods.GET, "/v1/getmessage", new MessageGetHandler())
	        .add(Methods.GET, "/v1/addmessage", new MessageInsertHandler())
	        .add(Methods.DELETE, "/v1/deletemessage", new MessageSessionUpdateHandler())
	        .add(Methods.POST, "/v1/extmessageremove", new ExtMessgeRemoveHandler())
	        .add(Methods.POST, "/v1/extmessageappend", new ExtMessageAppendHandler())
	        .add(Methods.POST, "/v1/extmessageupdate", new ExtMessageUpdateHandler())
	        .add(Methods.POST, "/v1/extmessageupdateone", new ExtMessageUpdateOneHandler())
        ;
    }
}
