import { AXIOS } from "@/http-common";

class CarDiaryDataService {
  createServiceRecord(carId, serviceRecord) {
    return AXIOS.post(`cars/${carId}/operation`, serviceRecord);
  }

  getAllMaintenances() {
    return AXIOS.get(`maintenances`);
  }
}

export default new CarDiaryDataService();
