
package com.allstar.nmsc.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class PetsPostHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        
            exchange.endExchange();
        
    }
}
