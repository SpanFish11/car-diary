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
        name: "Cars",
        path: "/cars",
        component: () => import("@/view/pages/manager/CarsTable"),
      },
      {
        name: "Login",
        path: "/login",
        component: () => import("@/view/pages/LoginPage"),
      },
      {
        name: "Add New Service Record",
        path: "/cars/service/add",
        component: () => import("@/view/pages/manager/CreateServiceRecord"),
      },
      {
        name: "Client Cars",
        path: "/client",
        component: () => import("@/view/pages/client/ClientCars"),
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
