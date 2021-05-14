export default class NewCar {
  constructor(
    vin,
    brandId,
    modelId,
    equipmentId,
    year,
    price,
    mileage,
    clientId,
    used,
    ours
  ) {
    this.vin = vin;
    this.brandId = brandId;
    this.modelId = modelId;
    this.equipmentId = equipmentId;
    this.year = year;
    this.price = price;
    this.mileage = mileage;
    this.clientId = clientId;
    this.used = used;
    this.ours = ours;
  }
}
