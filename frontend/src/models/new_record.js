export default class NewRecord {
  constructor(
    serviceOperationNumber,
    date,
    mileage,
    serviceWorks,
    changableParts
  ) {
    this.serviceOperationNumber = serviceOperationNumber;
    this.date = date;
    this.mileage = mileage;
    this.serviceWorks = serviceWorks;
    this.changableParts = changableParts;
  }
}
