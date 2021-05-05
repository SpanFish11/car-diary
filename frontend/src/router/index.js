import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "homepage",
  },
  {
    path: "/service",
    name: "manager-add-service-record",
    component: () => import("../view/AddServiceRecord"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
