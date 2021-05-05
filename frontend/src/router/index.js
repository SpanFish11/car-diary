import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "homepage",
  },
  {
    path: "/manager",
    name: "manager-cars",
    component: () => import("../components/manager/ListOfAllCars"),
  },
  {
    path: "/client",
    name: "client-cars",
    component: () => import("../components/client/Main"),
  },
  {
    path: "/manager/cardetails/:carId",
    name: "manager-cars-details",
    component: () => import("../components/manager/cardetails/CarDetails"),
  },
  {
    path: "/manager/client",
    name: "manager-add-client",
    component: () => import("../components/manager/client/AddNewClient"),
  },
  {
    path: "/manager/soldcar",
    name: "manager-add-sold-car",
    component: () => import("../components/client/AddNewCarModal"),
  },
  {
    path: "/manager/addServiceRecord",
    name: "manager-add-service-record",
    component: () =>
      import("../components/manager/serviceRecords/AddServiceRecord"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
