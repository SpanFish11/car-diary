<template>
  <v-container fluid>
    <strong>Authorities:</strong>
    <ul>
      <li v-for="(role, index) in currentUser.roles" :key="index">
        {{ role }}
      </li>
    </ul>
    <P>{{ cars }}</P>
  </v-container>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";

export default {
  name: "ClientCars",
  data: () => ({
    cars: [],
  }),
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  mounted() {
    this.getAllCars();
  },
  methods: {
    async getAllCars() {
      try {
        const res = await CarDiaryDataService.getAllClientCars();
        // console.log(res.data)
        this.cars = this.resolveResponse(res.data);
        // console.log(this.cars)
      } catch (error) {
        console.log(error.response);
      }
    },
    // TODO поработать с мапингом
    async getLastAddedCar(carId) {
      let self = this;
      try {
        const res = await CarDiaryDataService.getLastAddedClientCar(carId);
        res.data.forEach(function (item) {
          self.cars.push({
            id: item.id,
            brand: item.brand.name,
            model: item.model.name,
            photo: item.photoUrl,
            vin: item.vin,
            year: item.year,
            mileage: item.mileage,
            // client: {first: item.client.firstName, last: item.client.lastName},
            our: item.ours,
          });
        });
      } catch (error) {
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
  },
};
</script>
