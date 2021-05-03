<template>
  <main>
    <b-container fluid class="clearfix">
      <b-row>
        <b-col class="text-center" cols="5">
          <h2>{{ car.brand.name }} {{ car.model.name }}, {{ car.year }}</h2>
          <b-img v-bind:src="car.photoUrl"></b-img>
        </b-col>
        <b-col cols="7">
          <h2>Price: {{ car.price }}$</h2>
          <h3 style="color: goldenrod">Equipment information</h3>
          <h4>Equipment type: {{ car.equipment.name }}</h4>
          <h4>Engine size: {{ car.equipment.engineSize }}</h4>
          <h4>Fuel type: {{ car.equipment.engineType }}</h4>
          <h4>Transmission type: {{ car.equipment.transmissionType }}</h4>
          <h4>Power: {{ car.equipment.horsePower }} hp</h4>
          <h3 style="color: goldenrod">Guarantee</h3>
          <h4>Guarantee start: </h4>
          <b-skeleton width="55%"></b-skeleton>
          <h4>Guarantee end: </h4>
          <b-skeleton width="50%"></b-skeleton>
          <h4>Guarantee type:</h4>
          <b-skeleton width="70%"></b-skeleton>
          <h4>KASKO insurance:</h4>
          <b-skeleton width="95%"></b-skeleton>

        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col cols="10"><h1>History of service operations</h1></b-col>
        <b-col>
          <b-skeleton type="button"></b-skeleton>
        </b-col>
      </b-row>
    </b-container>
    <div class="overflow-auto">
      <b-skeleton-table
          :rows="5"
          :columns="4"
          :table-props="{ bordered: true, striped: true }"
      ></b-skeleton-table>
      <div class="mt-3">
        <b-pagination
            v-model="currentPage"
            :total-rows="15"
            :per-page="perPage"
            aria-controls="my-table"
            size="lg"
            align="center"
        ></b-pagination>
      </div>
    </div>
  </main>
</template>

<script>

import Vue from 'vue'
import {BootstrapVue, BootstrapVueIcons} from 'bootstrap-vue'
import {AXIOS} from "@/backend-api";

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

export default {
  name: "car-details",
  props: ['carId'],
  data() {
    return {
      perPage: 5,
      currentPage: 1,
      car: []
    }
  },
  computed: {
    rows() {
      return this.items.length
    }
  },
  mounted() {
    this.getCarById();
  },
  methods: {
    getCarById() {
      AXIOS.get('/cars/' + this.$route.params.carId).then(response => {
        this.car = response.data;
        console.log(this.car);
      }).catch(error => {
        console.log('ERROR: ' + error.response.data)
      })
    },
    handleAddCarEvent() {
      window.location.reload();
    }
  },
  metaInfo: {
    title: 'Car details',
    titleTemplate: '%s | Manager Application'
  },
}
</script>

<style scoped>
</style>
