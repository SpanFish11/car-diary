<template>
  <div class="fill-height">
    <v-progress-linear
      :active="inLoading"
      :indeterminate="inLoading"
      absolute
    ></v-progress-linear>

    <v-container fill-height fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-card-text>
              <validation-observer ref="observer" v-slot="{ invalid }">
                <v-form @submit.prevent="submit">
                  <validation-provider
                    v-slot="{ errors }"
                    name="Email"
                    :rules="{ required: true, email: true }"
                  >
                    <v-text-field
                      ref="email"
                      label="Email"
                      name="Email"
                      v-model="user.email"
                      type="text"
                      :error-messages="errors"
                      outlined
                      required
                    ></v-text-field>
                  </validation-provider>
                  <validation-provider
                    v-slot="{ errors }"
                    name="Email"
                    :rules="{ required: true }"
                  >
                    <v-text-field
                      ref="password"
                      v-model="user.password"
                      label="Password"
                      name="Password"
                      :error-messages="errors"
                      type="password"
                      outlined
                      required
                    ></v-text-field>
                  </validation-provider>

                  <v-btn
                    type="submit"
                    :disabled="invalid"
                    color="success"
                    style="min-height: 50px"
                    class="v-btn--block"
                  >
                    Sign in
                  </v-btn>
                </v-form>
              </validation-observer>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import AuthRequest from "@/models/auth_request";
import jwt_decode from "jwt-decode";

export default {
  name: "LoginPage",
  metaInfo: {
    title: "Sign in",
  },
  data: () => ({
    user: new AuthRequest(),

    remember: false,
    inProgress: false,
    inLoading: false,
  }),
  computed: {
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    async submit() {
      this.$refs.observer.validate();
      this.inProgress = true;
      this.inLoading = true;

      try {
        await this.$store.dispatch("auth/login", this.user).then(() => {
          this.setSnackbarSuccess({ show: true, message: "Welcome back!" });
          const { roles } = jwt_decode(this.$store.state.auth.user.token);
          if (roles.includes("user") && roles.length === 1) {
            this.$router.push({ name: "Client Cars", force: true });
          } else {
            this.$router.push({ name: "Cars", force: true });
          }
        });
      } catch (error) {
        this.inProgress = false;
        this.inLoading = false;
        this.setSnackbarError({ show: true, message: "Error authentication" });
      }
    },
  },
};
</script>
