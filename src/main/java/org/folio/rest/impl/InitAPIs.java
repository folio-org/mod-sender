package org.folio.rest.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.folio.rest.resource.interfaces.InitAPI;
import org.folio.sender.DeliveryVerticle;

import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;


public class InitAPIs implements InitAPI {

  private static final Logger LOG = LogManager.getLogger(InitAPIs.class);

  public void init(Vertx vertx, Context context, Handler<AsyncResult<Boolean>> resultHandler) {
    context.put("webClient", WebClient.create(vertx));
    vertx.deployVerticle(new DeliveryVerticle())
      .map(true)
      .onFailure(err -> LOG.error("Failed to deploy DeliveryVerticle", err))
      .onComplete(resultHandler);
  }
}
