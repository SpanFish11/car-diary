<template>
  <div class="mb-4 container">
    <validation-observer ref="observer" v-slot="{ handleSubmit }">
      <b-form ref="form" @submit.stop.prevent="handleSubmit(addNewCar)">
        <validation-provider
          name="Vin"
          :rules="{ required: true, min: 17, max: 17, excluded: vin_numbers }"
          v-slot="validationContext"
        >
          <b-form-group
            id="vin-number-input-group"
            label="VIN number"
            label-for="vin-number-input-group"
          >
            <b-form-input
              id="vin-number-input"
              name="vin-number-input"
              v-model="newCar.vin"
              :state="getValidationState(validationContext)"
              aria-describedby="input-1"
            ></b-form-input>

            <b-form-invalid-feedback id="input-1">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Brand"
          :rules="{ required: true }"
          v-slot="validationContext"
        >
          <b-form-group
            id="brand-input-group"
            label="Brand"
            label-for="brand-input-group"
          >
            <b-form-select
              id="brand-input"
              name="brand-input"
              v-model="newCar.brandId"
              :options="brands"
              @change="loadAllModelsByBrandId"
              :state="getValidationState(validationContext)"
              aria-describedby="input-2"
            ></b-form-select>

            <b-form-invalid-feedback id="input-2">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Model"
          :rules="{ required: true }"
          v-slot="validationContext"
        >
          <b-form-group
            id="model-input-group"
            label="Model"
            label-for="model-input-group"
          >
            <template #first>
              <b-form-select-option :value="null" disabled
                >-- Please select an option --</b-form-select-option
              >
            </template>
            <b-form-select
              id="model-input"
              name="model-input"
              v-model="newCar.modelId"
              :options="models"
              :state="getValidationState(validationContext)"
              aria-describedby="input-3"
            ></b-form-select>
            <b-form-invalid-feedback id="input-3">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Equipment"
          :rules="{ required: true }"
          v-slot="validationContext"
        >
          <b-form-group
            id="equipment-input-group"
            label="Equipment"
            label-for="equipment-input-group"
          >
            <b-form-select
              id="equipment-input"
              name="equipment-input"
              v-model="newCar.equipmentId"
              :options="equipments"
              :state="getValidationState(validationContext)"
              aria-describedby="input-7"
              @change="loadEquipmentInfo"
            ></b-form-select>
            <div v-if="newCar.equipmentId !== null">
              <b-button id="button-1" variant="success">
                Equipment information
              </b-button>
              <b-tooltip target="button-1">
                <h3>Engine size: {{ equipmentInfo.engineSize }}</h3>
                <h3>Fuel type: {{ equipmentInfo.engineType }}</h3>
                <h3>Transmission type: {{ equipmentInfo.transmissionType }}</h3>
                <h3>Power: {{ equipmentInfo.horsePower }}hp</h3>
              </b-tooltip>
            </div>
            <b-form-invalid-feedback id="input-7">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Manufacture year"
          :rules="{
            required: true,
            numeric: true,
            between: [1900, currentYear],
            min: 4,
            max: 4,
          }"
          v-slot="validationContext"
        >
          <b-form-group
            id="year-input-group"
            label="Manufacture year"
            label-for="year-input-group"
          >
            <b-form-input
              id="year-input"
              name="year-input"
              v-model="newCar.year"
              :state="getValidationState(validationContext)"
              aria-describedby="input-4"
            ></b-form-input>
            <b-form-invalid-feedback id="input-5">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Price"
          :rules="{ required: true, regex: '^[0-9]+(\\.[0-9]{1,2})?$' }"
          v-slot="validationContext"
        >
          <b-form-group
            id="price-input-group"
            label="Price"
            label-for="price-input-group"
          >
            <b-form-input
              id="price-input"
              name="price-input"
              v-model="newCar.price"
              :state="getValidationState(validationContext)"
              aria-describedby="input-4"
            ></b-form-input>
            <b-form-invalid-feedback id="input-5">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <validation-provider
          name="Mileage"
          :rules="{ required: false, numeric: true }"
          v-slot="validationContext"
        >
          <b-form-group
            id="mileage-input-group"
            label="Mileage"
            label-for="mileage-input-group"
          >
            <b-form-input
              id="mileage-input"
              name="mileage-input"
              v-model="newCar.mileage"
              :state="getValidationState(validationContext)"
              aria-describedby="input-5"
              :disabled="disabled()"
            ></b-form-input>
            <b-form-invalid-feedback id="input-5">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <div v-if="$route.path === '/manager/soldcar'">
          <validation-provider
            name="Used"
            :rules="{ required: true }"
            v-slot="validationContext"
          >
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                v-model="newCar.used"
                :state="getValidationState(validationContext)"
                :aria-describedby="ariaDescribedby"
                aria-controls="ex-new-used"
              >
                <b-form-radio value="false">New</b-form-radio>
                <b-form-radio value="true">Used</b-form-radio>
              </b-form-radio-group>
              <b-form-invalid-feedback id="input-6">{{
                validationContext.errors[0]
              }}</b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            name="Ours"
            :rules="{ required: true }"
            v-slot="validationContext"
          >
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                v-model="newCar.ours"
                :state="getValidationState(validationContext)"
                :aria-describedby="ariaDescribedby"
                aria-controls="ex-new-used"
              >
                <b-form-radio value="true">Ours</b-form-radio>
                <b-form-radio value="false">Not Ours</b-form-radio>
              </b-form-radio-group>
              <b-form-invalid-feedback id="input-6">{{
                validationContext.errors[0]
              }}</b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            name="Client"
            :rules="{ required: true }"
            v-slot="validationContext"
          >
            <b-form-group
              label="Client"
              id="client-input-group"
              label-for="popover-input-model"
            >
              <b-form-input
                placeholder="Pick a client"
                list="my-list-id"
                v-model="currentClient"
                :state="getValidationState(validationContext)"
              ></b-form-input>
              <datalist id="my-list-id">
                <option v-for="option in clients" v-bind:key="option.id">
                  {{ option.firstName }} {{ option.lastName }} ({{
                    option.email
                  }})
                </option>
              </datalist>
              <b-button v-b-modal.modal variant="outline-dark"
                >Add new client</b-button
              >
              <b-modal id="modal" title="Add new client" hide-footer>
                <addNewClient
                  ref="addClient"
                  @add-client="handleClientEvent"
                ></addNewClient>
              </b-modal>
              <b-form-invalid-feedback id="input-6">{{
                validationContext.errors[0]
              }}</b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>
        </div>

        <validation-provider
          name="Photo"
          :rules="{ required: false, image: true, size: 10000 }"
          v-slot="validationContext"
        >
          <b-form-group
            id="photo-input-group"
            label="Photo"
            label-for="photo-input-group"
          >
            <b-form-file
              id="photo-input"
              name="photo-input"
              accept=".jpg, .png, .jpeg"
              :state="getValidationState(validationContext)"
              v-model="photoToUpload.photo"
              placeholder="Choose a file or drop it here..."
              drop-placeholder="Drop file here..."
              aria-describedby="input-6"
            ></b-form-file>
            <small class="text-muted"
              >Selected file:
              {{ photoToUpload.photo ? photoToUpload.photo.name : "" }}</small
            >

            <b-form-invalid-feedback id="input-6">{{
              validationContext.errors[0]
            }}</b-form-invalid-feedback>
          </b-form-group>
        </validation-provider>

        <b-button-toolbar class="float-right mb-1">
          <b-button-group
            class="mx-1"
            v-if="$route.path !== '/manager/soldcar'"
          >
            <b-button @click="$bvModal.hide('modal')" variant="danger"
              >Close</b-button
            >
          </b-button-group>
          <b-button-group class="mx-1">
            <b-button @click="resetForm" variant="warning">Reset</b-button>
          </b-button-group>
          <b-button-group class="mx-1">
            <b-button
              type="submit"
              variant="success"
              v-if="$route.path !== '/manager/soldcar'"
              >Submit</b-button
            >
            <b-button type="submit" variant="success" @click="addNewCar" v-else
              >Submit</b-button
            >
          </b-button-group>
        </b-button-toolbar>
      </b-form>
    </validation-observer>
  </div>
