<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        <v-toolbar flat>
          <v-toolbar-title>All Cars</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-menu
            v-model="menu"
            :close-on-content-click="false"
            :nudge-width="200"
            offset-x
            transition="scale-transition"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-badge
                :content="apply_filters"
                :value="apply_filters"
                bordered
                color="red"
                overlap
              >
                <v-btn v-bind="attrs" v-on="on" color="primary"> Filters</v-btn>
              </v-badge>
            </template>

            <v-card>
              <v-card-title>Filters</v-card-title>

              <v-divider></v-divider>

              <v-container fluid>
                <v-row align="center">
                  <v-col cols="5">
                    <v-select
                      v-model="filter.modelId"
                      :items="models"
                      clearable
                      hide-details
                      label="Model"
                      menu-props="auto"
                      prepend-icon="mdi-car"
                      single-line
                    ></v-select>
                  </v-col>
                </v-row>
                <v-row align="center">
                  <v-col cols="5">
                    <v-select
                      v-model="filter.specificYear"
                      :disabled="filter.from || filter.until"
                      :items="years"
                      clearable
                      hide-details
                      label="Specific Year"
                      menu-props="auto"
                      prepend-icon="mdi-calendar"
                      single-line
                    ></v-select>
                  </v-col>
                </v-row>
                <v-row align="center">
                  <v-col cols="5">
                    <v-select
                      v-model="filter.from"
                      :disabled="filter.specificYear"
                      :items="years"
                      clearable
                      hide-details
                      label="From"
                      menu-props="auto"
                      prepend-icon="mdi-calendar"
                      single-line
                      @change="generateYears"
                    ></v-select>
                  </v-col>
                  <v-col cols="5">
                    <v-select
                      v-model="filter.until"
                      :disabled="filter.specificYear"
                      :items="years"
                      clearable
                      hide-details
                      label="Until"
                      menu-props="auto"
                      prepend-icon="mdi-calendar"
                      single-line
                      @change="generateYears"
                    ></v-select>
                  </v-col>
                </v-row>
              </v-container>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" text @click="menuCancel"> Cancel</v-btn>
                <v-btn color="orange" text @click="menuReset"> Reset</v-btn>
                <v-btn color="primary" text @click="menuSave"> Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-menu>

          <v-spacer></v-spacer>
          <router-link v-slot="{ navigate }" :to="{ name: 'Sold Car' }" custom>
            <v-btn
              color="primary"
              role="link"
              @click="navigate"
              @keypress.enter="navigate"
            >
              New Car
            </v-btn>
          </router-link>
        </v-toolbar>
      </v-card-title>
      <v-data-table
        :footer-props="{
          showFirstLastPage: true,
          firstIcon: 'mdi-arrow-collapse-left',
          lastIcon: 'mdi-arrow-collapse-right',
          prevIcon: 'mdi-arrow-left',
          nextIcon: 'mdi-arrow-right',
        }"
        :headers="headers"
        :items="cars"
        :loading="loading"
        :options.sync="options"
        :server-items-length="totalCars"
        elevation="1"
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-row>
              <v-col cols="7" sm="6">
                <v-text-field
                  v-model="filter.search"
                  :disabled="!filter.filterOn"
                  append-icon="mdi-magnify"
                  clearable
                  dense
                  hide-details
                  label="Search"
                  single-line
                  @change="getDataFromApi"
                ></v-text-field>
              </v-col>
              <v-col cols="5" sm="3">
                <v-autocomplete
                  v-model="filter.filterOn"
                  :items="searchBy"
                  clearable
                  dense
                  label="Search By"
                  @change="changeSearchBy"
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-toolbar>
        </template>

        <template v-slot:[`item.client`]="{ item }">
          {{ item.client.first }} {{ item.client.last }}
        </template>

        <template v-slot:[`item.our`]="{ item }">
          <v-icon v-if="item.our === true" color="green"
            >mdi-checkbox-marked
          </v-icon>
          <v-icon v-if="item.our === false" color="red">mdi-minus-box</v-icon>
        </template>

        <template v-slot:[`item.actions`]="{ item }">
          <router-link
            v-slot="{ navigate }"
            :to="{
              name: 'Car details',
              params: { carId: item.id },
            }"
            custom
          >
            <v-btn
              color="primary"
              role="link"
              @click="navigate"
              @keypress.enter="navigate"
            >
              Details
            </v-btn>
          </router-link>
          <router-link
            v-slot="{ navigate }"
            :to="{
              name: 'Add New Service Record',
              params: { id: item.id },
            }"
            custom
          >
            <v-btn
              class="ml-1"
              color="primary"
              role="link"
              @click="navigate"
              @keypress.enter="navigate"
            >
              Add Service Record
            </v-btn>
          </router-link>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import Filter from "@/models/filter";

