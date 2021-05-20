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
                          <div>Engine size: {{ equipmentInfo.engineSize }}</div>
                          <div>Fuel type: {{ equipmentInfo.engineType }}</div>
                          <div>
                            Transmission type:
                            {{ equipmentInfo.transmissionType }}
                          </div>
                          <div>Power: {{ equipmentInfo.horsePower }} hp</div>
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
                              :disabled="disabled()"
                              :error-messages="errors"
                              clearable
                              label="Mileage*"
                              name="mileage"
                              required
                            ></v-text-field>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" md="4" sm="6">
                          <validation-provider
                            v-slot="{ errors }"
                            :rules="{ required: true }"
                            name="used"
                          >
                            <v-radio-group
                              v-model="car.used"
                              :error-messages="errors"
                              name="used"
                              required
                              row
                              @change="disabled"
                            >
                              <template v-slot:label>
                                <div>New or used*</div>
                              </template>
                              <v-radio label="New" value="false"></v-radio>
                              <v-radio label="Used" value="true"></v-radio>
                            </v-radio-group>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" md="4" sm="6">
                          <validation-provider
                            v-slot="{ errors }"
                            :rules="{ required: true }"
                            name="used"
                          >
                            <v-radio-group
                              v-model="car.ours"
                              :error-messages="errors"
                              name="used"
                              required
                              row
                            >
                              <template v-slot:label>
                                <div>Ours or not*</div>
                              </template>
                              <v-radio label="Ours" value="true"></v-radio>
                              <v-radio label="Not Ours" value="false"></v-radio>
                            </v-radio-group>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" md="8" sm="6">
                          <validation-provider
                            v-slot="{ errors }"
                            :rules="{
                              required: true,
                            }"
                            name="client"
                          >
                            <v-autocomplete
                              v-model="car.clientId"
                              :error-messages="errors"
                              :items="clients"
                              cache-items
                              clearable
                              dense
                              label="Client*"
                              name="client"
                              required
                            >
                              <template v-slot:no-data>
                                <v-container fluid>
                                  You can create a new client by clicking on the
                                  button.
                                </v-container>
                              </template>
                            </v-autocomplete>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" md="4" sm="6">
                          <v-dialog
                            v-model="dialog"
                            max-width="600px"
                            persistent
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-btn
                                v-bind="attrs"
                                v-on="on"
                                color="primary"
                                dark
                              >
                                Add new Client
                              </v-btn>
                            </template>
                            <validation-observer
                              ref="clientObserver"
                              v-slot="{ invalid }"
                            >
                              <form @submit.prevent="saveClient">
                                <v-card>
                                  <v-card-title>
                                    <span class="headline">New Client</span>
                                  </v-card-title>
                                  <v-card-text>
                                    <v-container>
                                      <v-row>
                                        <v-col cols="12" md="6" sm="6">
                                          <validation-provider
                                            v-slot="{ errors }"
                                            :rules="{
                                              required: true,
                                              alpha_spaces: true,
                                            }"
                                            name="first name"
                                          >
                                            <v-text-field
                                              v-model="user.firstName"
                                              :error-messages="errors"
                                              clearable
                                              label="Legal first name*"
                                              required
                                              type="text"
                                            ></v-text-field>
                                          </validation-provider>
                                        </v-col>
                                        <v-col cols="12" md="6" sm="6">
                                          <validation-provider
                                            v-slot="{ errors }"
                                            :rules="{
                                              required: true,
                                              alpha_spaces: true,
                                            }"
                                            name="last name"
                                          >
                                            <v-text-field
                                              v-model="user.lastName"
                                              :error-messages="errors"
                                              clearable
                                              label="Legal last name*"
                                              required
                                              type="text"
                                            ></v-text-field>
                                          </validation-provider>
                                        </v-col>
                                        <v-col cols="12">
                                          <validation-provider
                                            v-slot="{ errors }"
                                            :rules="{
                                              required: true,
                                              email: true,
                                              excluded: emails,
                                            }"
                                            name="email"
                                          >
                                            <v-text-field
                                              v-model="user.email"
                                              :error-messages="errors"
                                              clearable
                                              label="Email*"
                                              required
                                            ></v-text-field>
                                          </validation-provider>
                                        </v-col>
                                      </v-row>
                                    </v-container>
                                    <small>*indicates required field</small>
                                  </v-card-text>
                                  <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn
                                      color="warning"
                                      text
                                      @click="dialog = false"
                                    >
                                      Close
                                    </v-btn>
                                    <v-btn
                                      color="error"
                                      text
                                      @click="resetClient"
                                    >
                                      Reset
                                    </v-btn>
                                    <v-btn
                                      :disabled="invalid"
                                      color="success"
                                      @click="saveClient(user)"
                                    >
                                      Save
                                    </v-btn>
                                  </v-card-actions>
                                </v-card>
                              </form>
                            </validation-observer>
                          </v-dialog>
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
                        <v-img v-if="url" :src="url" contain max-height="400" />
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
                          ? brands.find((brand) => brand.value === car.brandId)
                              .text
                          : null
                      }}
                    </div>
                    <div>
                      Model:
                      {{
                        car.modelId
                          ? models.find((model) => model.value === car.modelId)
                              .text
                          : null
                      }}
                    </div>
                    <div>
                      Equipment:
                      {{
                        car.equipmentId
                          ? equipments.find(
                              (equipment) => equipment.value === car.equipmentId
                            ).text
                          : null
                      }}
                    </div>
                    <div>Manufacture year: {{ car.year }}</div>
                    <div>Price: {{ car.price }}</div>
                    <div>Mileage: {{ car.mileage }}</div>
                    <div>New: {{ car.used === "true" ? "Yes" : "No" }}</div>
                    <div>Ours: {{ car.ours === "true" ? "Yes" : "No" }}</div>
                    <div>
                      Client:
                      {{
                        car.clientId
                          ? clients.find(
                              (client) => client.value === car.clientId
                            ).text
                          : null
                      }}
                    </div>
                    <v-divider></v-divider>
                    <v-container>
                      <v-img v-if="url" :src="url" contain max-height="400" />
                    </v-container>
                  </v-card-text>
                </v-card>

                <v-btn text @click="e1 = 2">
                  <v-icon left> mdi-arrow-left</v-icon>
                  Back
                </v-btn>
                <v-btn class="ml-2" color="orange" @click="resetForm"
                  >Reset
                </v-btn>
                <v-btn
                  :disabled="invalid"
                  class="ml-2"
                  color="primary"
                  @click="saveCar(car)"
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
import CarDiaryDataService from "@/services/CarDiaryDataService";
import NewUser from "@/models/new_user";
import { mapMutations, mapState } from "vuex";

