<template>
  <v-container fluid>
    <validation-observer ref="observer" v-slot="{ invalid }">
      <form>
        <v-card @submit.prevent="submit">
          <v-card-title>
            <span class="headline">New Appointments</span>
          </v-card-title>
          <v-card-text>
            <validation-provider
              v-slot="{ errors }"
              rules="required"
              name="repairment"
            >
              <v-radio-group
                v-model="appointment.repairment"
                :error-messages="errors"
                row
              >
                <v-radio label="Repairment" value="true"> </v-radio>
                <v-radio label="Regular Service" value="false"> </v-radio>
              </v-radio-group>
            </validation-provider>
            <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              :return-value.sync="appointment.date"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="appointment.date"
                  label="Picker in menu"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="appointment.date"
                no-title
                scrollable
                v-bind:min="minDate"
              >
                <v-spacer></v-spacer>
                <v-btn text color="primary" @click="menu = false">
                  Cancel
                </v-btn>
                <v-btn
                  text
                  color="primary"
                  @click="$refs.menu.save(appointment.date)"
                >
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>
            <validation-provider
              v-slot="{ errors }"
              rules="required"
              name="maintenances"
              v-if="appointment.repairment === 'false'"
            >
              <v-autocomplete
                v-model="appointment.maintainceId"
                :items="maintenances"
                item-value="id"
                :error-messages="errors"
                label="Regular Service"
                @change="setOperationsAndDetails"
              >
                <template
                  v-slot:append
                  v-if="
                    !(
                      currentOperations.length === 0 &&
                      currentDetails.length === 0
                    )
                  "
                >
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                      <v-icon v-on="on">mdi-help-circle-outline</v-icon>
                    </template>
                    <div
                      v-for="operation in currentOperations"
                      :key="operation.name"
                    >
                      <h3>Service operation</h3>
                      <p>Name: {{ operation.name }}</p>
                      <p>Price: {{ operation.price }}</p>
                    </div>
                    <div v-for="detail in currentDetails" :key="detail.name">
                      <h3>Detail</h3>
                      <p>Model: {{ detail.name }}</p>
                      <p>Price: {{ detail.price }}</p>
                    </div>
                  </v-tooltip>
                </template>
              </v-autocomplete>
            </validation-provider>
            <v-row>
              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  rules="required|max:255"
                  name="description"
                >
                  <v-textarea
                    v-model="appointment.description"
                    :counter="255"
                    :error-messages="errors"
                    color="blue"
                  >
                    <template v-slot:label>
                      <div>Description</div>
                    </template>
                  </v-textarea>
                </validation-provider>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="error" @click="reset"> Reset</v-btn>
            <v-btn
              color="success"
              :disabled="invalid"
              @click="createAppointment"
            >
              Add</v-btn
            >
          </v-card-actions>
        </v-card>
      </form>
    </validation-observer>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import { mapMutations, mapState } from "vuex";

export default {
  name: "CreateAppointment",
  props: ["carId"],
  data: () => ({
    appointment: {
      repairment: null,
      maintainceId: null,
      description: "",
      date: new Date().toISOString().substr(0, 10),
      carId: null,
    },
    menu: false,
    minDate: new Date().toISOString().substr(0, 10),
    maintenances: [],
    currentOperations: [],
    currentDetails: [],
  }),
  mounted() {
    this.loadMaintenances();
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
    async loadMaintenances() {
      try {
        const res = await CarDiaryDataService.getAllMaintenances();
        this.responceData = res.data;
        const arr = [];
        res.data.forEach(function (item) {
          arr.push({
            id: item.id,
            text: item.operationNumber,
            operations: item.operations,
            details: item.details,
          });
        });
        this.maintenances = arr;
      } catch (error) {
        console.log(error.response);
      }
    },
    async setOperationsAndDetails() {
      const maintenance = this.maintenances.find(
        (elem) => elem.id === this.appointment.maintainceId
      );
      this.currentOperations = maintenance.operations;
      this.currentDetails = maintenance.details;
    },
    async createAppointment() {
      this.appointment.carId = this.carId;
      console.log();
      try {
        await CarDiaryDataService.saveAppointment(this.appointment);
        this.reset();
        this.setSnackbarSuccess({
          show: true,
          message: "Appointment created successfully!",
        });
        await this.$router.push("/appointments");
      } catch (error) {
        console.log("ERROR: " + error.response);
        this.setSnackbarError({ show: true, message: error.response.data });
      }
    },
    reset() {
      this.appointment = {
        repairment: null,
        maintainceId: null,
        description: "",
        date: new Date().toISOString().substr(0, 10),
        carId: null,
      };
      this.currentDetails = [];
      this.currentOperations = [];
      this.$refs.observer.reset();
    },
    submit() {
      this.$refs.observer.validate();
    },
  },
};
</script>

<style scoped></style>
