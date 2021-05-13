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
        <div v-if="guarantee.length !== 0">
          <h2 style="color: goldenrod">
            Guarantee
            <v-icon color="green" large>mdi-shield-check-outline</v-icon>
          </h2>
          <h4>Guarantee start: {{ guarantee.start }}</h4>
          <h4>Guarantee end: {{ guarantee.end }}</h4>
          <h4>
            Guarantee extended:
            <v-icon color="green" v-if="guarantee.extended" large>
              mdi-check-bold
            </v-icon>
            <v-icon color="red" v-else large>mdi-close-thick</v-icon>
          </h4>
        </div>
        <div v-else>
          <h3>
            No guarantee
            <v-icon color="red" medium>mdi-alert-octagram-outline</v-icon>
          </h3>
          <v-dialog max-width="350"
                    v-model="dialogGuarantee"
                    v-if="!this.$store.state.auth.user.roles.includes('user') && this.$store.state.auth.user.roles.length === 1">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="primary" dark v-bind="attrs" v-on="on">
                Add Guarantee
              </v-btn>
            </template>
            <CreateGuarantee v-on:event_create="eventHandler"/>
          </v-dialog>
        </div>
      </v-col>
      <v-col>
        <v-btn depressed color="error" @click="$router.go(-1)">Return</v-btn>
      </v-col>
    </v-row>
    <v-row align-v="center">
      <v-col cols="8"><h1>History of service operations</h1></v-col>
      <v-col>
        <v-btn color="primary" @click="printHistoryRecords">
          Download
        </v-btn>
      </v-col>
      <v-col
          v-if="!this.$store.state.auth.user.roles.includes('user') && this.$store.state.auth.user.roles.length === 1">
        <router-link
            :to="{
            name: 'Add New Service Record',
            params: { id: $route.params.carId },
          }"
            custom
            v-slot="{ navigate }"
        >
          <v-btn
              color="primary"
              @click="navigate"
              @keypress.enter="navigate"
              role="link">
            Add Service Record
          </v-btn>
        </router-link>
      </v-col>
    </v-row>
    <v-data-table
        :headers="headers"
        :items="serviceRecord"
        item-key="id"
        :items-per-page="5"
        class="elevation-1"
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
            <v-dialog v-model="dialogWorks" width="600px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    class="mx-2"
                    fab
                    dark
                    small
                    color="grey darken-1"
                    v-bind="attrs"
                    v-on="on"
                    @click="editServiceWorks(row.item.serviceWorks)"
                >
                  <v-icon dark>mdi-folder-information-outline</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">Information about Service Works</span>
                </v-card-title>
                <v-card v-for="item in serviceWorks" :key="item.id">
                  <v-col>
                    <v-row>
                      <h5>Name: {{ item.name }}</h5>
                    </v-row>
                    <v-row>
                      <h5>Price: {{ item.price }}</h5>
                    </v-row>
                    <v-row>
                      <h5>Guarantee:</h5>
                      <v-icon color="green" v-if="item.guarantee">
                        mdi-emoticon-cool-outline
                      </v-icon>
                      <v-icon color="red" v-else>
                        mdi-emoticon-angry-outline
                      </v-icon>
                    </v-row>
                  </v-col>
                </v-card>
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
            <v-dialog v-model="dialogDetails" width="600px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    class="mx-2"
                    fab
                    dark
                    small
                    color="deep-orange lighten-2"
                    v-on="on"
                    v-bind="attrs"
                    @click="editDetails(row.item.changableParts)"
                >
                  <v-icon dark>mdi-folder-information-outline</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">Information about Details</span>
                </v-card-title>
                <v-card v-for="item in details" :key="item.id">
                  <h5>Model: {{ item.model }}</h5>
                  <h5>Price: {{ item.price }}</h5>
                  <h5>Status: {{ item.replaced ? "replaced" : "repaired" }}</h5>
                </v-card>
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
      car: [],
      guarantee: [],
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
    this.getGuaranteeById();
    this.getServiceRecord();
  },
  methods: {
    ...mapMutations({
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    editDetails(value) {
      this.details = value;
    },
    editServiceWorks(value) {
      this.serviceWorks = value;
    },
    eventHandler() {
      this.dialogGuarantee = false;
      this.getGuaranteeById();
    },
    async printHistoryRecords() {
      try {
        const response = await CarDiaryDataService.printHistoryOfServiceRecord(
            this.$route.params.carId
        );
        let blob = new Blob([response.data], {type: "application/pdf"});
        let link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob);
        link.download = 'carOperations.pdf';
        link.click();
      } catch (error) {
        console.log("ERROR: " + error);
        this.setSnackbarError(!this.snackbarError);
      }
    },
    getCarById() {
      CarDiaryDataService.getCarById(this.$route.params.carId)
      .then((response) => {
        this.car = response.data;
      })
      .catch((error) => {
        console.log("ERROR: " + error.response.data);
      });
    },
    getGuaranteeById() {
      CarDiaryDataService.getGuaranteeByCarId(this.$route.params.carId)
      .then((response) => {
        this.guarantee = response.data;
      })
      .catch((error) => {
        console.log("ERROR: " + error.response.data);
      });
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
    }
  },
  metaInfo: {
    title: "Car details",
    titleTemplate: "%s | Manager Application",
  },
};
</script>

<style scoped></style>
