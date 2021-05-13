<template>
    <v-container fluid>
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
                                name="vin"
                                :rules="{
                                  required: true,
                                  min: 17,
                                  max: 17,
                                }"
                            >
                              <v-text-field
                                  label="VIN number*"
                                  name="vin"
                                  :error-messages="errors"
                                  required
                                  :counter="17"
                                  clearable
                                  v-model="car.vin"
                              ></v-text-field>
                            </validation-provider>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="brand"
                                :rules="{ required: true }"
                            >
                              <v-select
                                  :items="brands"
                                  label="Brand*"
                                  name="brand"
                                  required
                                  :error-messages="errors"
                                  clearable
                                  @change="loadAllModelsByBrandId"
                                  v-model="car.brandId"
                              ></v-select>
                            </validation-provider>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="model"
                                :rules="{ required: true }"
                            >
                              <v-select
                                  label="Model*"
                                  name="model"
                                  :items="models"
                                  :error-messages="errors"
                                  required
                                  clearable
                                  v-model="car.modelId"
                              ></v-select>
                            </validation-provider>
                          </v-col>
                          <v-tooltip :disabled="!car.equipmentId" bottom>
                            <template v-slot:activator="{ on, attrs }">
                              <v-col
                                  v-bind="attrs"
                                  v-on="on"
                                  cols="12"
                                  sm="6"
                                  md="4"
                              >
                                <validation-provider
                                    v-slot="{ errors }"
                                    name="equipment"
                                    :rules="{ required: true }"
                                >
                                  <v-select
                                      label="Equipment*"
                                      :items="equipments"
                                      :error-messages="errors"
                                      name="equipment"
                                      required
                                      clearable
                                      @change="loadEquipmentInfo"
                                      v-model="car.equipmentId"
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
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="year"
                                :rules="{
                                  required: true,
                                  numeric: true,
                                  between: [1900, new Date().getFullYear()],
                                  min: 4,
                                  max: 4,
                                }"
                            >
                              <v-text-field
                                  label="Manufacture year*"
                                  name="year"
                                  required
                                  :error-messages="errors"
                                  clearable
                                  v-model="car.year"
                              ></v-text-field>
                            </validation-provider>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="price"
                                :rules="{
                                  required: true,
                                  numeric: true,
                                  min: 1,
                                  max: 7,
                                  regex: '^[0-9]+(\\.[0-9]{1,2})?$',
                                }"
                            >
                              <v-text-field
                                  label="Price*"
                                  name="price"
                                  :error-messages="errors"
                                  required
                                  clearable
                                  v-model="car.price"
                              ></v-text-field>
                            </validation-provider>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="mileage"
                                :rules="{ numeric: true }"
                            >
                              <v-text-field
                                  label="Mileage"
                                  name="mileage"
                                  :error-messages="errors"
                                  clearable
                                  :disabled="disabled"
                                  v-model="car.mileage"
                              ></v-text-field>
                            </validation-provider>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <validation-provider
                                v-slot="{ errors }"
                                name="mileage"
                                :rules="{ required: true }"
                            >
                              <v-radio-group
                                  name="mileage"
                                  row
                                  required
                                  :error-messages="errors"
                                  v-model="car.used"
                              >
                                <template v-slot:label>
                                  <div>New or used*</div>
                                </template>
                                <v-radio label="New" value="false"></v-radio>
                                <v-radio label="Used" value="true"></v-radio>
                              </v-radio-group>
                            </validation-provider>
                          </v-col>
                        </v-row>
                      </v-container>
                      <small>*indicates required field</small>
                    </v-card-text>
                  </v-card>

                  <v-btn :disabled="invalid" color="primary" @click="e1 = 2">
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
                              contain
                              max-height="400"
                              v-if="url"
                              :src="url"
                          />
                        </v-container>
                        <v-file-input
                            @change="onFileChange"
                            v-model="photoToUpload.photo"
                            color="primary accent-4"
                            accept="image/png, image/jpeg"
                            :rules="[
                              (value) =>
                                !value ||
                                value.size < 10000000 ||
                                'Avatar size should be less than 10 MB!',
                            ]"
                            counter
                            label="Photo input"
                            placeholder="Select your files"
                            prepend-icon="mdi-paperclip"
                            :show-size="1000"
                        >
                        </v-file-input>
                      </v-container>
                    </v-card-text>
                  </v-card>

                  <v-btn @click="e1 = 1" text>
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
                            contain
                            max-height="400"
                            v-if="url"
                            :src="url"
                        />
                      </v-container>
                    </v-card-text>
                  </v-card>

                  <v-btn @click="e1 = 2" text>
                    <v-icon left> mdi-arrow-left</v-icon>
                    Back
                  </v-btn>
                  <v-btn class="ml-2" color="orange" @click="resetForm"
                  >Reset
                  </v-btn>
                  <v-btn
                      class="ml-2"
                      color="primary"
                      :disabled="invalid"
                      @click="saveCar()"
                  >Save
                  </v-btn>
                </v-stepper-content>
              </v-stepper-items>
            </v-stepper>
          </v-form>
        </validation-observer>
      </v-card>
    </v-container>
</template>

<script>
import NewCar from "@/models/new_car";

export default {
  name: "SoldCar",
  data: () => ({
    inLoading: false,
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
  methods: {
    saveCar() {},
    loadAllModelsByBrandId() {},
    loadEquipmentInfo() {},
    onFileChange() {},
    resetForm() {},
  },
};
</script>

<style scoped></style>
