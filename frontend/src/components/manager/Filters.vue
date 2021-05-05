<template>
  <div>
    <div>
      <!-- Our triggering (target) element -->
      <b-button id="popover-reactive-1" size="sm" ref="button"
        >Filters</b-button
      >
    </div>

    <!-- Our popover title and content render container -->
    <!-- We use placement 'auto' so popover fits in the best spot on viewport -->
    <!-- We specify the same container as the trigger button, so that popover is close to button -->
    <b-popover
      target="popover-reactive-1"
      triggers="click"
      :show.sync="popoverShow"
      placement="auto"
      container="my-container"
      ref="popover"
      @show="onShow"
      @shown="onShown"
      @hidden="onHidden"
    >
      <template #title>
        <b-button @click="onClose" class="close" aria-label="Close">
          <span class="d-inline-block" aria-hidden="true">&times;</span>
        </b-button>
        Interactive Filters
      </template>

      <div>
        <b-form-group
          label="Specific Year"
          label-for="popover-specific-year"
          label-cols="3"
          class="mb-1"
        >
          <b-form-input
            ref="input1"
            id="popover-specific-year"
            v-model="input1"
            size="sm"
            type="number"
            placeholder="Enter year"
            :disabled="input2.length > 0 || input3.length > 0"
          ></b-form-input>
        </b-form-group>

        <b-form-group
          label="Period"
          label-for="popover-period-year"
          label-cols="3"
          class="mb-1"
        >
          <b-input-group size="sm">
            <b-form-input
              :disabled="input1.length > 0"
              v-model="input2"
              type="number"
              placeholder="From year"
              aria-label="First name"
            ></b-form-input>
            <b-form-input
              :disabled="input1.length > 0"
              v-model="input3"
              type="number"
              placeholder="Until year"
              aria-label="Last name"
            ></b-form-input>
          </b-input-group>
        </b-form-group>

        <b-form-group
          label="Model"
          label-for="popover-input-model"
          label-cols="3"
          class="sm"
        >
          <b-form-input
            placeholder="Pick a model"
            list="my-list-id"
            v-model="model"
          ></b-form-input>

          <datalist id="my-list-id">
            <option v-for="option in options" v-bind:key="option.id">
              {{ option.text }}
            </option>
          </datalist>
        </b-form-group>

        <b-button @click="onClose" class="mr-2" size="sm" variant="danger"
          >Cancel</b-button
        >
        <b-button @click="onReset" class="mr-2" size="sm" variant="warning"
          >Reset</b-button
        >
        <b-button @click="onOk()" size="sm" variant="success">Apply</b-button>
      </div>
    </b-popover>
  </div>
</template>

<script>
function processResponse(data) {
  let arr = [];
  data.forEach(function (model) {
    arr.push({ id: model.id, text: model.name });
  });
  return arr;
}

import { AXIOS } from "@/backend-api";

export default {
  data() {
    return {
      model: "",
      input1: "",
      input2: "",
      input3: "",
      options: [],

      inputSpecificYear: null,
      inputFromYear: null,
      inputUntilYear: null,
      inputModelId: null,
      modelName: null,
      popoverShow: false,
    };
  },
  mounted() {
    this.loadAllModels();
  },
  methods: {
    onReset() {
      this.model = "";
      this.input1 = "";
      this.input2 = "";
      this.input3 = "";
      this.setFields();
      this.pushEvent();
    },
    loadAllModels() {
      AXIOS.get("brands")
        .then((response) => {
          response.data.forEach((brand) => {
            this.loadModelByBrandId(brand.id);
          });
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    loadModelByBrandId(id) {
      AXIOS.get(`brands/${id}/models`)
        .then((response) => {
          this.options.push.apply(this.options, processResponse(response.data));
        })
        .catch((error) => {
          console.log("ERROR: " + error.response.data);
        });
    },
    onClose() {
      this.popoverShow = false;
    },
    onOk() {
      this.setFields();
      this.pushEvent();
      this.onClose();
    },
    setFields() {
      this.inputSpecificYear = this.input1;
      this.inputFromYear = this.input2;
      this.inputUntilYear = this.input3;
      const tmp = this.model;
      if (this.model.length !== 0) {
        const { id, text } = this.options.find(function (element) {
          return element.text === tmp;
        });
        this.inputModelId = id;
        this.modelName = text;
      } else if (this.model.length === 0) {
        this.inputModelId = null;
      }
    },
    pushEvent() {
      this.$emit("filterEvent", {
        specificYear: this.inputSpecificYear,
        fromYear: this.inputFromYear,
        untilYear: this.inputUntilYear,
        modelId: this.inputModelId,
        modelName: this.modelName,
      });
    },
    onShow() {
      // This is called just before the popover is shown
      // Reset our popover form variables
    },
    onShown() {},
    onHidden() {
      // Called just after the popover has finished hiding
      // Bring focus back to the button
      this.focusRef(this.$refs.button);
    },
    focusRef(ref) {
      // Some references may be a component, functional component, or plain element
      // This handles that check before focusing, assuming a `focus()` method exists
      // We do this in a double `$nextTick()` to ensure components have
      // updated & popover positioned first
      this.$nextTick(() => {
        this.$nextTick(() => {
          (ref.$el || ref).focus();
        });
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
