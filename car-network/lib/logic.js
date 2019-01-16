
/**
 * Buy card transaction
 * @param {org.example.biznet.TradeCar} trade
 * @transaction
 */
async function TradeCar(trade) {
    trade.car.owner = trade.newOwner;
    return getAssetRegistry("org.example.biznet.Car")
      .then(assetRegistry => {
        return assetRegistry.update(trade.car); // Update the network registry
      })
      .then(() => {
        let event = getFactory().newEvent(
          "org.example.biznet",
          "TradeNotification"
        ); // Get a reference to the event specified in the modeling language
        event.car = trade.car;
        emit(event); // Fire off the event
      });
  
}
