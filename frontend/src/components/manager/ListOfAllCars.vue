<template>
  <div id="page">
    <main>
      <b-container fluid>
        <!-- User Interface controls -->
        <b-row>
          <b-col lg="6" class="my-1">
            <b-form-group
                label="Applied filters"
                label-for="filter-input"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0"
            >
              <b-badge v-if="specificYear" class="mr-1 ml-1">Specific Year: {{ specificYear }}</b-badge>
              <b-badge v-if="modelId" class="mr-1 ml-1">{{ modelName }}</b-badge>
              <b-badge v-if="from" class="mr-1 ml-1">From: {{ from }}</b-badge>
              <b-badge v-if="until" class="mr-1 ml-1">Until: {{ until }}</b-badge>
            </b-form-group>

          </b-col>
          <b-col lg="6" class="my-1">
          </b-col>

          <b-col lg="6" class="my-1">
            <b-form-group
                label="Search"
                label-for="filter-input"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0"
            >
              <b-input-group size="sm">
                <template #prepend>
                  <Filters @filterEvent="onFilter"/>
                </template>

                <b-form-input
                    id="filter-input"
                    v-model="filter"
                    type="search"
                    placeholder="Type to Search"
                    :disabled="!filterOn"
                    @input="search(filter)"
                ></b-form-input>

                <template #append>
                  <b-button variant="success" @click="retrieveCars">Search</b-button>
                  <b-button variant="danger" :disabled="!filterOn" @click="clearSearch">Clear</b-button>
                </template>
              </b-input-group>
            </b-form-group>
          </b-col>

          <b-col lg="6" class="my-1">
            <b-form-group
                label="Search by"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0"
                v-slot="{ ariaDescribedby }"
            >
              <b-form-checkbox-group
                  v-model="filterOn"
                  :aria-describedby="ariaDescribedby"
                  class="mt-1"
              >
                <b-form-checkbox value="vin">VIN</b-form-checkbox>
                <b-form-checkbox value="lastname">Last Name</b-form-checkbox>
              </b-form-checkbox-group>
            </b-form-group>
          </b-col>

          <b-col sm="5" md="6" class="my-1">
            <b-form-group
                label="Per page"
                label-for="per-page-select"
                label-cols-sm="6"
                label-cols-md="4"
                label-cols-lg="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0"
            >
              <b-form-select
                  id="per-page-select"
                  v-model="pageSize"
                  :options="pageOptions"
                  size="sm"
                  @change="handlePageSizeChange(pageSize)"
              >
              </b-form-select>
            </b-form-group>
          </b-col>

          <b-col sm="7" md="6" class="my-1">
            <b-pagination
                v-model="page"
                :total-rows="count"
                :per-page="pageSize"
                align="fill"
                size="sm"
                class="my-0"
                @change="handlePageChange"
            ></b-pagination>
          </b-col>
        </b-row>

        <!-- Main table element -->
        <b-table class="mt-2 text-center"
                 :items="cars"
                 :fields="fields"
                 stacked="md"
                 show-empty
                 small
                 :busy="isBusy"
        >
          <template #cell(client)="row">
            {{ row.value.first }} {{ row.value.last }}
          </template>

          <template #cell(actions)="row">
            <b-button size="sm" @click="row.toggleDetails">
              {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
            </b-button>
          </template>

          <template #table-busy>
            <div class="text-center my-2">
              <b-spinner class="align-middle"></b-spinner>
              <strong>Loading...</strong>
            </div>
          </template>

        </b-table>

      </b-container>
    </main>

  </div>
</template>

<script>

import Filters from "@/components/manager/Filters";
import {AXIOS} from "@/backend-api"

export default {
  components: {
    Filters
  },
  name: "ListOfAllCars",
  metaInfo: {
    title: 'All Cars',
    titleTemplate: '%s | Manager Application'
  },
  data: () => ({
    isBusy: false,
    cars: [],
    fields: [
      {key: 'brand'},
      {key: 'model', sortable: true},
      {key: 'vin', label: 'VIN', sortable: true},
      {key: 'year', sortable: true},
      {key: 'mileage', sortable: true},
      {key: 'client', label: 'Client full name', sortable: true},
      {
        key: 'our',
        formatter: (value) => {
          return value ? 'Yes' : 'No'
        }, sortable: true, sortByFormatted: true, filterByFormatted: true
      },
      {key: 'actions', label: 'Actions'}
    ],
    currentIndex: -1,

    page: 1,
    count: 0,
    pageSize: 5,
    pageOptions: [5, 10, 15, {value: 100, text: "Show a lot"}],

    filter: null,
    filterOn: null,

    modelId: null,
    modelName: null,
    specificYear: null,
    from: null,
    until: null
  }),
  computed: {},
  mounted() {
    this.retrieveCars()
  },
  methods: {
    onFilter(data) {
      this.modelId = data.modelId
      this.specificYear = data.specificYear
      this.from = data.fromYear
      this.until = data.untilYear
      this.modelName = data.modelName
      this.retrieveCars()
    },
    search(data) {
      if (data === '') {
        return
      }
      console.log(data)
      this.retrieveCars()
    },
    clearSearch() {
      this.filter = ''
      this.filterOn = null
      this.retrieveCars()
    },
    getRequestParams(page, pageSize, modelId, filterOn, specificYear, from, until) {
      let params = {params: {}};
      (page) ? params.params["page"] = page - 1 : undefined;
      (pageSize) ? params.params["size"] = pageSize : undefined;
      (modelId) ? params.params["modelId"] = modelId : undefined;
      (filterOn === "vin") ? params.params["vin"] = this.filter : undefined;
      (filterOn === "lastname") ? params.params["lastname"] = this.filter : undefined;
      (specificYear) ? params.params["specificYear"] = specificYear : undefined;
      (from) ? params.params["from"] = from : undefined;
      (until) ? params.params["until"] = until : undefined;
      return params;
    },
    async retrieveCars() {
      const params = this.getRequestParams(
          this.page,
          this.pageSize,
          this.modelId,
          this.filterOn,
          this.specificYear,
          this.from,
          this.until
      )

      try {
        this.isBusy = true

        const res = await AXIOS.get('cars', params)
        const {content, totalElements} = res.data
        this.cars = this.resolveResponse(content)
        this.count = totalElements

        this.isBusy = false
      } catch (error) {
        console.log(error.response)
        // handle error
      }
    },
    resolveResponse(content) {
      const arr = []
      content.forEach(function (item) {
        arr.push({
          brand: item.brand.name,
          model: item.model.name,
          vin: item.vin,
          year: item.year,
          mileage: item.mileage,
          client: {first: item.client.firstName, last: item.client.lastName},
          our: item.ours
        })
      })
      return arr;
    },
    handlePageChange(value) {
      this.page = value;
      this.retrieveCars()
    },
    handlePageSizeChange(value) {
      this.pageSize = value;
      this.page = 1;
      this.retrieveCars()
    },
  }
}
</script>

<style scoped>
</style>