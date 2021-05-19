<template>
  <div class="fill-height">
    <v-progress-linear
      :active="inLoading"
      :indeterminate="inLoading"
      absolute
    ></v-progress-linear>

    <v-container fluid>
      <v-container text-center>
        <v-dialog
          v-model="dialog"
          transition="dialog-top-transition"
          width="700"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on" color="primary" elevation="5"
              >Add New Car
            </v-btn>
          </template>

          <v-card flat>
            <validation-observer ref="observer" v-slot="{ invalid }">
              <v-form @submit.prevent="saveCar">
                <v-stepper v-model="e1">
                  <v-stepper-header>
                    <v-stepper-step :complete="e1 > 1" step="1">
                      Car information
                    </v-stepper-step>

                    <v-divider></v-divider>

                    <v-stepper-step :complete="e1 > 2" step="2">
                      Add photo <small>(optional)</small>
                    </v-stepper-step>

                    <v-divider></v-divider>

                    <v-stepper-step step="3"> Verify</v-stepper-step>
                  </v-stepper-header>

                  <v-stepper-items>
                    <v-stepper-content step="1">
                      <v-card class="mb-12" flat>
                        <v-card-text>
                          <v-container fluid>
                            <v-row>
                              <v-col cols="12">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{
                                    required: true,
                                    min: 17,
                                    max: 17,
                                  }"
                                  name="vin"
                                >
                                  <v-text-field
                                    v-model="car.vin"
                                    :counter="17"
                                    :error-messages="errors"
                                    clearable
                                    label="VIN number*"
                                    name="vin"
                                    required
                                  ></v-text-field>
                                </validation-provider>
                              </v-col>
                              <v-col cols="12" md="4" sm="6">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{ required: true }"
                                  name="brand"
                                >
                                  <v-select
                                    v-model="car.brandId"
                                    :error-messages="errors"
                                    :items="brands"
                                    clearable
                                    label="Brand*"
                                    name="brand"
                                    required
                                    @change="loadAllModelsByBrandId"
                                  ></v-select>
                                </validation-provider>
                              </v-col>
                              <v-col cols="12" md="4" sm="6">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{ required: true }"
                                  name="model"
                                >
                                  <v-select
                                    v-model="car.modelId"
                                    :error-messages="errors"
                                    :items="models"
                                    clearable
                                    label="Model*"
                                    name="model"
                                    required
                                  ></v-select>
                                </validation-provider>
                              </v-col>
                              <v-tooltip :disabled="!car.equipmentId" bottom>
                                <template v-slot:activator="{ on, attrs }">
                                  <v-col
                                    v-bind="attrs"
                                    v-on="on"
                                    cols="12"
                                    md="4"
                                    sm="6"
                                  >
                                    <validation-provider
                                      v-slot="{ errors }"
                                      :rules="{ required: true }"
                                      name="equipment"
                                    >
                                      <v-select
                                        v-model="car.equipmentId"
                                        :error-messages="errors"
                                        :items="equipments"
                                        clearable
                                        label="Equipment*"
                                        name="equipment"
                                        required
                                        @change="loadEquipmentInfo"
                                      ></v-select>
                                    </validation-provider>
                                  </v-col>
                                </template>
                                <div>
                                  Engine size: {{ equipmentInfo.engineSize }}
                                </div>
                                <div>
                                  Fuel type: {{ equipmentInfo.engineType }}
                                </div>
                                <div>
                                  Transmission type:
                                  {{ equipmentInfo.transmissionType }}
                                </div>
                                <div>
                                  Power: {{ equipmentInfo.horsePower }} hp
                                </div>
                              </v-tooltip>
                              <v-col cols="12" md="4" sm="6">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{
                                    required: true,
                                    numeric: true,
                                    between: [1900, new Date().getFullYear()],
                                    min: 4,
                                    max: 4,
                                  }"
                                  name="year"
                                >
                                  <v-text-field
                                    v-model="car.year"
                                    :counter="4"
                                    :error-messages="errors"
                                    clearable
                                    label="Manufacture year*"
                                    name="year"
                                    required
                                  ></v-text-field>
                                </validation-provider>
                              </v-col>
                              <v-col cols="12" md="4" sm="6">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{
                                    required: true,
                                    min: 1,
                                    max: 7,
                                    regex: '^([0-9]+)+(\\.[0-9]{1,2})?$',
                                  }"
                                  name="price"
                                >
                                  <v-text-field
                                    v-model="car.price"
                                    :counter="7"
                                    :error-messages="errors"
                                    clearable
                                    label="Price*"
                                    name="price"
                                    required
                                  ></v-text-field>
                                </validation-provider>
                              </v-col>
                              <v-col cols="12" md="4" sm="6">
                                <validation-provider
                                  v-slot="{ errors }"
                                  :rules="{
                                    required: true,
                                    numeric: true,
                                    max_value: 999999,
                                    min_value: 0,
                                  }"
                                  name="mileage"
                                >
                                  <v-text-field
                                    v-model="car.mileage"
                                    :counter="6"
                                    :error-messages="errors"
                                    clearable
                                    label="Mileage*"
                                    name="mileage"
                                    required
                                  ></v-text-field>
                                </validation-provider>
                              </v-col>
                            </v-row>
                          </v-container>
                          <small>*indicates required field</small>
                        </v-card-text>
                      </v-card>

                      <v-btn
                        :disabled="invalid"
                        color="primary"
                        @click="e1 = 2"
                      >
                        Next
                        <v-icon right> mdi-arrow-right</v-icon>
                      </v-btn>
                    </v-stepper-content>

                    <v-stepper-content step="2">
                      <v-card class="mb-12" flat>
                        <v-card-text>
                          <v-container fluid>
                            <v-container>
                              <v-img
                                v-if="url"
                                :src="url"
                                contain
                                max-height="400"
                              />
                            </v-container>
                            <v-file-input
                              v-model="photoToUpload.photo"
                              :rules="[
                                (value) =>
                                  !value ||
                                  value.size < 5000000 ||
                                  'Photo size should be less than 5 MB!',
                              ]"
                              :show-size="1000"
                              accept="image/png, image/jpeg"
                              color="primary accent-4"
                              counter
                              label="Photo input"
                              placeholder="Select your files"
                              prepend-icon="mdi-paperclip"
                              @change="onFileChange"
                            >
                            </v-file-input>
                          </v-container>
                        </v-card-text>
                      </v-card>

                      <v-btn text @click="e1 = 1">
                        <v-icon left> mdi-arrow-left</v-icon>
                        Back
                      </v-btn>
                      <v-btn class="ml-2" color="primary" @click="e1 = 3"
                        >Next
                        <v-icon right> mdi-arrow-right</v-icon>
                      </v-btn>
                    </v-stepper-content>

                    <v-stepper-content step="3">
                      <v-card class="mb-12" flat>
                        <v-card-text>
                          <div>VIN: {{ car.vin }}</div>
                          <div>
                            Brand:
                            {{
                              car.brandId
                                ? brands.find(
                                    (brand) => brand.value === car.brandId
                                  ).text
                                : null
                            }}
                          </div>
                          <div>
                            Model:
                            {{
                              car.modelId
                                ? models.find(
                                    (model) => model.value === car.modelId
                                  ).text
                                : null
                            }}
                          </div>
                          <div>
                            Equipment:
                            {{
                              car.equipmentId
                                ? equipments.find(
                                    (equipment) =>
                                      equipment.value === car.equipmentId
                                  ).text
                                : null
                            }}
                          </div>
                          <div>Manufacture year: {{ car.year }}</div>
                          <div>Price: {{ car.price }}</div>
                          <div>Mileage: {{ car.mileage }}</div>
                          <v-divider></v-divider>
                          <v-container>
                            <v-img
                              v-if="url"
                              :src="url"
                              contain
                              max-height="400"
                            />
                          </v-container>
                        </v-card-text>
                      </v-card>

                      <v-btn text @click="e1 = 2">
                        <v-icon :disabled="inLoading" left>
                          mdi-arrow-left
                        </v-icon>
                        Back
                      </v-btn>
                      <v-btn
                        :disabled="inLoading"
                        class="ml-2"
                        color="orange"
                        @click="resetForm"
                        >Reset
                      </v-btn>
                      <v-btn
                        :disabled="invalid || inLoading"
                        class="ml-2"
                        color="primary"
                        @click="saveCar(currentUser.userId)"
                        >Save
                      </v-btn>
                    </v-stepper-content>
                  </v-stepper-items>
                </v-stepper>
              </v-form>
            </validation-observer>
          </v-card>
        </v-dialog>
      </v-container>

      <v-container>
        <v-item-group v-if="cars.length !== 0">
          <v-row>
            <v-col v-for="n in cars.length" :key="n" md="3">
              <v-item>
                <v-card class="mx-auto my-auto" max-width="374">
                  <v-img :src="cars[n - 1].photo" height="250"></v-img>

                  <v-card-title
                    >{{ cars[n - 1].brand }} {{ cars[n - 1].model }},
                    {{ cars[n - 1].year }}
                  </v-card-title>

                  <v-card-actions>
                    <router-link
                      v-slot="{ navigate }"
                      :to="{
                        name: 'Car details',
                        params: { carId: cars[n - 1].id },
                      }"
                      custom
                    >
                      <v-btn
                        color="primary"
                        role="link"
                        text
                        @click="navigate"
                        @keypress.enter="navigate"
                      >
                        Details
                      </v-btn>
                    </router-link>
                  </v-card-actions>
                  <v-divider></v-divider>
                  <v-card-text>
                    <div>Mileage {{ cars[n - 1].mileage }}</div>
                  </v-card-text>
                </v-card>
              </v-item>
            </v-col>
          </v-row>
        </v-item-group>
      </v-container>
    </v-container>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import CarDiaryDataService from "@/services/CarDiaryDataService";
