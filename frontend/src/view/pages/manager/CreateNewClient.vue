<template>
  <v-container fluid>
    <validation-observer ref="observer" v-slot="{ invalid }">
      <form @submit.prevent="saveClient">
        <v-card>
          <v-card-title>
            <span class="headline">New Client</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="6">
                  <validation-provider
                    v-slot="{ errors }"
                    name="first name"
                    :rules="{ required: true, alpha_spaces: true }"
                  >
                    <v-text-field
                      label="Legal first name*"
                      v-model="user.firstName"
                      type="text"
                      clearable
                      :error-messages="errors"
                      required
                    ></v-text-field>
                  </validation-provider>
                </v-col>
                <v-col cols="12" sm="6" md="6">
                  <validation-provider
                    v-slot="{ errors }"
                    name="last name"
                    :rules="{ required: true, alpha_spaces: true }"
                  >
                    <v-text-field
                      label="Legal last name*"
                      required
                      clearable
                      :error-messages="errors"
                      type="text"
                      v-model="user.lastName"
                    ></v-text-field>
                  </validation-provider>
                </v-col>
                <v-col cols="12">
                  <validation-provider
                    v-slot="{ errors }"
                    name="email"
                    :rules="{ required: true, email: true, excluded: emails}"
                  >
                    <v-text-field
                      label="Email*"
                      required
                      clearable
                      :error-messages="errors"
                      v-model="user.email"
                    ></v-text-field>
                  </validation-provider>
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="error" @click="reset"> Reset </v-btn>
            <v-btn
              color="success"
              :disabled="invalid"
              @click="saveClient(user)"
            >
              Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </form>
    </validation-observer>
  </v-container>
</template>

<script>
import NewUser from "@/models/new_user";
import CarDiaryDataService from "@/services/CarDiaryDataService";
import { mapMutations, mapState } from "vuex";

export default {
  name: "CreateNewClient",
  data: () => ({
    user: new NewUser(),
    emails: [],
  }),
  mounted() {
    this.loadAllEmails();
  },
  computed: {
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    async saveClient(client) {
      this.$refs.observer.validate();
      try {
        const res = await CarDiaryDataService.saveNewClient(client);
        console.log(res);
        this.setSnackbarSuccess(!this.snackbarSuccess);
        await this.$router.push("/manager");
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError(!this.snackbarError);
      }
    },
    reset() {
      this.user.lastName = null;
      this.user.firstName = null;
      this.user.email = null;
      this.$refs.observer.reset();
    },
    async loadAllEmails() {
      try {
        const res = await CarDiaryDataService.getAllClients();
        res.data.forEach((client) => {
          this.emails.push(client.email);
        });
      } catch (error) {
        console.log(error.response);
      }
    },
  },
};
</script>
