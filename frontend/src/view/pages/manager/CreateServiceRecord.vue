<template>
  <validation-observer ref="observer" v-slot="{ invalid }">
    <v-container>
      <v-overlay :value="overlay">
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>

      <v-card elevation="24">
        <v-card-title>Add new service record</v-card-title>

        <v-container fluid>
          <form ref="form" @submit.stop.prevent="submit">
            <v-row align="center">
              <v-col cols="12">
                <v-autocomplete
                  v-model="serviceOperationName"
                  :items="maintenances"
                  clearable
                  dense
                  filled
                  hint="Select maintenance if it is, otherwise, leave it blank"
                  label="Maintenance"
                  persistent-hint
                  @change="someMethod(serviceOperationName)"
                ></v-autocomplete>
              </v-col>

              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  :rules="{
                    required: true,
                    numeric: true,
                    min_value: currentMileage,
                    max_value: 999999,
                  }"
                  name="Mileage"
                >
                  <v-text-field
                    v-model="mileage"
                    :counter="6"
                    :error-messages="errors"
                    :placeholder="`Current value is ${currentMileage}`"
                    clearable
                    dense
                    filled
                    label="Mileage"
                  ></v-text-field>
                </validation-provider>
              </v-col>

              <v-col cols="12">
                <v-dialog
                  ref="dialog"
                  v-model="serviceOperationDate"
                  :return-value.sync="date"
                  persistent
                  width="290px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <validation-provider
                      v-slot="{ errors }"
                      :rules="{ required: true }"
                      name="Date"
                    >
                      <v-text-field
                        v-model="date"
                        v-bind="attrs"
                        v-on="on"
                        :error-messages="errors"
                        clearable
                        dense
                        filled
                        label="Date"
                        placeholder="Choose a date"
                        prepend-icon="mdi-calendar"
                        readonly
                      ></v-text-field>
                    </validation-provider>
                  </template>
                  <v-date-picker v-model="date" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn
                      color="error"
                      elevation="10"
                      text
                      @click="serviceOperationDate = false"
                    >
                      Cancel
                    </v-btn>
                    <v-btn
                      color="success"
                      elevation="10"
                      text
                      @click="$refs.dialog.save(date)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-dialog>
              </v-col>

              <v-col>
                <v-data-table
                  :headers="serviceWorksHeaders"
                  :items="serviceWorks"
                  class="elevation-1"
                  hide-default-footer
                >
                  <template v-slot:top>
                    <v-toolbar dense flat>
                      <v-toolbar-title>Operations</v-toolbar-title>
                      <v-divider class="mx-4" inset vertical></v-divider>
                      <v-spacer></v-spacer>

                      <v-dialog
                        v-model="dialogServiceOperation"
                        max-width="500px"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            v-bind="attrs"
                            v-on="on"
                            class="mb-2"
                            color="primary"
                            dark
                            elevation="10"
                            small
                          >
                            New Operation
                          </v-btn>
                        </template>
                        <v-card>
                          <v-card-title>
                            <span class="headline">{{
                              formServiceOperationTitle
                            }}</span>
                          </v-card-title>

                          <v-form
                            ref="serviceOperationForm"
                            v-model="serviceServiceOperationFormValid"
                          >
                            <v-card-text>
                              <v-container>
                                <v-row align="center">
                                  <v-col cols="12">
                                    <v-select
                                      v-model="
                                        editedServiceOperationItem.guarantee
                                      "
                                      :items="serviceVariable"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                      label="Guarantee"
                                    ></v-select>
                                  </v-col>
                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServiceOperationItem.name"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                      label="Name"
                                      type="text"
                                    ></v-text-field>
                                  </v-col>

                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServiceOperationItem.price"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                        (v) =>
                                          /^(?:0|[1-9][0-9]*)\.?[0-9]+$/.test(
                                            v
                                          ) || 'Only numbers',
                                      ]"
                                      clearable
                                      dense
                                      label="Price"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>
                              </v-container>
                            </v-card-text>

                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                color="red darken-1"
                                elevation="10"
                                text
                                @click="closeServiceOperation"
                              >
                                Cancel
                              </v-btn>
                              <v-btn
                                :disabled="!serviceServiceOperationFormValid"
                                color="green darken-1"
                                elevation="10"
                                text
                                @click="saveServiceOperation"
                              >
                                Save
                              </v-btn>
                            </v-card-actions>
                          </v-form>
                        </v-card>
                      </v-dialog>
                      <v-dialog
                        v-model="dialogServiceOperationDelete"
                        max-width="500px"
                      >
                        <v-card>
                          <v-card-title class="headline"
                            >Are you sure you want to delete this item?
                          </v-card-title>
                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                              color="red darken-1"
                              elevation="10"
                              text
                              @click="closeServiceOperationDelete"
                              >Cancel
                            </v-btn>
                            <v-btn
                              color="green darken-1"
                              elevation="10"
                              text
                              @click="deleteServiceOperationItemConfirm"
                              >OK
                            </v-btn>
                            <v-spacer></v-spacer>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-toolbar>
                  </template>

                  <template v-slot:[`item.guarantee`]="{ item }">
                    <v-simple-checkbox
                      v-model="item.guarantee"
                      dense
                      disabled
                    ></v-simple-checkbox>
                  </template>

                  <template v-slot:[`item.actions`]="{ item }">
                    <v-icon
                      :disabled="item.disabled === true"
                      class="mr-2"
                      color="green"
                      @click="editServiceOperationItem(item)"
                    >
                      mdi-pencil
                    </v-icon>
                    <v-icon
                      :disabled="item.disabled === true"
                      color="red"
                      @click="deleteServiceOperationItem(item)"
                      >mdi-delete
                    </v-icon>
                  </template>
                </v-data-table>
              </v-col>

              <v-col>
                <v-data-table
                  :headers="servicePartsHeaders"
                  :items="serviceParts"
                  class="elevation-1"
                  hide-default-footer
                >
                  <template v-slot:top>
                    <v-toolbar dense flat>
                      <v-toolbar-title>Parts</v-toolbar-title>
                      <v-divider class="mx-4" inset vertical></v-divider>
                      <v-spacer></v-spacer>

                      <v-dialog v-model="dialogServicePart" max-width="500px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            v-bind="attrs"
                            v-on="on"
                            class="mb-2"
                            color="primary"
                            dark
                            elevation="10"
                            small
                          >
                            New Operation Part
                          </v-btn>
                        </template>
                        <v-card>
                          <v-card-title>
                            <span class="headline">{{
                              formServiceOperationPart
                            }}</span>
                          </v-card-title>

                          <v-form
                            ref="servicePartItemForm"
                            v-model="servicePartItemFormValid"
                          >
                            <v-card-text>
                              <v-container>
                                <v-row align="center">
                                  <v-col cols="12">
                                    <v-select
                                      v-model="editedServicePartItem.replaced"
                                      :items="serviceVariable"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                      label="Replaced"
                                    ></v-select>
                                  </v-col>
                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServicePartItem.model"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                      label="Model"
                                      type="text"
                                    ></v-text-field>
                                  </v-col>

                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServicePartItem.price"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                        (v) =>
                                          /^(?:0|[1-9][0-9]*)\.?[0-9]+$/.test(
                                            v
                                          ) || 'Only numbers',
                                      ]"
                                      clearable
                                      dense
                                      label="Price"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>
                              </v-container>
                            </v-card-text>

                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                color="red darken-1"
                                elevation="10"
                                text
                                @click="closeServicePart"
                              >
                                Cancel
                              </v-btn>
                              <v-btn
                                :disabled="!servicePartItemFormValid"
                                color="green darken-1"
                                elevation="10"
                                text
                                @click="saveServicePart"
                              >
                                Save
                              </v-btn>
                            </v-card-actions>
                          </v-form>
                        </v-card>
                      </v-dialog>
                      <v-dialog
                        v-model="dialogServicePartDelete"
                        max-width="500px"
                      >
                        <v-card>
                          <v-card-title class="headline"
                            >Are you sure you want to delete this item?
                          </v-card-title>
                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                              color="red darken-1"
                              elevation="10"
                              text
                              @click="closeServicePartDelete"
                              >Cancel
                            </v-btn>
                            <v-btn
                              color="green darken-1"
                              elevation="10"
                              text
                              @click="deleteServicePartnItemConfirm"
                              >OK
                            </v-btn>
                            <v-spacer></v-spacer>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                    </v-toolbar>
                  </template>

                  <template v-slot:[`item.replaced`]="{ item }">
                    <v-simple-checkbox
                      v-model="item.replaced"
                      dense
                      disabled
                    ></v-simple-checkbox>
                  </template>

                  <template v-slot:[`item.actions`]="{ item }">
                    <v-icon
                      :disabled="item.disabled === true"
                      class="mr-2"
                      color="green"
                      @click="editServicePartItem(item)"
                    >
                      mdi-pencil
                    </v-icon>
                    <v-icon
                      :disabled="item.disabled === true"
                      color="red"
                      @click="deleteServicePartItem(item)"
                      >mdi-delete
                    </v-icon>
                  </template>
                </v-data-table>
              </v-col>
            </v-row>

            <v-container class="mt-5 mb-5">
              <v-row align="stretch" justify="center">
                <v-btn
                  :disabled="invalid"
                  class="mr-4 mt-5"
                  color="success"
                  elevation="10"
                  type="submit"
                >
                  submit
                </v-btn>
                <v-btn class="mt-5" elevation="10" @click="clear">
                  clear
                </v-btn>
              </v-row>
            </v-container>
          </form>
        </v-container>
      </v-card>
    </v-container>
  </validation-observer>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";
