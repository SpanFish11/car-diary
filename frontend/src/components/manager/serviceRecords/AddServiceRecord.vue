<template>
  <div class="container">
    <b-form>
      <div>
        <b-form-input
          placeholder="Pick TO"
          list="my-list-id"
          v-model="TO"
        ></b-form-input>

        <datalist id="my-list-id">
          <option v-for="option in options" v-bind:key="option.id">
            {{ option.text }}
          </option>
        </datalist>
      </div>

      <div class="mt-4">
        <b-form-input
          v-model="mileage"
          type="number"
          placeholder="Enter mileage"
        ></b-form-input>
      </div>

      <div class="mt-2">
        <label>Choose a date</label>
        <b-form-datepicker
          id="example-datepicker"
          v-model="value"
          class="mb-2"
        ></b-form-datepicker>
      </div>

      <!--    добавление работ-->
      <div>
        <b-input-group>
          <b-input-group-prepend is-text>
            <b-form-checkbox v-model="work.guarantee" switch class="mr-n2">
              <span class="sr-only">Switch for following text input</span>
            </b-form-checkbox>
          </b-input-group-prepend>

          <b-form-input
            v-model="work.name"
            type="text"
            placeholder="Work name"
          ></b-form-input>

          <b-form-input
            v-model="work.price"
            type="number"
            placeholder="Work price"
          ></b-form-input>

          <b-input-group-append>
            <b-button size="sm" @click="addServiceWork" text="Button"
              >Add Work</b-button
            >
          </b-input-group-append>
        </b-input-group>

        <b-table
          class="bg-white mt-2 text-center"
          small
          show-empty
          striped
          hover
          :items="serviceWorks"
          :fields="serviceWorksFields"
        >
          <template #cell(actions)="row">
            <b-button
              size="sm"
              @click="deleteServiceWork(row.index)"
              variant="white"
              text="Button"
            >
              <b-icon icon="x-circle" scale="1" variant="danger"></b-icon>
            </b-button>
          </template>
        </b-table>
      </div>

      <!--    добавление деталей-->
      <div>
        <b-input-group>
          <b-input-group-prepend is-text>
            <b-form-checkbox v-model="part.replaced" switch class="mr-n2">
              <span class="sr-only">Switch for following text input</span>
            </b-form-checkbox>
          </b-input-group-prepend>

          <b-form-input
            v-model="part.model"
            type="text"
            placeholder="Part model"
          ></b-form-input>
          <b-form-input
            v-model="part.price"
            type="number"
            placeholder="Part price"
          ></b-form-input>

          <b-input-group-append>
            <b-button size="sm" @click="addServicePart" text="Button"
              >Add part</b-button
            >
          </b-input-group-append>
        </b-input-group>

        <b-table
          class="bg-white mt-2 text-center"
          small
          show-empty
          striped
          hover
          :items="serviceParts"
          :fields="servicePartsFields"
        >
          <template #cell(actions)="row">
            <b-button
              size="sm"
              @click="deleteServicePart(row.index)"
              variant="white"
              text="Button"
            >
              <b-icon icon="x-circle" scale="1" variant="danger"></b-icon>
            </b-button>
          </template>
        </b-table>
      </div>

      <b-button @click="sendGovno" variant="success">Button</b-button>
    </b-form>
  </div>
</template>

<script>
import { AXIOS } from "@/backend-api";

export default {
  name: "AddServiceRecord",
  metaInfo: {
    title: "Add Service Record",
    titleTemplate: "%s | Manager Application",
  },
  data: () => ({
    value: "",
    TO: "",
    mileage: "",
    guaranteeOrNot: false,
    work: {
      guarantee: false,
      name: "",
      price: "",
    },
    part: {
      replaced: false,
      model: "",
      price: "",
    },
    serviceWorks: [],
    serviceParts: [],
    options: [
      { id: "1", text: "TO-0" },
      { id: "2", text: "TO-1 (промежуточное)" },
      { id: "3", text: "TO-2 (полное)" },
      { id: "4", text: "Доп. TO-1" },
      { id: "5", text: "Доп. TO-2" },
      { id: "6", text: "Доп. TO-3" },
    ],
    serviceWorksFields: [
      { key: "guarantee" },
      { key: "name", label: "Work Name" },
      { key: "price", label: "Work Price" },
      { key: "actions", label: "Actions" },
    ],
    servicePartsFields: [
      { key: "replaced" },
      { key: "model" },
      { key: "price" },
      { key: "actions", label: "Actions" },
    ],
  }),
  mounted() {},
  methods: {
    addServiceWork() {
      this.serviceWorks.push({
        guarantee: this.work.guarantee,
        name: this.work.name,
        price: this.work.price,
      });
      this.resetWorkFields();
    },
    deleteServiceWork: function (index) {
      this.serviceWorks.splice(index, 1);
    },
    resetWorkFields() {
      this.work.guarantee = false;
      this.work.name = "";
      this.work.price = "";
    },
    addServicePart() {
      this.serviceParts.push({
        replaced: this.part.replaced,
        model: this.part.model,
        price: this.part.price,
      });
      this.resetPartFields();
    },
    deleteServicePart: function (index) {
      this.serviceParts.splice(index, 1);
    },
    resetPartFields() {
      this.part.replaced = false;
      this.part.model = "";
      this.part.price = "";
    },
    sendGovno() {
      const data = {
        date: this.value,
        mileage: this.mileage,
        serviceOperationNumber: this.TO,
        serviceWorks: this.serviceWorks,
        changableParts: this.serviceParts,
      };
      AXIOS.post(`cars/${1}/operation`, data)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
  },
};
</script>

<style scoped>
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  /* display: none; <- Crashes Chrome on hover */
  -webkit-appearance: none;
  margin: 0; /* <-- Apparently some margin are still there even though it's hidden */
}

input[type="number"] {
  -moz-appearance: textfield; /* Firefox */
}
</style>
