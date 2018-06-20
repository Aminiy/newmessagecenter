
package com.allstar.nmsc;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.util.Methods;

import com.allstar.nmsc.handler.MessageGetHandler;
import com.allstar.nmsc.handler.PetsGetHandler;
import com.allstar.nmsc.handler.PetsPetIdDeleteHandler;
import com.allstar.nmsc.handler.PetsPetIdGetHandler;
import com.allstar.nmsc.handler.PetsPostHandler;
import com.networknt.health.HealthGetHandler;
import com.networknt.info.ServerInfoGetHandler;
import com.networknt.server.HandlerProvider;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
        
            .add(Methods.GET, "/v1/health", new HealthGetHandler())
            .add(Methods.GET, "/v1/server/info", new ServerInfoGetHandler())
            .add(Methods.POST, "/v1/pets", new PetsPostHandler())
            .add(Methods.GET, "/v1/pets", new PetsGetHandler())
            .add(Methods.GET, "/v1/pets/{petId}", new PetsPetIdGetHandler())
            .add(Methods.DELETE, "/v1/pets/{petId}", new PetsPetIdDeleteHandler())
        
            // 
            .add(Methods.GET, "/v1/getmessage", new MessageGetHandler())
        ;
    }
}