export default {
  name: "CarsTable",
  metaInfo: {
    title: "All Cars",
  },
  data: () => ({
    filter: new Filter(),
    searchBy: [
      { id: 0, text: "VIN" },
      { id: 1, text: "Last Name" },
    ],
    models: [],
    apply_filters: 0,

    menu: false,
    years: [],

    totalCars: 0,
    cars: [],
    loading: true,
    options: {},
    headers: [
      { text: "Brand", value: "brand", sortable: false },
      { text: "Model", value: "model", sortable: false },
      { text: "VIN", value: "vin", sortable: false },
      { text: "Year", value: "year", sortable: false },
      { text: "Mileage", value: "mileage", sortable: false },
      { text: "Client full name", value: "client", sortable: false },
      { text: "Our", value: "our", sortable: false },
      { text: "Actions", value: "actions", sortable: false },
    ],
  }),
  watch: {
    options: {
      handler() {
        this.getDataFromApi();
      },
      deep: true,
    },
  },
  mounted() {
    this.getDataFromApi();
    this.loadAllModels();
    this.generateYears();
  },
  methods: {
    requestParams() {
      const { page, itemsPerPage } = this.options;
      this.filter.page = page;
      this.filter.pageSize = itemsPerPage;
    },
    async getDataFromApi() {
      this.loading = true;
      this.requestParams();
      try {
        const res = await CarDiaryDataService.getAllCars(this.filter.setUp());
        const { content, totalElements } = res.data;
        this.cars = this.resolveResponse(content);
        this.totalCars = totalElements;
        this.loading = false;
      } catch (error) {
        this.loading = false;
        console.log(error.response);
      }
    },
    resolveResponse(content) {
      const arr = [];
      content.forEach(function (item) {
        arr.push({
          id: item.id,
          brand: item.brand.name,
          model: item.model.name,
          vin: item.vin,
          year: item.year,
          mileage: item.mileage,
          client: { first: item.client.firstName, last: item.client.lastName },
          our: item.ours,
        });
      });
      return arr;
    },
    async loadAllModels() {
      let self = this;
      try {
        const response = await CarDiaryDataService.getAllBrands();
        for (const brand of response.data) {
          const res = await CarDiaryDataService.getBrandModels(brand.id);
          res.data.forEach(function (model) {
            self.models.push({ value: model.id, text: model.name });
          });
        }
      } catch (error) {
        console.log(error.response);
      }
    },
    generateYears() {
      const min = this.filter.from ? this.filter.from : 1900;
      const max = this.filter.until
        ? this.filter.until
        : new Date().getFullYear().valueOf();
      const arr = [];
      for (let i = min; i <= max; i++) {
        arr.push(i);
      }
      this.years = arr;
    },
    menuSave() {
      this.menu = false;
      this.getDataFromApi();
    },
    menuCancel() {
      this.menu = false;
      this.resetFilters();
    },
    menuReset() {
      this.resetFilters();
    },
    resetFilters() {
      this.filter.modelId = null;
      this.filter.specificYear = null;
      this.filter.from = null;
      this.filter.until = null;
    },
    changeSearchBy() {
      if (this.filter.filterOn === null) {
        this.filter.search = null;
        this.getDataFromApi();
      }
    },
  },
};
</script>
