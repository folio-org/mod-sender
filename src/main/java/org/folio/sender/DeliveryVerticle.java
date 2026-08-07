package org.folio.sender;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.serviceproxy.ServiceBinder;
import org.folio.sender.delivery.DeliveryChannel;
import org.folio.sender.delivery.EmailDeliveryChannel;
import org.folio.sender.delivery.MailDeliveryChannel;
import org.folio.sender.delivery.TextNotifyDeliveryChannel;

public class DeliveryVerticle extends AbstractVerticle {

  public static final String DELIVERY_CHANNELS_LOCAL_MAP = "delivery-channel.map";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    registerDeliveryChannel("email", "delivery-channel.email.queue", vertx,
      new EmailDeliveryChannel(vertx, "/email"));
    registerDeliveryChannel("mail", "delivery-channel.mail.queue", vertx,
      new MailDeliveryChannel(vertx, "/mail"));
    registerDeliveryChannel("sms", "delivery-channel.sms.queue", vertx,
      new TextNotifyDeliveryChannel(vertx, "/text-notify"));

    startPromise.handle(Future.succeededFuture());
  }

  private <T extends DeliveryChannel> void registerDeliveryChannel(String name, String address, Vertx vertx,
                                                                   T deliveryChannelInstance) {
    LocalMap<String, String> deliveryChannelAddressesMap = vertx.sharedData()
      .getLocalMap(DELIVERY_CHANNELS_LOCAL_MAP);

    new ServiceBinder(vertx).setAddress(address).register(DeliveryChannel.class, deliveryChannelInstance);
    deliveryChannelAddressesMap.put(name, address);
  }
}
