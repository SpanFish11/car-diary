<template>
  <main>
    <b-container fluid class="clearfix">
      <b-row>
        <b-col class="text-center" cols="5">
          <h3>{{ car.brand }} {{ car.model }}, {{ car.year }}</h3>
          <b-img
              src="https://www.newcartestdrive.com/wp-content/uploads/2018/03/2018-maxima.jpg"
              alt="Image 1"></b-img>
        </b-col>
        <b-col cols="7">
          <h3>Price: {{ car.price }}</h3>
          <h5>Engine size: {{ car.technical_details.engine_size }}</h5>
          <h5>Fuel type: {{ car.technical_details.fuel_type }}</h5>
          <h5>Transmission type: {{ car.technical_details.transmissions_type }}</h5>
          <h5>Power: {{ car.technical_details.power }}</h5>
          <h4>Guarantee</h4>
          <h5>Some information: Guarantee is a legal term more comprehensive and of higher import than either warranty
            or "security". It most commonly designates a private transaction by means of which one person, to obtain
            some trust, confidence or credit for another, engages to be answerable for him. It may also designate a
            treaty through which claims, rights or possessions are secured. It is to be differentiated from the
            colloquial "personal guarantee" in that a guarantee is a legal concept which produces an economic effect. A
            personal guarantee by contrast is often used to refer to a promise made by an individual which is supported
            by, or assured through, the word of (speak enough) the individual.</h5>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <b-col cols="10"><h1>History of service operations</h1></b-col>
        <b-col>
          <b-button variant="outline-primary"> Print History</b-button>
        </b-col>
      </b-row>
    </b-container>
    <div class="overflow-auto">
      <b-table
          id="my-table"
          :items="items"
          :per-page="perPage"
          :current-page="currentPage"
      >
        <template #cell(guarantee)="data">
          <b-icon icon="emoji-sunglasses" scale="2" variant="success" v-if="data.item.guarantee"></b-icon>
          <b-icon icon="emoji-angry" scale="2" variant="danger" v-else></b-icon>
        </template>
      </b-table>
      <div class="mt-3">
        <b-pagination
            v-model="currentPage"
            :total-rows="rows"
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

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)

export default {
  name: "Main",
  data() {
    return {
      perPage: 5,
      currentPage: 1,
      car: {
        brand: 'Nissan',
        model: 'Maxima',
        year: '2018',
        price: '30090$',
        image_url: 'https://aczpix.b-cdn.net/wp-content/uploads/2015/04/foto-maxima-8_13.jpg',
        technical_details: {
          engine_size: '3.5 L',
          fuel_type: 'Research octane number 96',
          transmissions_type: '6-speed manual 4/5-speed automatic CVT',
          power: '290 horsepower'
        }
      },
      items: [
        {operation_name: 'Basic Lube, Oil, & Filter', date: '18/06/20', price: '$0.00', guarantee: true},
        {operation_name: 'A/C Service', date: '19/01/21', price: '$99.95', guarantee: false},
        {operation_name: 'Charging System Diagnostics', date: '22/11/20', price: '$0.00', guarantee: true},
        {operation_name: 'Used Car Inspection', date: '18/06/20', price: '$0.00', guarantee: true},
        {operation_name: 'Tire Rotation', date: '05/07/19', price: '$21.00', guarantee: false},
        {operation_name: 'Mounting', date: '08/08/20', price: '$0.00', guarantee: true},
        {operation_name: 'Wheel Alignment', date: '19/01/21', price: '$79.95', guarantee: false},
        {operation_name: 'Balancing', date: '05/07/19', price: '$0.00', guarantee: true},
        {operation_name: 'Used Car Inspection', date: '01/02/21', price: '$59.95', guarantee: false},
        {operation_name: 'Charging System Diagnostics', date: '17/09/20', price: '$0.00', guarantee: true},
        {operation_name: 'A/C Service', date: '22/11/20', price: '$79.25', guarantee: false},
        {operation_name: 'Tire Rotation', date: '18/06/20', price: '$21.00', guarantee: false},
        {operation_name: 'Tire Rotation', date: '17/09/20', price: '$0.00', guarantee: true},
        {operation_name: 'Balancing', date: '08/08/20', price: '$0.00', guarantee: true},
        {operation_name: 'Transmission Flush', date: '22/11/20', price: '$0.00', guarantee: true}
      ]
    }
  },
  computed: {
    rows() {
      return this.items.length
    }
  }
}
</script>

<style scoped>
</style>
