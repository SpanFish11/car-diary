import Vue from "vue";
import Vuex from "vuex";
import { auth } from "./auth.module";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    drawer: null,
    snackbarSuccess: null,
    snackbarError: null,
    snackbarSuccessMessage: null,
  },
  mutations: {
    SET_DRAWER(state, payload) {
      state.drawer = payload;
    },
    SET_SNACKBARSUCCESS(state, payload) {
      state.snackbarSuccess = payload.show;
      state.snackbarSuccessMessage = payload.message
    },
    SET_SNACKBARERROR(state, payload) {
      state.snackbarError = payload;
    },
  },
  actions: {},
  modules: { auth },
});