</template>

<script>
import { AXIOS } from "@/backend-api";
import addNewClient from "@/components/manager/client/AddNewClient";

function processResponse(data) {
  let arr = [{ value: null, text: "Choose..." }];
  data.forEach(function (item) {
    arr.push({ value: item.id, text: item.name });
  });
  return arr;
}

export default {
  name: "AddNewCarModal",
  components: { addNewClient },
  data: () => ({
    brands: [{ value: null, text: "Choose..." }],
    models: [{ value: null, text: "Choose..." }],
    equipments: [{ value: null, text: "Choose..." }],
    newCar: {
      vin: null,
      brandId: null,
      modelId: null,
      equipmentId: null,
      year: null,
      mileage: null,
      price: null,
      clientId: null,
      used: null,
      ours: null,
    },
    currentClient: null,
    equipmentInfo: {
      engineSize: null,
      engineType: null,
      horsePower: null,
      transmissionType: null,
    },
    currentYear: new Date().getFullYear(),
    vin_numbers: [],
    clients: [],
    photoToUpload: {
      photo: null,
    },
  }),
  mounted() {
    this.loadAllBrands();
    this.loadAllVins();
    this.getAllClients();
    this.loadAllEquipments();
  },
  methods: {
    loadAllBrands() {
      AXIOS.get("brands")
        .then((response) => {
          this.brands = processResponse(response.data);
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    loadAllModelsByBrandId() {
      this.newCar.modelId = null;
      if (this.newCar.brandId === null) {
        this.models = [{ value: null, text: "Choose..." }];
        this.newCar.modelId = null;
      } else
        AXIOS.get(`brands/${this.newCar.brandId}/models`)
          .then((response) => {
            this.models = processResponse(response.data);
          })
          .catch((error) => {
            console.log("ERROR: " + error.response.data);
          });
    },
    loadAllEquipments() {
      AXIOS.get("equipments")
        .then((response) => {
          this.equipments = processResponse(response.data);
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },
    resetForm() {
      this.newCar = {
        vin: null,
        brandId: null,
        modelId: null,
        year: null,
        mileage: null,
        equipmentId: null,
      };
      this.currentClient = null;
      this.equipmentInfo = {
        engineSize: null,
        engineType: null,
        horsePower: null,
        transmissionType: null,
      };
      this.photoToUpload = {
        photo: null,
      };
      this.$nextTick(() => {
        this.$refs.observer.reset();
      });
    },
    loadEquipmentInfo() {
      AXIOS.get("equipments")
        .then((response) => {
          this.equipmentInfo = response.data.find(
            (equipment) => equipment.id === this.newCar.equipmentId
          );
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    addNewCar() {
      if (this.newCar.mileage == null) this.newCar.mileage = 0;
      if (this.$route.path === "/manager/soldcar") {
        this.newCar.clientId = this.clients.find(
          (client) =>
            client.email === this.currentClient.match(/\((.*)\)/).pop()
        ).id;
        AXIOS.post("cars", this.newCar)
          .then((response) => {
            this.photoToUpload.photo != null
              ? this.uploadPhoto(response.data)
              : this.makeToast(response.status);
            this.resetForm();
          })
          .catch((error) => {
            console.log("ERROR: " + error.response.data);
            this.makeWarningToast();
          });
      } else {
        AXIOS.post("/clients/1/cars", this.newCar)
          .then((response) => {
            this.photoToUpload.photo != null
              ? this.uploadPhoto(response.data)
              : this.makeToast(response.status);
            this.$bvModal.hide("modal");
            if (this.photoToUpload.photo === null) {
              setTimeout(() => this.$router.go(0), 2000);
            }
          })
          .catch((error) => {
            console.log("ERROR: " + error.response.data);
            this.makeWarningToast();
          });
      }
    },
    async uploadPhoto(data) {
      const formData = new FormData();
      Object.keys(this.photoToUpload).forEach((key) => {
        formData.append(key, this.photoToUpload[key]);
      });
      const promise = await AXIOS.patch(`cars/${data}/photos`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      console.log(promise.status);
      this.makeToast(promise.status);
      if (promise.status === 200) {
        this.$router.go(0);
      } else {
        console.log("ERROR: " + promise.data);
        this.$bvToast.toast(
          `Sorry, but something went wrong with the image upload. Try again while editing the car.`,
          {
            title: "Car create successful!",
            variant: "warning",
            autoHideDelay: 5000,
            solid: true,
            toaster: "b-toaster-top-center",
            appendToast: false,
          }
        );
      }
    },
    makeToast(status = Number()) {
      if (status === 200 || status === 201) {
        this.$bvToast.toast(`You're breathtaking! ❤`, {
          title: "Car create successful!",
          variant: "success",
          autoHideDelay: 5000,
          solid: true,
          toaster: "b-toaster-top-center",
          appendToast: false,
        });
      }
    },
    makeWarningToast() {
      this.$bvToast.toast(
        `Sorry, but the car has not been added. Try again.!`,
        {
          title: "Something went wrong :(",
          variant: "danger",
          autoHideDelay: 5000,
          solid: true,
          toaster: "b-toaster-top-center",
          appendToast: false,
        }
      );
    },
    loadAllVins() {
      AXIOS.get("/clients")
        .then((response) => {
          this.vin_numbers = [];
          response.data.forEach((client) =>
            AXIOS.get("/clients/" + client.id + "/cars")
              .then((response) => {
                response.data.forEach((car) => {
                  this.vin_numbers.push(car.vin);
                });
              })
              .catch((error) => {
                console.log("ERROR: " + error.response.data);
              })
          );
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    disabled() {
      if (this.newCar.used === "false") {
        this.newCar.mileage = null;
        return true;
      }
      return false;
    },
    getAllClients() {
      AXIOS.get("/clients")
        .then((response) => {
          this.clients = response.data;
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    handleClientEvent() {
      this.currentClient = this.$refs.addClient.setFullName();
      this.$bvModal.hide("modal");
      this.getAllClients();
    },
  },
};
</script>

<style scoped></style>
