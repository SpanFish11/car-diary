import { AXIOS } from "@/http-common";
import authHeader from "@/services/auth-header";

class CarDiaryDataService {
  createServiceRecord(carId, serviceRecord) {
    return AXIOS.post(`cars/${carId}/operation`, serviceRecord, {
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
}

export default new CarDiaryDataService();
