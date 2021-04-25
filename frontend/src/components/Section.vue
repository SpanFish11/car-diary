<template>
  <section class="py-5 text-center container">

    <validation-observer ref="observer" v-slot="{ handleSubmit }">
      <b-form @submit.stop.prevent="handleSubmit(addNewCar)">
        <validation-provider
            name="Vin"
            :rules="{ required: true, min: 17, max: 17 }"
            v-slot="validationContext"
        >
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

        <validation-provider name="Mileage" :rules="{ required: false, numeric: true }" v-slot="validationContext">
          <b-form-group id="mileage-input-group" label="Mileage" label-for="mileage-input-group">
            <b-form-input
                id="mileage-input"
                name="mileage-input"
                v-model="newCar.mileage"
                :state="getValidationState(validationContext)"
                aria-describedby="input-5"
            ></b-form-input>

            <b-form-invalid-feedback id="input-5">{{ validationContext.errors[0] }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <b-form-file
            v-model="photoToUpload.photo"
            :state="Boolean(photoToUpload.photo)"
            placeholder="Choose a file or drop it here..."
            drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">Selected file: {{ photoToUpload.photo ? photoToUpload.photo.name : '' }}</div>

        <b-button variant="danger">Close</b-button>
        <b-button variant="warning" class="ml-2" @click="resetForm()">Reset</b-button>
        <b-button type="submit" class="ml-2" variant="success">Submit</b-button>

      </b-form>
    </validation-observer>
  </section>
</template>

<script>

import {AXIOS} from "@/backend-api";

function processResponse(data) {
  let arr = [{value: null, text: "Choose..."}]
  data.forEach(function (item) {
    arr.push({value: item.id, text: item.name})
  })
  return arr
}

export default {
  name: "Section",
  data: () => ({
    brands: [{value: null, text: "Choose..."}],
    models: [{value: null, text: "Choose..."}],
    newCar: {
      vin: null,
      brandId: null,
      modelId: null,
      year: null,
      mileage: null
    },
    currentYear: new Date().getFullYear(),
    photoToUpload: {
      photo: null
    }
  }),
  mounted() {
    this.loadAllBrands()
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
      this.photoToUpload = {
        photo: null
      }

      this.$nextTick(() => {
        this.$refs.observer.reset();
      });
    },
    addNewCar() {
      if (this.newCar.mileage == null)
        this.newCar.mileage = 0

      AXIOS.post('cars', this.newCar)
          .then((response) => {
            this.photoToUpload.photo != null ? this.uploadPhoto(response.data) : console.log(response.status)
          }).catch((error) => {
        console.log('ERROR: ' + error.response.data);
      });
    },
    uploadPhoto(data) {
      const formData = new FormData()
      Object.keys(this.photoToUpload).forEach((key) => {
        formData.append(key, this.photoToUpload[key])
      })
      AXIOS.patch(`cars/${data}/photos`, formData, {headers: {'Content-Type': 'multipart/form-data'}})
          .then((response) => {
            console.log(response.status)
            this.makeToast();
          }).catch(error => {
        console.log('ERROR: ' + error.response.data)
      })
    },
    makeToast(append = false) {
      this.$bvToast.toast(`You're breathtaking`, {
        title: 'Car create successful',
        variant: "success",
        autoHideDelay: 5000,
        solid: true,
        toaster: "b-toaster-top-center",
        appendToast: append
      })
    }
  }
}
</script>

<style scoped>
form {
  max-width: 500px;
  margin: 0 auto;
  text-align: left;
}

.form-group > label {
  font-weight: 600;
}
</style>