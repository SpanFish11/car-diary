<template>
  <main>
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
            <h2 style="color: goldenrod">Guarantee
              <v-icon color="green" large>{{ icons.mdiShieldCheckOutline }}</v-icon>
            </h2>
            <h4>Guarantee start: {{ guarantee.start }}</h4>
            <h4>Guarantee end: {{ guarantee.end }}</h4>
            <h4>Guarantee extended:
              <v-icon color="green" v-if="guarantee.extended" large>
                {{ icons.mdiCheckBold }}
              </v-icon>
              <v-icon color="red" v-else large>{{ icons.mdiCloseThick }}</v-icon>
            </h4>
          </div>
          <div v-else>
            <h3>No guarantee
              <v-icon color="red" medium>{{ icons.mdiAlertOctagramOutline }}</v-icon>
            </h3>
          </div>
        </v-col>
        <v-col>
          <v-btn
              depressed
              color="error"
              @click="$router.go(-1)">Return
          </v-btn>
        </v-col>
      </v-row>
      <v-row align-v="center">
        <v-col cols="9"><h1>History of service operations</h1></v-col>
        <v-col>
          <v-dialog
              v-model="dialogDownload"
              persistent
              max-width="350">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="primary"
                  dark
                  v-bind="attrs"
                  v-on="on">
                Download Record
              </v-btn>
            </template>
            <v-card>
              <v-card-title class="headline">
                Choose filetype and path
              </v-card-title>
              <v-container fluid>
                <v-autocomplete
                    filled
                    v-model="chosenType"
                    :items="types"
                    label="Type"
                ></v-autocomplete>
                <v-text-field label="File Path" outlined prepend-inner-icon="mdi-folder-download"
                              :rules="folderRules" :value="incommingFolder" v-model="incommingFolder"></v-text-field>
              </v-container>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green darken-1"
                    text
                    @click="dialogDownload = false">
                  Close
                </v-btn>
                <v-btn
                    color="green darken-1"
                    @click="printHistoryRecords"
                    text>
                  Download
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
      <v-data-table
          :headers="headers"
          :items="serviceRecord"
          item-key="id"
          :items-per-page="5"
          class="elevation-1">
        <template
            v-for="header in headers.filter((header) =>
                header.hasOwnProperty('formatter'))"
            v-slot:[`item.${header.value}`]="{value }">
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
              <v-dialog
                  v-model="dialogWorks"
                  width="600px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn class="mx-2" fab dark small color="grey darken-1" v-bind="attrs"
                         v-on="on" @click="editServiceWorks(row.item.serviceWorks)">
                    <v-icon dark>{{ icons.mdiFolderInformationOutline }}</v-icon>
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
                        <h5>Guarantee: </h5>
                        <v-icon color="green" v-if="item.guarantee" large>
                          {{ icons.mdiEmoticonCoolOutline }}
                        </v-icon>
                        <v-icon color="red" v-else large>
                          {{ icons.mdiEmoticonAngryOutline }}
                        </v-icon>
                      </v-row>
                    </v-col>
                  </v-card>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="green darken-1"
                        text
                        @click="dialogWorks = false">
                      Close
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </td>
            <td>
              <v-dialog
                  v-model="dialogDetails"
                  width="600px">
                <template v-slot:activator="{on, attrs}">
                  <v-btn class="mx-2" fab dark small color="deep-orange lighten-2" v-on="on" v-bind="attrs"
                         @click="editDetails(row.item.changableParts)">
                    <v-icon dark>{{ icons.mdiFolderInformationOutline }}</v-icon>
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
                        @click="dialogDetails = false">
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
  </main>
</template>

<script>
import Vue from "vue";
import {BootstrapVue, BootstrapVueIcons} from "bootstrap-vue";
import {AXIOS} from "@/http-common";
import {
  mdiAlertOctagramOutline,
  mdiCheckBold,
  mdiCloseThick,
  mdiEmoticonAngryOutline,
  mdiEmoticonCoolOutline,
  mdiFolderInformationOutline,
  mdiShieldCheckOutline
} from '@mdi/js';

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

export default {
  name: "car-details",
  props: ["carId"],
  data() {
    return {
      headers: [
        {
          text: 'Regular maintenance',
          align: 'start',
          sortable: true,
          value: 'serviceOperationNumber'
        },
        {text: 'Date', value: 'date', sortable: true},
        {text: 'Mileage', value: 'mileage', sortable: true},
        {text: "Service works", value: "serviceWorks", sortable: false},
        {text: "Details", value: "changableParts", sortable: false},
      ],
      car: [],
      guarantee: [],
      icons: {
        mdiAlertOctagramOutline,
        mdiShieldCheckOutline,
        mdiCloseThick,
        mdiCheckBold,
        mdiFolderInformationOutline,
        mdiEmoticonCoolOutline,
        mdiEmoticonAngryOutline
      },
      types: ['pdf', 'csv', 'docx', 'html'],
      chosenType: null,
      incommingFolder: 'C:/',
      folderRules: [
        value => {
          const pattern = /^([A-z]:)\/([A-z0-9-_.+]+\/)*([A-z0-9]+)*$/
          return pattern.test(value) || 'Invalid file path'
        },
      ],
      dialogWorks: false,
      dialogDetails: false,
      dialogDownload: false,
      serviceRecord: [],
      details: [],
      serviceWorks: []
    };
  },
  computed: {
    rows() {
      return this.items.length;
    },
  },
  mounted() {
    this.getCarById();
    this.getGuaranteeById();
    this.getServiceRecord();
  },
  methods: {
    editDetails(value) {
      this.details = value;
    },
    editServiceWorks(value) {
      this.serviceWorks = value;
    },
    async printHistoryRecords() {
      try {
        let params = new URLSearchParams();
        params.append('path', this.incommingFolder);
        if (this.chosenType !== null) {
          params.append('fileFormat', this.chosenType);
        }
        await AXIOS.post("/operations/" + this.$route.params.carId + "/print", params);
        this.dialogDownload = false;
      } catch (error) {
        console.log("ERROR: " + error);
      }
    },
    getCarById() {
      AXIOS.get("/cars/" + this.$route.params.carId)
          .then((response) => {
            this.car = response.data;
          })
          .catch((error) => {
            console.log("ERROR: " + error.response.data);
          });
    },
    getGuaranteeById() {
      AXIOS.get("/guarantee/" + this.$route.params.carId)
          .then((response) => {
            this.guarantee = response.data;
          }).catch((error) => {
        console.log("ERROR: " + error.response.data);
      });
    },
    formatServiceRecordNumber(value) {
      return value ? value : "Not regular maintenance";
    },
    async getServiceRecord() {
      try {
        const response = await AXIOS.get("/operations/" + this.$route.params.carId);
        const contents = response.data;
        this.serviceRecord = contents;
      } catch (error) {
        console.log("ERROR: " + error.response.data);
      }
    },
    handleAddCarEvent() {
      this.$router.go(0);
    },
  },
  metaInfo: {
    title: "Car details",
    titleTemplate: "%s | Manager Application",
  },
};
</script>

<style scoped></style>
