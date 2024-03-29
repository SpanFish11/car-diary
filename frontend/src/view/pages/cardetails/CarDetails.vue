<template>
  <v-container fluid>
    <v-row>
      <v-col class="text-center" cols="4">
        <h2>{{ car.brand.name }} {{ car.model.name }}, {{ car.year }}</h2>
        <v-card>
          <v-img v-bind:src="car.photoUrl"/>
        </v-card>
      </v-col>
      <v-col cols="6">
        <h2>Price: {{ car.price }}$</h2>
        <h2 style="color: goldenrod">Equipment information</h2>
        <h4>Engine size: {{ car.equipment.engineSize }}</h4>
        <h4>Fuel type: {{ car.equipment.engineType }}</h4>
        <h4>Transmission type: {{ car.equipment.transmissionType }}</h4>
        <h4>Power: {{ car.equipment.horsePower }} hp</h4>
        <div v-if="car.guarantee !== null">
          <h2 style="color: goldenrod">
            Guarantee
            <v-icon color="green" large>mdi-shield-check-outline</v-icon>
          </h2>
          <h4>Guarantee start: {{ car.guarantee.start }}</h4>
          <h4>Guarantee end: {{ car.guarantee.end }}</h4>
          <h4>Max mileage: {{ car.guarantee.mileage }} km</h4>
          <h4 style="color: green">Number of mileage remaining: {{
              car.guarantee.mileage - car.mileage
            }} km</h4>
          <h4 style="color: green">Number of days remaining: {{
              (Date.parse(car.guarantee.end) - Date.parse(car.guarantee.start)) / (60 * 60 * 24
                  * 1000)
            }}</h4>
          <h4>
            Guarantee extended:
            <v-icon v-if="car.guarantee.extended" color="green" large>
              mdi-check-bold
            </v-icon>
            <v-icon v-else color="red" large>mdi-close-thick</v-icon>
          </h4>
          <div v-if="
              !(
                currentUser().roles.includes('user') &&
                currentUser().roles.length === 1) &&
                !car.guarantee.extended
            ">
            <v-btn
                color="blue"
                outlined
                @click="extendGurantee">
              Extend
            </v-btn>
          </div>
        </div>
        <div v-else>
          <h3>
            No guarantee
            <v-icon color="red" medium>mdi-alert-octagram-outline</v-icon>
          </h3>
          <v-dialog
              v-if="
              !(
                currentUser().roles.includes('user') &&
                currentUser().roles.length === 1
              )
            "
              v-model="dialogGuarantee"
              max-width="350"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn v-bind="attrs" v-on="on" color="primary" dark>
                Add Guarantee
              </v-btn>
            </template>
            <CreateGuarantee v-on:event_create="eventHandler"/>
          </v-dialog>
        </div>
      </v-col>
      <v-col>
        <v-btn color="error" depressed @click="returnHomePage">
          <v-icon left> mdi-arrow-left</v-icon>
          Return
        </v-btn>
      </v-col>
    </v-row>
    <v-row align-v="center">
      <v-col cols="8"><h1>History of service operations</h1></v-col>
      <v-col>
        <v-btn color="primary" @click="printHistoryRecords"> Download</v-btn>
      </v-col>
      <v-col
          v-if="
          !(
            currentUser().roles.includes('user') &&
            currentUser().roles.length === 1
          )
        "
      >
        <router-link
            v-slot="{ navigate }"
            :to="{
            name: 'Add New Service Record',
            params: { id: $route.params.carId },
          }"
            custom
        >
          <v-btn
              color="primary"
              role="link"
              @click="navigate"
              @keypress.enter="navigate"
          >
            Add Service Record
          </v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-data-table
        :headers="headers"
        :items="serviceRecord"
        :items-per-page="5"
        class="elevation-1"
        item-key="id"
    >
      <template
          v-for="header in headers.filter((header) =>
          header.hasOwnProperty('formatter')
        )"
          v-slot:[`item.${header.value}`]="{ value }"
      >
        {{ header.formatter(value) }}
      </template>
      <template v-slot:item="row">
        <tr>
          <td>
            {{ formatServiceRecordNumber(row.item.serviceOperationNumber) }}
          </td>
          <td>
            {{ row.item.date }}
          </td>
          <td>
            {{ row.item.mileage }}
          </td>
          <td>
            <v-dialog v-model="dialogWorks" :retain-focus="false" width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                    class="mx-2"
                    color="grey darken-1"
                    dark
                    fab
                    small
                    @click="editServiceWorks(row.item.serviceWorks)"
                >
                  <v-icon dark>mdi-folder-information-outline</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title> Information about Service Works</v-card-title>
                <v-divider class="mx-4"></v-divider>
                <v-container fluid>
                  <v-card-text v-for="item in serviceWorks" :key="item.id" flat>
                    <v-col>
                      <v-row> Name: {{ item.name }}</v-row>
                      <v-row> Price: {{ item.price }}</v-row>
                      <v-row>
                        Guarantee:
                        <v-icon v-if="item.guarantee" color="green">
                          mdi-emoticon-cool-outline
                        </v-icon>
                        <v-icon v-else color="red">
                          mdi-emoticon-angry-outline
                        </v-icon>
                      </v-row>
                    </v-col>
                  </v-card-text>
                </v-container>
                <v-divider class="mx-4"></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="green darken-1"
                      text
                      @click="dialogWorks = false"
                  >
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </td>
          <td>
            <v-dialog
                v-model="dialogDetails"
                :retain-focus="false"
                width="500px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                    class="mx-2"
                    color="deep-orange lighten-2"
                    dark
                    fab
                    small
                    @click="editDetails(row.item.changableParts)"
                >
                  <v-icon dark>mdi-folder-information-outline</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title> Information about Details</v-card-title>
                <v-divider class="mx-4"></v-divider>
                <v-container fluid>
                  <div v-if="details.length === 0">
                    <v-card-text flat>
                      <v-col>
                        <v-row> No data available</v-row>
                      </v-col>
                    </v-card-text>
                  </div>
                  <div v-else>
                    <v-card-text v-for="item in details" :key="item.id" flat>
                      <v-col>
                        <v-row> Model: {{ item.model }}</v-row>
                        <v-row> Price: {{ item.price }}</v-row>
                        <v-row>
                          Status: {{ item.replaced ? "replaced" : "repaired" }}
                        </v-row>
                      </v-col>
                    </v-card-text>
                  </div>
                </v-container>
                <v-divider class="mx-4"></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="green darken-1"
                      text
                      @click="dialogDetails = false"
                  >
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </td>
        </tr>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import CreateGuarantee from "@/view/pages/manager/CreateGuarantee";
