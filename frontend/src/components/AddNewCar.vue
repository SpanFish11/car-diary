<template>
  <div>
    <validation-observer ref="observer" v-slot="{ handleSubmit }">
      <b-form @submit.stop.prevent="handleSubmit(onSubmit)">
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
  </div>
</template>

<script>
export default {
  name: "AddNewCar"
}
</script>

<style scoped>

</style>