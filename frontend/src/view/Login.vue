<template>
  <div class="fill-height">
    <v-progress-linear
      :active="inLoading"
      :indeterminate="inLoading"
      striped
      absolute
      color="purple accent-4"
    ></v-progress-linear>

    <v-snackbar color="error" top v-model="snackbarError" :timeout="15000">
      <span>{{ message }}</span>
      <v-btn text @click="snackbarError = false">{{ "close" }}</v-btn>
    </v-snackbar>

    <v-snackbar color="success" top v-model="snackbarSuccess" :timeout="15000">
      <span>{{ message }}</span>
      <v-btn text @click="snackbarSuccess = false">{{ "close" }}</v-btn>
    </v-snackbar>

    <v-container fill-height fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-card-text>
              <v-form>
                <v-text-field
                  ref="email"
                  name="email"
                  type="text"
                  outlined
                  required
                  v-model="user.email"
                ></v-text-field>
                <v-text-field
                  ref="password"
                  name="password"
                  type="password"
                  outlined
                  required
                  v-model="user.password"
                ></v-text-field>

                <v-layout row wrap class="ml-0 mr-0">
                  <v-checkbox
                    v-model="remember"
                    class="mt-0"
                    :label="'remember'"
                  ></v-checkbox>
                  <v-spacer></v-spacer>
                  <router-link
                    to="/forgot-password"
                    custom
                    v-slot="{ navigate }"
                  >
                    <v-btn
                      @click="navigate"
                      elevation="2"
                      small
                      @keypress.enter="navigate"
                      role="link"
                      >Forgot password?
                    </v-btn>
                  </router-link>
                </v-layout>

                <v-btn
                  color="success"
                  style="min-height: 50px"
                  class="v-btn--block"
                  @click="login()"
                  :disabled="(inProgress, inLoading)"
                >
                  {{ "login" }}
                </v-btn>
              </v-form>
            </v-card-text>
          </v-card>

          <v-card-text class="text-center">
            <div class="my-8 subtitle-1">
              <span v-once
                >Don't have an account yet?
                <router-link to="/registration" custom v-slot="{ navigate }">
                  <v-btn
                    @click="navigate"
                    elevation="2"
                    small
                    @keypress.enter="navigate"
                    role="link"
                    >Sign Up</v-btn
                  >
                </router-link>
              </span>
            </div>
          </v-card-text>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import User from "../models/user";

export default {
  name: "Login",
  metaInfo: {
    title: "Login",
    titleTemplate: "%s | Car Diary",
  },
  data: () => ({
    user: new User("", ""),
    message: "",
    remember: false,
    snackbarSuccess: false,
    snackbarError: false,
    inProgress: false,
    inLoading: false,
  }),
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  methods: {
    login() {
      this.inProgress = true;
      this.inLoading = true;

      if (!this.user.email) {
        console.log("прикрутить валидацию и прерывать процесс логина");
        this.inProgress = false;
        this.inLoading = false;
      } else {
        this.$store.dispatch("auth/login", this.user).then(
          () => {
            this.$router.go(0);
            this.$router.push("/service");
          },
          (error) => {
            this.inProgress = false;
            this.inLoading = false;
            this.message =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();
            this.snackbarError = true;
          }
        );
      }
    },
  },
};
</script>

<style scoped></style>
