package org.folio.sender.delivery;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.folio.HttpStatus;
import org.folio.rest.jaxrs.model.TextNotifyEntity;
import org.folio.rest.jaxrs.model.User;

public class TextNotifyDeliveryChannel implements DeliveryChannel {

  private static final Logger LOG = LogManager.getLogger(TextNotifyDeliveryChannel.class);

  private final WebClient webClient;
  private final String textNotifyUrlPath;

  public TextNotifyDeliveryChannel(Vertx vertx, String textNotifyUrlPath) {
    this.webClient = WebClient.create(vertx);
    this.textNotifyUrlPath = textNotifyUrlPath;
  }

  @Override
  public void deliverMessage(String notificationId, JsonObject recipientJson,
    JsonObject message, JsonObject okapiHeadersJson) {
    var deliveryChannel = message.getString("deliveryChannel");
    LOG.debug("deliverMessage:: Sending message for notificationId {} using channel {}",
      notificationId, deliveryChannel);

    try {
      var recipient = recipientJson.mapTo(User.class);
      var textNotifyEntity = message.mapTo(TextNotifyEntity.class);
      textNotifyEntity.setNotificationId(notificationId);
      textNotifyEntity.setTo(recipient.getPersonal().getMobilePhone());

      var request = EmailDeliveryChannel.createRequestAndPrepareHeaders(okapiHeadersJson, textNotifyUrlPath, webClient);

      request.sendJson(textNotifyEntity)
        .onFailure(error -> LOG.error("deliverMessage:: Error from text-notify module for notificationId {}",
          notificationId, error))
        .onSuccess(response -> {
          if (response.statusCode() != HttpStatus.SC_OK) {
            LOG.error("deliverMessage:: Text-notify module responded with status '{}' for notificationId '{}'",
              response.statusCode(), notificationId);
          }
        });
    } catch (Exception e) {
      LOG.error("deliverMessage:: Error while attempting to deliver notificationId {} using channel {}",
        notificationId, deliveryChannel, e);
    }
  }
}