export default {
  name: "SoldCar",
  metaInfo: {
    title: "Add New Car",
  },
  data: () => ({
    car: new NewCar(),
    user: new NewUser(),
    e1: 1,
    dialog: false,
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
    clients: [],
    emails: [],
  }),
  computed: {
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  mounted() {
    this.loadAllClients();
    this.loadAllBrands();
    this.loadAllEquipments();
    this.loadAllEmails();
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    async loadAllEmails() {
      try {
        const res = await CarDiaryDataService.getAllClients();
        res.data.forEach((client) => {
          this.emails.push(client.email);
        });
      } catch (error) {
        console.log(error.response);
      }
    },
    async saveClient(client) {
      this.dialog = false;
      try {
        const res = await CarDiaryDataService.saveNewClient(client);
        await this.loadAllClients();
        this.car.clientId = res.data;
        this.resetClient();
      } catch (error) {
        console.log(error.response);
      }
    },
    resetClient() {
      this.user = new NewUser();
      this.$refs.clientObserver.reset();
    },
    disabled() {
      if (this.car.used === "false") {
        this.car.mileage = 0;
        return true;
      } else {
        return false;
      }
    },
    async loadAllClients() {
      try {
        const { data } = await CarDiaryDataService.getAllClients();
        const arr = [];
        data.forEach(function (item) {
          arr.push({
            value: item.id,
            text: `${item.firstName} ${item.lastName} (${item.email})`,
          });
        });
        this.clients = arr;
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
    async saveCar(car) {
      try {
        const res = await CarDiaryDataService.saveCar(car);
        console.log(res);
        if (this.photoToUpload.photo !== null) {
          const formData = new FormData();
          Object.keys(this.photoToUpload).forEach((key) => {
            formData.append(key, this.photoToUpload[key]);
          });
          await CarDiaryDataService.saveCarImage(res.data, formData);
        }
        this.resetForm();
        await this.$router.push("/manager");
        this.setSnackbarSuccess({
          show: true,
          message: "Car successfully created!",
        });
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError({ show: true, message: error.response.data });
      }
    },
    async loadAllBrands() {
      try {
        const brands = await CarDiaryDataService.getAllBrands();
        this.brands = this.processResponse(brands.data);
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
    onFileChange() {
      if (this.photoToUpload.photo === null) {
        this.url = null;
        return;
      }
      this.url = URL.createObjectURL(this.photoToUpload.photo);
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
      this.car.clientId = null;
      this.car.used = null;
      this.car.ours = null;
    },
  },
};
</script>
