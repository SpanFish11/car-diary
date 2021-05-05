import { AXIOS } from "@/http-common";

class CarDiaryDataService {
  createServiceRecord(carId, serviceRecord) {
    return AXIOS.post(`cars/${carId}/operation`, serviceRecord);
  }
}

export default new CarDiaryDataService();
