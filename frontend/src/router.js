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
        {
            path: "/manager/cardetails",
            name: "manager-cars-details",
            component: () => import("./components/manager/cardetails/CarDetails"),
        },
        {
            path: "/manager/client",
            name: "manager-add-client",
            component: () => import("./components/manager/client/AddNewClient"),
        },
        {
            path: "/manager/soldcar",
            name: "manager-add-sold-car",
            component: () => import("./components/manager/soldcar/AddSoldCar"),
        }
    ],
});
