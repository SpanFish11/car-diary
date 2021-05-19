import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "THIS IS CAR DIARY",
    component: () => import("@/view/Index"),
    children: [
      {
        name: "Home",
        path: "/",
        component: () => import("@/view/pages/Home"),
      },
      {
        path: "/login",
        component: () => import("@/view/pages/LoginPage"),
      },
      {
        name: "Client Cars",
        path: "/client",
        component: () => import("@/view/pages/client/ClientCars"),
      },
      {
        name: "Cars",
        path: "/manager",
        component: () => import("@/view/pages/manager/CarsTable"),
      },
      {
        name: "Add New Service Record",
        path: "/manager/service/add",
        component: () => import("@/view/pages/manager/CreateServiceRecord"),
      },
      {
        name: "Sold Car",
        path: "/manager/car/add",
        component: () => import("@/view/pages/manager/SoldCar"),
      },
      {
        name: "Add New Client",
        path: "/manager/client/add",
        component: () => import("@/view/pages/manager/CreateNewClient"),
      },
      {
        name: "Car details",
        path: "/cardetails/:carId",
        component: () => import("@/view/pages/cardetails/CarDetails"),
      },
      {
        name: "Add Car guarantee",
        path: "/manager/guarantee",
        component: () => import("@/view/pages/manager/CreateGuarantee"),
      },
      {
        name: "Change Password",
        path: "/password",
        component: () => import("@/view/pages/ChangePassword"),
      },
      {
        path: "*",
        component: () => import("@/view/pages/NotFound"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;

router.beforeEach((to, from, next) => {
  const publicPages = ["/login"];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem("user");

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next("/login");
  } else {
    next();
  }
});