import {mapMutations, mapState} from "vuex";

export default {
  name: "car-details",
  components: {CreateGuarantee},
  props: ["carId"],
  data() {
    return {
      headers: [
        {
          text: "Regular maintenance",
          align: "start",
          sortable: true,
          value: "serviceOperationNumber",
        },
        {text: "Date", value: "date", sortable: true},
        {text: "Mileage", value: "mileage", sortable: true},
        {text: "Service works", value: "serviceWorks", sortable: false},
        {text: "Details", value: "changableParts", sortable: false},
      ],
      car: null,
      dialogWorks: false,
      dialogDetails: false,
      dialogGuarantee: false,
      serviceRecord: [],
      details: [],
      serviceWorks: [],
    };
  },
  computed: {
    rows() {
      return this.items.length;
    },
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  mounted() {
    this.getCarById();
    this.getServiceRecord();
  },
  methods: {
    ...mapMutations({
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    currentUser() {
      return this.$store.state.auth.user;
    },
    returnHomePage() {
      if (
          this.currentUser().roles.includes("user") &&
          this.currentUser().roles.length === 1
      ) {
        this.$router.push({name: "Client Cars", force: true});
      } else {
        this.$router.push({name: "Cars", force: true});
      }
    },
    editDetails(value) {
      this.details = value;
    },
    editServiceWorks(value) {
      this.serviceWorks = value;
    },
    eventHandler() {
      this.dialogGuarantee = false;
      this.$router.go(0);
    },
    async printHistoryRecords() {
      try {
        const response = await CarDiaryDataService.printHistoryOfServiceRecord(
            this.$route.params.carId
        );
        let blob = new Blob([response.data], {type: "application/pdf"});
        let link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = "carOperations.pdf";
        link.click();
      } catch (error) {
        console.log("ERROR: " + error);
        this.setSnackbarError({
          show: true,
          message: error.response.data.message,
        });
      }
    },
    async getCarById() {
      if (
          this.currentUser().roles.includes("user") &&
          this.currentUser().roles.length === 1
      ) {
        try {
          const response = await CarDiaryDataService.getAllClientsCars(
              this.currentUser().userId
          );
          this.car = await response.data.find(
              (car) => car.id == this.$route.params.carId
          );
          if (this.car === undefined) {
            this.setSnackbarError({
              show: true,
              message: "You chose not your car",
            });
            await this.$router.push({name: "Client Cars", force: true});
          }
        } catch (error) {
          console.log("ERROR: " + error.response.data);
          this.setSnackbarError({
            show: true,
            message: error.response.data.message,
          });
        }
      } else {
        await CarDiaryDataService.getCarById(this.$route.params.carId)
        .then((response) => {
          this.car = response.data;
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
          this.setSnackbarError({
            show: true,
            message: error.response.data.message,
          });
        });
      }
    },
    formatServiceRecordNumber(value) {
      return value ? value : "Not regular maintenance";
    },
    async getServiceRecord() {
      try {
        const response = await CarDiaryDataService.getServiceRecordsByCarId(
            this.$route.params.carId
        );
        this.serviceRecord = response.data;
      } catch (error) {
        console.log("ERROR: " + error.response.data);
      }
    },
    async extendGurantee() {
      await CarDiaryDataService.changeGuarantee(this.$route.params.carId)
      .then((response) => {
        this.car.guarantee = response.data;
      })
      .catch((error) => {
        console.log("ERROR: " + error.response.data);
        this.setSnackbarError({
          show: true,
          message: error.response.data.message,
        });
      });
    }
  },
  metaInfo: {
    title: "Car details",
    titleTemplate: "%s | Manager Application",
  },
};
</script>

<style scoped></style>
