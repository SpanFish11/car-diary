import { AXIOS } from "@/http-common";

class CarDiaryDataService {
  createServiceRecord(carId, serviceRecord) {
    return AXIOS.post(`/operations/${carId}`, serviceRecord, {
      headers: authHeader(),
    });
  }

  getAllMaintenances() {
    return AXIOS.get(`maintenances`, {
      headers: authHeader(),
    });
  }

  getAllCars(params) {
    return AXIOS.get(`cars`, { params: params.params, headers: authHeader() });
  }

  getAllBrands() {
    return AXIOS.get(`brands`, { headers: authHeader() });
  }

  getBrandModels(brandId) {
    return AXIOS.get(`brands/${brandId}/models`, { headers: authHeader() });
  }

  getAllClientCars() {
    return AXIOS.get(`cars/my`, { headers: authHeader() });
  }

  getLastAddedClientCar(carId) {
    return AXIOS.get(`/cars/${carId}`, { headers: authHeader() });
  }

  saveNewCar(car) {
    return AXIOS.post(`clients/1/cars`, car, { headers: authHeader() });
  }

  saveCarImage(carId, photo) {
    let header = authHeader();
    Object.assign(header, { "Content-Type": "multipart/form-data" });
    return AXIOS.patch(`cars/${carId}/photos`, photo, { headers: header });
  }

  getAllClients() {
    return AXIOS.get(`/clients`, { headers: authHeader() });
  }

  getAllClientsCars(clientId) {
    return AXIOS.get(`/clients/${clientId}/cars`, { headers: authHeader() });
  }

  getAllEquipments() {
    return AXIOS.get(`equipments`, { headers: authHeader() });
    return AXIOS.get(`maintenances`, {
      headers: authHeader(),
    });
  }

  createGuarantee(carId, request) {
    return AXIOS.post(`guarantee/${carId}`, request, { headers: authHeader() });
  }

  getCarById(carId) {
    return AXIOS.get(`/cars/${carId}`, { headers: authHeader() });
  }

  getGuaranteeByCarId(carId) {
    return AXIOS.get(`/guarantee/${carId}`, { headers: authHeader() });
  }

  getServiceRecordsByCarId(carId) {
    return AXIOS.get(`/operations/${carId}`, { headers: authHeader() });
  }

  printHistoryOfServiceRecord(carId) {
    return AXIOS.get(`/operations/${carId}/print`, {
      headers: authHeader(),
      responseType: "arraybuffer",
    });
  }
}

export default new CarDiaryDataService();
