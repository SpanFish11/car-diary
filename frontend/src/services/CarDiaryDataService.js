import { AXIOS } from "@/http-common";
import authHeader from "@/services/auth-header";

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
    return AXIOS.get(`clients/cars/${carId}`, { headers: authHeader() });
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
