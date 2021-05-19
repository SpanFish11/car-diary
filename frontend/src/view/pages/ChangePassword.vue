<template>
  <v-container fill-height fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" md="4" sm="8">
        <v-card class="elevation-12">
          <v-card-text>
            <validation-observer ref="observer" v-slot="{ invalid }">
              <v-form @submit.prevent="savePassword">
                <validation-provider
                  v-slot="{ errors }"
                  :rules="{ required: true, min: 8 }"
                  name="Old Password"
                >
                  <v-text-field
                    ref="old"
                    v-model="request.oldPassword"
                    :error-messages="errors"
                    label="Old Password"
                    name="old"
                    counter="8"
                    outlined
                    required
                    type="password"
                  ></v-text-field>
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  :vid="request.newPassword"
                  :rules="{
                    required: true,
                    min: 8,
                    max: 84,
                  }"
                  name="New Password"
                >
                  <v-text-field
                    ref="new_password"
                    v-model="request.newPassword"
                    :error-messages="errors"
                    label="New Password"
                    name="new_password"
                    outlined
                    required
                    type="password"
                  >
                    <template v-slot:append>
                      <v-tooltip bottom>
                        <template v-slot:activator="{ on }">
                          <v-icon v-on="on">mdi-help-circle-outline</v-icon>
                        </template>
                        <ul>
                          <li>Length between 8 and 84 characters</li>
                          <li>At least one upper-case character</li>
                          <li>At least one lower-case character</li>
                          <li>At least one digit character</li>
                          <li>At least one symbol (special character)</li>
                          <li>Illegal sequences >= 5 chars long, like:</li>
                          <ul>
                            <li>Alphabetical: abcde</li>
                            <li>Numerical: 34567</li>
                          </ul>
                          <li>No whitespace</li>
                        </ul>
                      </v-tooltip>
                    </template>
                  </v-text-field>
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  :rules="{
                    required: true,
                    min: 8,
                    confirmed: request.newPassword,
                  }"
                  name="Password Confirm"
                >
                  <v-text-field
                    ref="confirm"
                    v-model="request.newPasswordConfirm"
                    :error-messages="errors"
                    label="Confirm password"
                    name="confirm"
                    outlined
                    required
                    type="password"
                  ></v-text-field>
                </validation-provider>

                <v-btn
                  :disabled="invalid"
                  class="v-btn--block"
                  color="success"
                  style="min-height: 50px"
                  type="submit"
                >
                  Change
                </v-btn>
              </v-form>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import { mapMutations, mapState } from "vuex";
import ChangePassword from "@/models/change_password";
import jwt_decode from "jwt-decode";

export default {
  name: "ChangePassword",
  metaInfo: {
    title: "Change password",
  },
  data: () => ({
    request: new ChangePassword(),
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
    async savePassword() {
      this.$refs.observer.validate();
      try {
        await CarDiaryDataService.saveNewPassword(this.request);
        this.setSnackbarSuccess({
          show: true,
          message: "Password changed successfully!",
        });
        const { roles } = jwt_decode(this.$store.state.auth.user.token);
        if (roles.includes("user") && roles.length === 1) {
          await this.$router.push({ name: "Client Cars", force: true });
        } else {
          await this.$router.push({ name: "Cars", force: true });
        }
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError({ show: true, message: error.response.data });
      }
    },
  },
};
</script>
