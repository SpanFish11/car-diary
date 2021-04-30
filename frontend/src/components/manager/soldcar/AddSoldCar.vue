<template>
  <div class="mb-4 container">
    <validation-observer ref="observer" v-slot="{ handleSubmit }">
      <b-form ref="form" @submit.stop.prevent="handleSubmit(addNewCar)">

        <validation-provider
            name="Vin"
            :rules="{ required: true, min: 17, max: 17 }"
            v-slot="validationContext">
          <b-form-group id="vin-number-input-group" label="VIN number" label-for="vin-number-input-group">
            <b-form-input
                id="vin-number-input"
                name="vin-number-input"
                v-model="newCar.vin"
                :state="getValidationState(validationContext)"
                aria-describedby="input-1"
            ></b-form-input>
            <b-form-invalid-feedback id="input-1">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider name="Brand" :rules="{ required: true }" v-slot="validationContext">
          <b-form-group id="brand-input-group" label="Brand" label-for="brand-input-group">
            <b-form-select
                id="brand-input"
                name="brand-input"
                v-model="newCar.brandId"
                :options="brands"
                @change="loadAllModelsByBrandId"
                :state="getValidationState(validationContext)"
                aria-describedby="input-2"
            ></b-form-select>
            <b-form-invalid-feedback id="input-2">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider name="Model" :rules="{ required: true }" v-slot="validationContext">
          <b-form-group id="model-input-group" label="Model" label-for="model-input-group">
            <template #first>
              <b-form-select-option :value="null" disabled>-- Please select an option --</b-form-select-option>
            </template>
            <b-form-select
                id="model-input"
                name="model-input"
                v-model="newCar.modelId"
                :options="models"
                :state="getValidationState(validationContext)"
                aria-describedby="input-3"
            ></b-form-select>
            <b-form-invalid-feedback id="input-3">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider name="Manufacture year"
                             :rules="{ required: true, numeric: true, between: [1900,currentYear], min: 4, max: 4 }"
                             v-slot="validationContext">
          <b-form-group id="year-input-group" label="Manufacture year" label-for="year-input-group">
            <b-form-input
                id="year-input"
                name="year-input"
                v-model="newCar.year"
                :state="getValidationState(validationContext)"
                aria-describedby="input-4"
            ></b-form-input>
            <b-form-invalid-feedback id="input-4">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <b-form-group v-slot="{ ariaDescribedby }">
          <b-form-radio-group
              v-model="newCar.new"
              :aria-describedby="ariaDescribedby"
              aria-controls="ex-new-used">
            <b-form-radio value="true">New</b-form-radio>
            <b-form-radio value="false">Used</b-form-radio>
          </b-form-radio-group>
        </b-form-group>

        <validation-provider name="Mileage" :rules="{ required: false, numeric: true }" v-slot="validationContext">
          <b-form-group id="mileage-input-group" label="Mileage" label-for="mileage-input-group">
            <b-form-input
                id="mileage-input"
                name="mileage-input"
                v-model="newCar.mileage"
                :state="getValidationState(validationContext)"
                aria-describedby="input-5"
                :disabled="disabled()"
            ></b-form-input>

            <b-form-invalid-feedback id="input-5">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider name="Price" :rules="{ required: false, numeric: true }" v-slot="validationContext">
          <b-form-group id="price-input-group" label="Price" label-for="price-input-group">
            <b-form-input
                id="price-input"
                name="price-input"
                v-model="newCar.price"
                :state="getValidationState(validationContext)"
                aria-describedby="input-6"
            ></b-form-input>

            <b-form-invalid-feedback id="input-6">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <b-button-toolbar class="float-right mb-1">
          <b-button-group class="mx-1">
            <b-button @click="resetForm" variant="warning">Reset</b-button>
          </b-button-group>
          <b-button-group class="mx-1">
            <b-button type="submit" variant="success">Submit</b-button>
          </b-button-group>
        </b-button-toolbar>

      </b-form>
    </validation-observer>
  </div>
</template>

<script>
import {AXIOS} from "@/backend-api"

function processResponse(data) {
  let arr = [{value: null, text: "Choose..."}]
  data.forEach(function (item) {
    arr.push({value: item.id, text: item.name})
  })
  return arr
}

export default {
  name: "AddSoldCar",
  data: () => ({
    brands: [{value: null, text: "Choose..."}],
    models: [{value: null, text: "Choose..."}],
    newCar: {
      vin: null,
      brandId: null,
      modelId: null,
      year: null,
      mileage: null,
      price: null,
      new: false
    },
    newCarId: 0,
    currentYear: new Date().getFullYear()
  }),
  mounted() {
    this.loadAllBrands();
  },
  methods: {
    loadAllBrands() {
      AXIOS.get('brands').then(response => {
        this.brands = processResponse(response.data);
      })
          .catch(error => {
            console.log('ERROR: ' + error.response.data)
          })
    },
    loadAllModelsByBrandId() {
      this.newCar.modelId = null
      if (this.newCar.brandId === null) {
        this.models = [{value: null, text: "Choose..."}]
        this.newCar.modelId = null
      } else
        AXIOS.get(`brands/${this.newCar.brandId}/models`).then(response => {
          this.models = processResponse(response.data);
        }).catch(error => {
          console.log('ERROR: ' + error.response.data)
        })
    },
    getValidationState({dirty, validated, valid = null}) {
      return dirty || validated ? valid : null;
    },
    resetForm() {
      this.newCar = {
        vin: null,
        brandId: null,
        modelId: null,
        year: null,
        mileage: null
      }
      this.$nextTick(() => {
        this.$refs.observer.reset();
      })
    },
    addNewCar() {
      if (this.newCar.mileage == null)
        this.newCar.mileage = 0
      AXIOS.post('cars', this.newCar)
          .then((response) => {
            this.newCarId = response.data;
            this.$bvToast.toast(`You're breathtaking! ❤`, {
              title: 'Car create successful!',
              variant: "success",
              autoHideDelay: 5000,
              solid: true,
              toaster: "b-toaster-top-center",
              appendToast: false
            });
          }).catch((error) => {
        console.log('ERROR: ' + error.response.data)
        this.$bvToast.toast(`Sorry, but the car has not been added. Try again.!`, {
          title: 'Something went wrong :(',
          variant: "danger",
          autoHideDelay: 5000,
          solid: true,
          toaster: "b-toaster-top-center",
          appendToast: false
        })
      });
    },
    disabled() {
      if (this.newCar.new == 'true') {
        this.newCar.mileage = null;
        return true;
      }
      return false;
    }
  }
}
</script>

<style scoped>

</style>