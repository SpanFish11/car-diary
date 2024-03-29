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
                <v-col cols="12" md="6" sm="6">
                  <validation-provider
                    v-slot="{ errors }"
                    :rules="{ required: true, alpha_spaces: true }"
                    name="first name"
                  >
                    <v-text-field
                      v-model="user.firstName"
                      :error-messages="errors"
                      clearable
                      label="Legal first name*"
                      required
                      type="text"
                    ></v-text-field>
                  </validation-provider>
                </v-col>
                <v-col cols="12" md="6" sm="6">
                  <validation-provider
                    v-slot="{ errors }"
                    :rules="{ required: true, alpha_spaces: true }"
                    name="last name"
                  >
                    <v-text-field
                      v-model="user.lastName"
                      :error-messages="errors"
                      clearable
                      label="Legal last name*"
                      required
                      type="text"
                    ></v-text-field>
                  </validation-provider>
                </v-col>
                <v-col cols="12">
                  <validation-provider
                    v-slot="{ errors }"
                    :rules="{ required: true, email: true, excluded: emails }"
                    name="email"
                  >
                    <v-text-field
                      v-model="user.email"
                      :error-messages="errors"
                      clearable
                      label="Email*"
                      required
                    ></v-text-field>
                  </validation-provider>
                </v-col>
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="error" @click="reset"> Reset</v-btn>
            <v-btn
              :disabled="invalid"
              color="success"
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
  metaInfo: {
    title: "Add New Client",
  },
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
        this.setSnackbarSuccess({
          show: true,
          message: "Client successfully created!",
        });
        await this.$router.push("/manager");
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError({ show: true, message: error.response.data });
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
