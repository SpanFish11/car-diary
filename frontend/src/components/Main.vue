<template>
  <main>
    <div class="album bg-light">
      <div class="container">

        <b-card-group columns v-if="cars.length !== 0">

          <b-card v-for="car in cars" v-bind:key="car.text"
                  v-bind:title="`${car.brand.name} ${car.model.name}, ${car.year}`"
                  v-bind:img-src="car.photoUrl"
                  img-alt="Image" img-top>
            <b-card-text>
            </b-card-text>
            <b-button-toolbar>
              <b-button-group size="sm">
                <b-button variant="outline-secondary">Details</b-button>
                <b-button variant="outline-secondary">Edit</b-button>
              </b-button-group>
              <b-button-group size="sm" right class="mx-3">
                <b-button variant="outline-secondary">записаться</b-button>
              </b-button-group>
            </b-button-toolbar>
            <template #footer>
              <small class="text-muted">Mileage {{ car.mileage }} km</small>
            </template>
          </b-card>

        </b-card-group>

        <div class="text-center" v-else>
          <p>No cars added</p>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import {AXIOS} from "@/backend-api"

export default {
  name: "Main",
  data: () => ({
    cars: []
  }),

  mounted() {
    this.loadAllCars()
  },
  methods: {
    loadAllCars() {
      AXIOS.get('cars').then(response => {
        this.cars = response.data;
      }).catch(error => {
        console.log('ERROR: ' + error.response.data)
      })
    }
  }
}
</script>

<style scoped>
</style>