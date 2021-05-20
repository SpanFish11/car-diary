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

  getLastAddedClientCar(carId) {
    return AXIOS.get(`/cars/${carId}`, { headers: authHeader() });
  }

  saveNewCar(clientId, car) {
    return AXIOS.post(`clients/${clientId}/cars`, car, {
      headers: authHeader(),
    });
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
  }

  saveNewClient(client) {
    return AXIOS.post(`/clients`, client, { headers: authHeader() });
  }

  saveCar(car) {
    return AXIOS.post(`/cars`, car, { headers: authHeader() });
  }

  createGuarantee(carId, request) {
    return AXIOS.post(`guarantee/${carId}`, request, { headers: authHeader() });
  }

  getCarById(carId) {
    return AXIOS.get(`/cars/${carId}`, { headers: authHeader() });
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

  saveNewPassword(data) {
    return AXIOS.put(`clients/password/reset`, data, { headers: authHeader() });
  }

  saveAppointment(appointment) {
    return AXIOS.post("/appointments", appointment, { headers: authHeader() });
  }

  getAllClientAppointments() {
    return AXIOS.get("/clients/appointments", { headers: authHeader() });
  }

  getAllAppointments() {
    return AXIOS.get("/appointments", { headers: authHeader() });
  }

  changeAppointmentStatus(appointmentId, request) {
    return AXIOS.put(
      `/appointments/${appointmentId}?status=` + request,
      {},
      { headers: authHeader() }
    );
  }

  changeCarMileage(clientId, request) {
    return AXIOS.patch(`/clients/${clientId}/cars`, request, {
      headers: authHeader(),
    });
  }
}

export default new CarDiaryDataService();
