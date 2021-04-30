import Vue from "vue";
import App from "./App.vue";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import {
  extend,
  localize,
  ValidationObserver,
  ValidationProvider,
} from "vee-validate";
import en from "vee-validate/dist/locale/en.json";
import Meta from "vue-meta";
import router from "./router";

// Import Bootstrap an BootstrapVue CSS files
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// Import vee-validate rules
import * as rules from "vee-validate/dist/rules";

// install rules
Object.keys(rules).forEach((rule) => {
  extend(rule, rules[rule]);
});

localize("en", en);

// Install components globally
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(Meta);
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);

Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