import NewRecord from "@/models/new_record";
import { mapMutations, mapState } from "vuex";

export default {
  name: "CreateServiceRecord.vue",
  props: ["id"],
  metaInfo: {
    title: "Add New Service Record",
  },
  data: () => ({
    servicePartItemFormValid: true,
    serviceServiceOperationFormValid: true,
    message: "",
    overlay: false,
    dialogServiceOperation: false,
    dialogServiceOperationDelete: false,
    dialogServicePart: false,
    dialogServicePartDelete: false,
    date: null,
    carId: 1,
    maintenances: [],
    serviceOperationName: null,
    tempServiceOperationName: null,
    mileage: null,
    currentMileage: null,
    serviceOperationDate: false,
    serviceWorksHeaders: [
      {
        text: "Guarantee",
        value: "guarantee",
        sortable: false,
        align: "center",
      },
      {
        text: "Operation Name",
        value: "name",
        sortable: false,
        align: "center",
      },
      {
        text: "Operation Price",
        value: "price",
        sortable: false,
        align: "center",
      },
      { text: "Actions", value: "actions", sortable: false, align: "center" },
    ],
    editedServiceOperationIndex: -1,
    editedServiceOperationItem: {
      guarantee: null,
      name: null,
      price: null,
    },
    defaultServiceOperationItem: {
      guarantee: null,
      name: null,
      price: null,
    },
    serviceWorks: [],
    servicePartsHeaders: [
      { text: "Replaced", value: "replaced", sortable: false, align: "center" },
      { text: "Model", value: "model", sortable: false, align: "center" },
      { text: "Price", value: "price", sortable: false, align: "center" },
      { text: "Actions", value: "actions", sortable: false, align: "center" },
    ],
    editedServicePartIndex: -1,
    editedServicePartItem: {
      replaced: null,
      model: null,
      price: null,
    },
    defaultServicePartItem: {
      replaced: null,
      model: null,
      price: null,
    },
    serviceParts: [],
    serviceVariable: [
      { text: "Yes", value: true },
      { text: "No", value: false },
    ],
    responceData: [],
  }),
  mounted() {
    this.loadMaintenances();
    this.carId = this.$route.params.id;
    this.getCurrentMileage();
  },
  computed: {
    formServiceOperationTitle() {
      return this.editedServiceOperationIndex === -1 ? "New Item" : "Edit Item";
    },
    formServiceOperationPart() {
      return this.editedServicePartIndex === -1 ? "New Item" : "Edit Item";
    },
    ...mapState(["snackbarSuccess"]),
    ...mapState(["snackbarError"]),
  },
  watch: {
    dialogServiceOperation(val) {
      val || this.closeServiceOperation();
    },
    dialogServiceOperationDelete(val) {
      val || this.closeServiceOperationDelete();
    },
    dialogServicePart(val) {
      val || this.closeServicePart();
    },
    dialogServicePartDelete(val) {
      val || this.closeServicePartDelete();
    },
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
    async getCurrentMileage() {
      try {
        const response = await CarDiaryDataService.getCarById(this.carId);
        this.currentMileage = response.data.mileage;
      } catch (error) {
        console.log(error.response);
      }
    },
    async loadMaintenances() {
      try {
        const res = await CarDiaryDataService.getAllMaintenances();
        this.responceData = res.data;
        const arr = [];
        res.data.forEach(function (item) {
          arr.push({ id: item.id, text: item.operationNumber });
        });
        this.maintenances = arr;
      } catch (error) {
        console.log(error.response);
      }
    },
    someMethod(serviceOperationName) {
      const self = this;
      if (serviceOperationName !== null) {
        const s = self.responceData.filter(
          (d) => d.operationNumber === serviceOperationName
        );
        s.forEach(function (item) {
          item.operations.forEach(function (item) {
            self.serviceWorks.push({
              guarantee: true,
              name: item.name,
              price: item.price,
              disabled: true,
            });
          });
          item.details.forEach(function (item) {
            self.serviceParts.push({
              replaced: true,
              model: item.name,
              price: item.price,
              disabled: true,
            });
          });
        });
      } else if (serviceOperationName === null) {
        const s = self.responceData.filter(
          (d) => d.operationNumber === self.tempServiceOperationName
        );
        s.forEach(function (item) {
          item.operations.forEach(function (value) {
            self.serviceWorks = self.serviceWorks.filter(
              (item) => item.name !== value.name
            );
          });
          item.details.forEach(function (value) {
            self.serviceParts = self.serviceParts.filter(
              (item) => item.model !== value.name
            );
          });
        });
      }
      this.tempServiceOperationName = serviceOperationName;
    },
    submit() {
      this.validate();
      this.saveServiceRecord();
    },
    clear() {
      this.serviceOperationName = null;
      this.mileage = null;
      this.date = null;
      this.serviceParts = [];
      this.serviceWorks = [];
      this.resetValidation();
    },
    async saveServiceRecord() {
      this.overlay = !this.overlay;
      const data = new NewRecord(
        this.serviceOperationName,
        this.date,
        this.mileage,
        this.serviceWorks,
        this.serviceParts
      );
      // todo сделать красиво
      await new Promise((resolve) => {
        setTimeout(resolve, 2000);
      });
      try {
        const res = await CarDiaryDataService.createServiceRecord(
          this.carId,
          data
        );
        this.overlay = false;
        this.message = `Record successfully created`;
        this.setSnackbarSuccess({
          show: true,
          message: "Service record successfully created!",
        });
        console.log(res);
        this.clear();
        await this.$router.push({
          name: "Car details",
          params: { carId: this.carId.toString() },
        });
        this.$router.go(1);
      } catch (error) {
        console.log(error.response);
        this.overlay = false;
        this.message = `${error.response.data.message}`;
        this.setSnackbarError({ show: true, message: error.response.data });
      }
    },
    editServiceOperationItem(item) {
      this.editedServiceOperationIndex = this.serviceWorks.indexOf(item);
      this.editedServiceOperationItem = Object.assign({}, item);
      this.dialogServiceOperation = true;
    },
    deleteServiceOperationItem(item) {
      this.editedServiceOperationIndex = this.serviceWorks.indexOf(item);
      this.editedServiceOperationItem = Object.assign({}, item);
      this.dialogServiceOperationDelete = true;
    },
    deleteServiceOperationItemConfirm() {
      this.serviceWorks.splice(this.editedServiceOperationIndex, 1);
      this.closeServiceOperationDelete();
    },
    closeServiceOperationDelete() {
      this.dialogServiceOperationDelete = false;
      this.$nextTick(() => {
        this.editedServiceOperationIndex = Object.assign(
          {},
          this.defaultServiceOperationItem
        );
        this.editedServiceOperationIndex = -1;
      });
    },
    closeServiceOperation() {
      this.dialogServiceOperation = false;
      this.$nextTick(() => {
        this.editedServiceOperationItem = Object.assign(
          {},
          this.defaultServiceOperationItem
        );
        this.editedIndex = -1;
        this.$refs.serviceOperationForm.reset();
        this.$refs.serviceOperationForm.resetValidation();
      });
    },
    saveServiceOperation() {
      this.$refs.serviceOperationForm.validate();
      if (this.editedServiceOperationIndex > -1) {
        Object.assign(
          this.serviceWorks[this.editedServiceOperationIndex],
          this.editedServiceOperationItem
        );
      } else {
        this.serviceWorks.push(this.editedServiceOperationItem);
      }
      this.closeServiceOperation();
    },
    editServicePartItem(item) {
      this.editedServicePartIndex = this.serviceParts.indexOf(item);
      this.editedServicePartItem = Object.assign({}, item);
      this.dialogServicePart = true;
    },
    deleteServicePartItem(item) {
      this.editedServicePartIndex = this.serviceParts.indexOf(item);
      this.editedServicePartItem = Object.assign({}, item);
      this.dialogServicePartDelete = true;
    },
    deleteServicePartnItemConfirm() {
      this.serviceParts.splice(this.editedServicePartIndex, 1);
      this.closeServicePartDelete();
    },
    closeServicePartDelete() {
      this.dialogServicePartDelete = false;
      this.$nextTick(() => {
        this.editedServicePartIndex = Object.assign(
          {},
          this.defaultServicePartItem
        );
        this.editedServicePartIndex = -1;
      });
    },
    closeServicePart() {
      this.dialogServicePart = false;
      this.$nextTick(() => {
        this.editedServicePartItem = Object.assign(
          {},
          this.defaultServicePartItem
        );
        this.editedIndex = -1;
        this.$refs.servicePartItemForm.reset();
        this.$refs.servicePartItemForm.resetValidation();
      });
    },
    saveServicePart() {
      this.$refs.servicePartItemForm.validate();
      if (this.editedServicePartIndex > -1) {
        Object.assign(
          this.serviceParts[this.editedServicePartIndex],
          this.editedServicePartItem
        );
      } else {
        this.serviceParts.push(this.editedServicePartItem);
      }
      this.closeServicePart();
    },
    validate() {
      this.$refs.observer.validate();
    },
    resetValidation() {
      this.$nextTick(() => {
        this.$refs.observer.reset();
      });
    },
  },
};
</script>
