import Vue from "vue";
import VueRouter from "vue-router";
import AddServiceRecord from "@/view/manager/serviceRecords/AddServiceRecord";
import Login from "@/view/Login";

Vue.use(VueRouter);

const routes = [
  { path: "/", name: "homepage" },
  { path: "/service", component: AddServiceRecord },
  { path: "/login", component: Login },
  {
    path: "/manager",
    name: "manager-cars",
    component: () => import("../view/manager/ListOfAllCars"),
  },
  {
    path: "/client",
    name: "client-cars",
    component: () => import("../view/client/Main"),
  },
  {
    path: "/manager/cardetails/:carId",
    name: "manager-cars-details",
    component: () => import("../view/manager/cardetails/CarDetails"),
  },
  {
    path: "/manager/client",
    name: "manager-add-client",
    component: () => import("../view/manager/client/AddNewClient"),
  },
  {
    path: "/manager/soldcar",
    name: "manager-add-sold-car",
    component: () => import("../view/client/AddNewCarModal"),
  },
  {
    path: "/manager/addServiceRecord",
    name: "manager-add-service-record",
    component: () => import("../view/manager/serviceRecords/AddServiceRecord"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
