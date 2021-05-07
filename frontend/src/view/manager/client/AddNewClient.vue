<template>
  <div class="mb-4 container">
    <validation-observer ref="observer" v-slot="{ handleSubmit }">
      <b-form
        @submit.stop.prevent="handleSubmit(addNewClient)"
        @reset="onReset"
      >
        <validation-provider
          :rules="{ required: true }"
          name="Name"
          v-slot="context"
        >
          <b-form-group
            id="input-group-2"
            label="Client Name:"
            label-for="input-2"
          >
            <b-form-input
              id="input-2"
              v-model="client.firstName"
              placeholder="Enter client name"
              :state="getValidationState(context)"
              required
            ></b-form-input>
            <b-form-invalid-feedback id="input-1-live-feedback">
              {{ context.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          :rules="{ required: true }"
          name="Surname"
          v-slot="context"
        >
          <b-form-group
            id="input-group-2"
            label="Client Surname:"
            label-for="input-2"
          >
            <b-form-input
              id="input-2"
              v-model="client.lastName"
              placeholder="Enter client surname"
              :state="getValidationState(context)"
              required
            ></b-form-input>
            <b-form-invalid-feedback id="input-1-live-feedback">
              {{ context.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          :rules="{ required: true, excluded: checkEmails }"
          name="Email"
          v-slot="context"
        >
          <b-form-group
            id="input-group-1"
            label="Client Email Address:"
            label-for="input-1"
          >
            <b-form-input
              id="input-1"
              v-model="client.email"
              type="email"
              placeholder="Enter email"
              :state="getValidationState(context)"
              required
            ></b-form-input>
            <b-form-invalid-feedback id="input-1-live-feedback">
              {{ context.errors[0] }}
            </b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>
        <b-button-toolbar class="float-right mb-1">
          <b-button-group class="mx-1">
            <b-button type="submit" variant="primary">Submit</b-button>
          </b-button-group>
          <b-button-group class="mx-1">
            <b-button type="reset" variant="danger">Reset</b-button>
          </b-button-group>
        </b-button-toolbar>
      </b-form>
    </validation-observer>
  </div>
</template>

<script>
import { AXIOS } from "@/http-common";

export default {
  name: "addNewClient",
  data: () => ({
    client: {
      email: "",
      firstName: "",
      lastName: "",
    },
    newClientId: 0,
    checkEmails: [],
  }),
  mounted() {
    this.getAllEmails();
  },
  methods: {
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },
    addNewClient() {
      AXIOS.post("/clients", this.client)
        .then((response) => {
          this.newClientId = response.data;
          this.$bvToast.toast("Client added successfully.", {
            title: "Client create successful!",
            variant: "success",
            autoHideDelay: 5000,
            solid: true,
            toaster: "b-toaster-top-center",
            appendToast: false,
          });
          this.$emit("add-client");
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
          this.$bvToast.toast(
            "Sorry, but the client has not been added. Try again.!",
            {
              title: "Something went wrong!",
              variant: "warning",
              autoHideDelay: 5000,
              solid: true,
              toaster: "b-toaster-top-center",
              appendToast: false,
            }
          );
        });
      this.onReset();
      this.getAllEmails();
    },
    onReset() {
      this.$nextTick(() => {
        this.$refs.observer.reset();
      });
    },
    getAllEmails() {
      AXIOS.get("/clients")
        .then((response) => {
          this.checkEmails = [];
          response.data.forEach((client) => {
            this.checkEmails.push(client.email);
          });
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    setFullName() {
      return (
        this.client.firstName +
        " " +
        this.client.lastName +
        "(" +
        this.client.email +
        ")"
      );
    },
  },
  metaInfo: {
    title: "Add Client",
    titleTemplate: "%s | Car Diary",
  },
};
</script>
