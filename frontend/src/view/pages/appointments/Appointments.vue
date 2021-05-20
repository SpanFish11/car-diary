<template>
  <v-container>
    <div v-if="appointments.length !== 0">
      <v-simple-table>
        <template v-slot:default>
          <thead>
          <tr>
            <td class="text-left">
              Type of works
            </td>
            <td class="text-left">
              Car Information
            </td>
            <td class="text-left">
              Description
            </td>
            <td class="text-left">
              Date
            </td>
            <td class="text-left">
              Status
            </td>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in appointments" :key="item.id">
            <td>
              <div v-if="item.repairment">
                <v-btn
                    color="deep-orange lighten-2"
                    disabled>
                  Repairment
                </v-btn>
              </div>
              <div v-else>
                <v-dialog v-model="dialogRegularService" width="500px" :retain-focus="false">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        color="deep-orange lighten-2"
                        v-on="on"
                        v-bind="attrs"
                        @click="editRegularService(item.regularService)">
                      {{ item.regularService.operationNumber }}
                    </v-btn>
                  </template>
                  <v-card>
                    <v-card-title> Information about Regular Service</v-card-title>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-text flat>
                      <div v-for="operation in regularService.operations" :key="operation.name">
                        <h3 style="color: darkblue">Service operation</h3>
                        <p style="color: black">Name: {{ operation.name }}</p>
                        <p style="color: black">Price: {{ operation.price }}</p>
                      </div>
                      <div v-for="detail in regularService.details" :key="detail.name">
                        <h3 style="color: goldenrod">Detail</h3>
                        <p style="color: black">Model: {{ detail.name }}</p>
                        <p style="color: black">Price: {{ detail.price }}</p>
                      </div>
                    </v-card-text>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                          color="green darken-1"
                          text
                          @click="dialogRegularService = false">
                        Close
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>
            </td>
            <td>
              <router-link
                  :to="{
                name: 'Car details',
                params: { carId: item.car.id },
              }"
                  custom
                  v-slot="{ navigate }">
                <v-btn
                    @click="navigate"
                    @keypress.enter="navigate"
                    role="link"
                    color="primary">
                  Details
                </v-btn>
              </router-link>
            </td>
            <td>{{ item.description }}</td>
            <td>{{ item.date }}</td>
            <td v-if="(currentUser().roles.includes('user') && currentUser().roles.length === 1)">
              {{ item.status }}
            </td>
            <td v-else>
              <v-edit-dialog
                  :return-value.sync="item.status"
                  persistent
                  @save="save(item.id, item.status)"
                  @cancel="cancel"
              >
                {{ item.status }}
                <template v-slot:input>
                  <v-select
                      v-model="item.status"
                      :items="statuses"
                      label="Edit"
                      single-line
                      counter
                  ></v-select>
                </template>
              </v-edit-dialog>
            </td>

          </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
    <div v-else>
      <v-alert :value="true" color="error" icon="mdi-vlc">
        Sorry, nothing to display here :(
      </v-alert>
    </div>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import {mapMutations, mapState} from "vuex";

export default {
  name: "ClientAppoinments",
  data: () => ({
    appointments: [],
    dialogRegularService: false,
    regularService: {},
    statuses: ["PENDING", "CONFIRM", "COMPLETED", "DENIED"]
  }),
  mounted() {
    this.loadAllAppointments();
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
    currentUser() {
      return this.$store.state.auth.user;
    },
    async loadAllAppointments() {
      try {
        let response;
        if (this.currentUser().roles.includes("user") &&
            this.currentUser().roles.length === 1) {
          response = await CarDiaryDataService.getAllClientAppointments();
        } else {
          response = await CarDiaryDataService.getAllAppointments();
        }
        this.appointments = response.data;
      } catch (error) {
        console.log(error.response);
      }
    },
    editRegularService(value) {
      this.regularService = value;
    },
    async save(id, status) {
      try {
        await CarDiaryDataService.changeAppointmentStatus(id, status);
        this.setSnackbarSuccess({show: true, message: "Status changed successfully!",});
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError({show: true, message: error.response.data});
      }
    },
    cancel() {
      this.snack = true
      this.snackColor = 'error'
      this.snackText = 'Canceled'
    }
  }
}
</script>

<style scoped>

</style>