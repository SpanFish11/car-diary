<template>
  <v-card elevation="24">
    <v-card-title>Add new Guarantee</v-card-title>
    <v-container fluid>
      <v-menu
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        :return-value.sync="date"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="date"
            label="Start Guarantee"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" no-title scrollable v-bind:max="maxDate">
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
          <v-btn text color="primary" @click="$refs.menu.save(date)">
            OK
          </v-btn>
        </v-date-picker>
      </v-menu>
      <v-switch v-model="extended" inset :label="`Extended`"></v-switch>
      <v-btn
        color="success"
        v-bind:disabled="disableSubmit"
        @click="createGuarantee"
      >
        Submit
      </v-btn>
    </v-container>
  </v-card>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import { mapMutations, mapState } from "vuex";

export default {
  name: "CreateGuarantee",
  metaInfo: {
    title: "Add Guarantee",
  },
  props: ["carId"],
  data() {
    return {
      extended: false,
      date: null,
      menu: false,
      maxDate: new Date().toISOString().substr(0, 10),
      disableSubmit: false,
    };
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
    async createGuarantee() {
      const car = this.$route.params.carId;
      try {
        const guarantee = {
          start: this.date,
          extended: this.extended,
        };
        await CarDiaryDataService.createGuarantee(car, guarantee);
        this.setSnackbarSuccess({'show': true, 'message': 'Guarantee added successfully!'});
        this.disableSubmit = true;
        this.$emit("event_create");
      } catch (error) {
        console.log("ERROR: " + error.response);
        this.setSnackbarError(!this.snackbarError);
      }
    },
  },
};
</script>

<style scoped></style>
