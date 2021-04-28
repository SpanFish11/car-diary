import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "homepage",
    },
    {
      path: "/manager",
      name: "manager-cars",
      component: () => import("./components/manager/ListOfAllCars"),
    },
    {
      path: "/client",
      name: "client-cars",
      component: () => import("./components/client/Main"),
    },
  ],
});
