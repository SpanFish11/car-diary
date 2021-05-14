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
                          <div>Engine size: {{ equipmentInfo.engineSize }}</div>
                          <div>Fuel type: {{ equipmentInfo.engineType }}</div>
                          <div>
                            Transmission type:
                            {{ equipmentInfo.transmissionType }}
                          </div>
                          <div>Power: {{ equipmentInfo.horsePower }} hp</div>
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
                              :rules="{ required: true, numeric: true }"
                          >
                            <v-text-field
                                label="Mileage*"
                                name="mileage"
                                :error-messages="errors"
                                clearable
                                required
                                :disabled="disabled()"
                                v-model="car.mileage"
                            ></v-text-field>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <validation-provider
                              v-slot="{ errors }"
                              name="used"
                              :rules="{ required: true }"
                          >
                            <v-radio-group
                                name="used"
                                row
                                @change="disabled"
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
                        <v-col cols="12" sm="6" md="4">
                          <validation-provider
                              v-slot="{ errors }"
                              name="used"
                              :rules="{ required: true }"
                          >
                            <v-radio-group
                                name="used"
                                row
                                required
                                :error-messages="errors"
                                v-model="car.ours"
                            >
                              <template v-slot:label>
                                <div>Ours or not*</div>
                              </template>
                              <v-radio label="Ours" value="true"></v-radio>
                              <v-radio label="Not Ours" value="false"></v-radio>
                            </v-radio-group>
                          </validation-provider>
                        </v-col>
                        <v-col cols="12" sm="6" md="8">
                          <validation-provider
                              v-slot="{ errors }"
                              name="client"
                              :rules="{
                              required: true,
                            }"
                          >
                            <v-autocomplete
                                dense
                                label="Client*"
                                name="client"
                                :error-messages="errors"
                                required
                                cache-items
                                clearable
                                :items="clients"
                                v-model="car.clientId"
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
                        <v-col cols="12" sm="6" md="4">
                          <v-dialog
                              v-model="dialog"
                              persistent
                              max-width="600px"
                          >
                            <template v-slot:activator="{ on, attrs }">
                              <v-btn
                                  color="primary"
                                  dark
                                  v-bind="attrs"
                                  v-on="on"
                              >
                                Add new Client
                              </v-btn>
                            </template>
                            <validation-observer ref="clientObserver" v-slot="{ invalid }">
                              <form @submit.prevent="saveClient">
                                <v-card>
                                  <v-card-title>
                                    <span class="headline">New Client</span>
                                  </v-card-title>
                                  <v-card-text>
                                    <v-container>
                                      <v-row>
                                        <v-col cols="12" sm="6" md="6">
                                          <validation-provider
                                              v-slot="{ errors }"
                                              name="first name"
                                              :rules="{ required: true, alpha_spaces: true }"
                                          >
                                            <v-text-field
                                                label="Legal first name*"
                                                v-model="user.firstName"
                                                type="text"
                                                clearable
                                                :error-messages="errors"
                                                required
                                            ></v-text-field>
                                          </validation-provider>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="6">
                                          <validation-provider
                                              v-slot="{ errors }"
                                              name="last name"
                                              :rules="{ required: true, alpha_spaces: true }"
                                          >
                                            <v-text-field
                                                label="Legal last name*"
                                                required
                                                clearable
                                                :error-messages="errors"
                                                type="text"
                                                v-model="user.lastName"
                                            ></v-text-field>
                                          </validation-provider>
                                        </v-col>
                                        <v-col cols="12">
                                          <validation-provider
                                              v-slot="{ errors }"
                                              name="email"
                                              :rules="{ required: true, email: true, excluded: emails }"
                                          >
                                            <v-text-field
                                                label="Email*"
                                                required
                                                clearable
                                                :error-messages="errors"
                                                v-model="user.email"
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
                                    <v-btn color="error" text @click="resetClient"> Reset</v-btn>
                                    <v-btn
                                        color="success"
                                        :disabled="invalid"
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
                        <v-img contain max-height="400" v-if="url" :src="url"/>
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
                      <v-img contain max-height="400" v-if="url" :src="url"/>
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
import {mapMutations, mapState} from "vuex";
export default {
  name: "SoldCar",
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
    emails: []
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
        const {data} = await CarDiaryDataService.getAllClients();
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
        this.resetForm();
        await this.$router.push("/manager");
        this.setSnackbarSuccess(!this.snackbarSuccess);
      } catch (error) {
        console.log(error.response);
        this.setSnackbarError(!this.snackbarError);
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
        arr.push({value: item.id, text: item.name});
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