import NewCar from "@/models/new_car";

export default {
  name: "ClientCars",
  data: () => ({
    inLoading: false,
    dialog: false,
    cars: [],
    car: new NewCar(),
    e1: 1,
    url: null,
    photoToUpload: {
      photo: null,
    },
    brands: [],
    equipments: [],
    models: [],
    equipmentInfo: {
      engineSize: null,
      engineType: null,
      horsePower: null,
      transmissionType: null,
    },
  }),
  mounted() {
    this.getAllCars(this.currentUser.userId);
    this.loadAllBrands();
    this.loadAllEquipments();
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    async getAllCars(clientId) {
      try {
        const res = await CarDiaryDataService.getAllClientsCars(clientId);
        this.cars = this.resolveResponse(res.data);
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
          photo: item.photoUrl,
          year: item.year,
          mileage: item.mileage,
        });
      });
      return arr;
    },
    async saveCar(clientId) {
      this.$refs.observer.validate();
      this.inLoading = true;

      try {
        const res = await CarDiaryDataService.saveNewCar(clientId, this.car);
        if (this.photoToUpload.photo !== null) {
          const formData = new FormData();
          Object.keys(this.photoToUpload).forEach((key) => {
            formData.append(key, this.photoToUpload[key]);
          });
          await CarDiaryDataService.saveCarImage(res.data, formData);
        }
        await this.getLastAddedCar(res.data);
        this.setSnackbarSuccess({
          show: true,
          message: "Car was created successfully!",
        });
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError({ show: true, message: error.response.data });
      }
    },
    async getLastAddedCar(carId) {
      try {
        const res = await CarDiaryDataService.getAllClientsCars(
          this.currentUser.userId
        );
        const cars = res.data;
        const car = cars.find((c) => c.id === carId);
        this.dialog = false;
        this.resetForm();
        await new Promise((resolve) => {
          setTimeout(resolve, 2000);
        });
        this.inLoading = false;
        this.cars.push({
          id: car.id,
          brand: car.brand.name,
          model: car.model.name,
          photo: car.photoUrl,
          year: car.year,
          mileage: car.mileage,
        });
      } catch (error) {
        console.log(error.response);
      }
    },
    onFileChange() {
      if (this.photoToUpload.photo === null) {
        this.url = null;
        return;
      }
      this.url = URL.createObjectURL(this.photoToUpload.photo);
    },
    async loadAllBrands() {
      try {
        const brands = await CarDiaryDataService.getAllBrands();
        this.brands = this.processResponse(brands.data);
      } catch (error) {
        console.log(error.response);
      }
    },
    async loadAllEquipments() {
      try {
        const equipments = await CarDiaryDataService.getAllEquipments();
        this.equipments = this.processResponse(equipments.data);
      } catch (error) {
        console.log(error.response);
      }
    },
    async loadAllModelsByBrandId() {
      this.car.modelId = null;
      if (this.car.brandId === null) {
        this.models = [];
        this.car.modelId = null;
      } else {
        try {
          const models = await CarDiaryDataService.getBrandModels(
            this.car.brandId
          );
          this.models = this.processResponse(models.data);
        } catch (error) {
          console.log(error.response);
        }
      }
    },
    async loadEquipmentInfo() {
      if (this.car.equipmentId === null) {
        return;
      }
      try {
        const equipments = await CarDiaryDataService.getAllEquipments();
        this.equipmentInfo = equipments.data.find(
          (equipment) => equipment.id === this.car.equipmentId
        );
      } catch (error) {
        console.log(error.response);
      }
    },
    processResponse: function (data) {
      const arr = [];
      data.forEach(function (item) {
        arr.push({ value: item.id, text: item.name });
      });
      return arr;
    },
    resetForm() {
      this.$refs.observer.reset();
      this.car.year = null;
      this.car.equipmentId = null;
      this.car.vin = null;
      this.car.brandId = null;
      this.photoToUpload.photo = null;
      this.car.modelId = null;
      this.car.price = null;
      this.car.mileage = null;
      this.e1 = 1;
    },
  },
};
</script>
