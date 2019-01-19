/**
 * Track the trade of a Car from one trader to another
 * @param {org.example.biznet.Trade} trade - the trade to be processed
 * @transaction
 */
async function trade(trade) {
  if(trade.car.km == trade.km && trade.car.status) { 
    trade.car.owner = trade.newOwner;
    trade.car.status = false;
    let assetRegistry = await getAssetRegistry('org.example.biznet.Car');
    await assetRegistry.update(trade.car);
    }
}

/**
 * Change Status of a car
 * @param {org.example.biznet.ChangeStatus} trade - the trade to be processed
 * @transaction
 */
async function ChangeStatus(trade) {
 	trade.car.status = true;
    let assetRegistry = await getAssetRegistry('org.example.biznet.Car');
    await assetRegistry.update(trade.car);
}

/**
 * Change Km of a car
 * @param {org.example.biznet.ChangeKm} trade - the trade to be processed
 * @transaction
 */
async function ChangeKm(trade) {
 	trade.car.km = trade.km;
    let assetRegistry = await getAssetRegistry('org.example.biznet.Car');
    await assetRegistry.update(trade.car);
}

/**
 * Change Schaden of a car
 * @param {org.example.biznet.ChangeSchaden} trade - the trade to be processed
 * @transaction
 */
async function ChangeSchaden(trade) {
 	trade.car.schaden = trade.schaden;
  	trade.car.schadenHash = trade.schadenHash;
    let assetRegistry = await getAssetRegistry('org.example.biznet.Car');
    await assetRegistry.update(trade.car);
}

/**
 * Change Kennzeichen of a car
 * @param {org.example.biznet.ChangeKennzeichen} trade - the trade to be processed
 * @transaction
 */
async function ChangeKennzeichen(trade) {
 	trade.car.kennzeichen = trade.kennzeichen;
    let assetRegistry = await getAssetRegistry('org.example.biznet.Car');
    await assetRegistry.update(trade.car);
}

/**
 * Track the trade of a Car from one trader to another
 * @param {org.example.biznet.CarForController} trade - the trade to be processed
 * @returns {org.example.biznet.ControllerCar} The Car with less information.
 * @transaction
 */
async function CarForController(trade) {
  	var factory = getFactory();
  	var cCar = factory.newResource('org.example.biznet', 'ControllerCar', '1');
    cCar.fahrNR = trade.car.fahrNR;
  	cCar.kennzeichen = trade.car.kennzeichen;
  	cCar.schaden = trade.car.schaden;
  	cCar.km = trade.car.km;
    return cCar;
    
}

/**
 * Track the trade of a Car from one trader to another
 * @param {org.example.biznet.CarForUser} trade - the trade to be processed
 * @returns {org.example.biznet.UserCar} The Car with less information.
 * @transaction
 */
async function CarForUser(trade) {
  	var factory = getFactory();
  	var cCar = factory.newResource('org.example.biznet', 'UserCar', '1');
    cCar.fahrNR = trade.car.fahrNR;
  	cCar.kennzeichen = trade.car.kennzeichen;
  	cCar.pos = trade.car.pos;
  	cCar.km = trade.car.km;
  	cCar.status = trade.car.status;
    return cCar;
    
}

/**
 * Track the trade of a Car from one trader to another
 * @param {org.example.biznet.getKennzeichen} trade - the trade to be processed
 * @returns {String} The Car with less information.
 * @transaction
 */
async function getKennzeichen(trade) {
    return trade.car.kennzeichen;
}

/**
 * Track the trade of a Car from one trader to another
 * @param {org.example.biznet.getFahrNr} trade - the trade to be processed
 * @returns {String} The Car with less information.
 * @transaction
 */
async function getFahrNR(trade) {
    return trade.car.fahrNR;
}

/**
 * Login
 * @param {org.example.biznet.Login} trade - the trade to be processed
 * @returns {org.example.biznet.Trader} The Trader.
 * @transaction
 */
async function Login(trade) {
  	return null;
    
}
