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
                  clearable
                  v-model="serviceOperationName"
                  :items="maintenances"
                  dense
                  filled
                  label="Maintenance"
                  hint="Select maintenance if it is, otherwise, leave it blank"
                  persistent-hint
                  @change="someMethod(serviceOperationName)"
                ></v-autocomplete>
              </v-col>

              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  name="Mileage"
                  :rules="{
                    required: true,
                    numeric: true,
                    max: 7,
                    min_value: 0,
                  }"
                >
                  <v-text-field
                    :counter="7"
                    v-model="mileage"
                    label="Mileage"
                    :error-messages="errors"
                    filled
                    dense
                    clearable
                    placeholder="Type mileage"
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
                      name="Date"
                      :rules="{ required: true }"
                    >
                      <v-text-field
                        v-model="date"
                        clearable
                        filled
                        dense
                        :error-messages="errors"
                        label="Date"
                        placeholder="Choose a date"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </validation-provider>
                  </template>
                  <v-date-picker v-model="date" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn
                      elevation="10"
                      text
                      color="error"
                      @click="serviceOperationDate = false"
                    >
                      Cancel
                    </v-btn>
                    <v-btn
                      elevation="10"
                      text
                      color="success"
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
                  hide-default-footer
                  class="elevation-1"
                >
                  <template v-slot:top>
                    <v-toolbar flat dense>
                      <v-toolbar-title>Operations</v-toolbar-title>
                      <v-divider class="mx-4" inset vertical></v-divider>
                      <v-spacer></v-spacer>

                      <v-dialog
                        v-model="dialogServiceOperation"
                        max-width="500px"
                      >
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            elevation="10"
                            color="primary"
                            dark
                            small
                            class="mb-2"
                            v-bind="attrs"
                            v-on="on"
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
                                      label="Guarantee"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                    ></v-select>
                                  </v-col>
                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServiceOperationItem.name"
                                      label="Name"
                                      type="text"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                    ></v-text-field>
                                  </v-col>

                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServiceOperationItem.price"
                                      dense
                                      clearable
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                        (v) =>
                                          /^(?:0|[1-9][0-9]*)\.?[0-9]+$/.test(
                                            v
                                          ) || 'Only numbers',
                                      ]"
                                      label="Price"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>
                              </v-container>
                            </v-card-text>

                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                elevation="10"
                                color="red darken-1"
                                text
                                @click="closeServiceOperation"
                              >
                                Cancel
                              </v-btn>
                              <v-btn
                                elevation="10"
                                color="green darken-1"
                                text
                                :disabled="!serviceServiceOperationFormValid"
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
                              elevation="10"
                              color="red darken-1"
                              text
                              @click="closeServiceOperationDelete"
                              >Cancel
                            </v-btn>
                            <v-btn
                              elevation="10"
                              color="green darken-1"
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
                      disabled
                      dense
                    ></v-simple-checkbox>
                  </template>

                  <template v-slot:[`item.actions`]="{ item }">
                    <v-icon
                      :disabled="item.disabled === true"
                      color="green"
                      class="mr-2"
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
                  hide-default-footer
                  class="elevation-1"
                >
                  <template v-slot:top>
                    <v-toolbar flat dense>
                      <v-toolbar-title>Parts</v-toolbar-title>
                      <v-divider class="mx-4" inset vertical></v-divider>
                      <v-spacer></v-spacer>

                      <v-dialog v-model="dialogServicePart" max-width="500px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-btn
                            elevation="10"
                            color="primary"
                            dark
                            small
                            class="mb-2"
                            v-bind="attrs"
                            v-on="on"
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
                                      label="Replaced"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                    ></v-select>
                                  </v-col>
                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServicePartItem.model"
                                      label="Model"
                                      type="text"
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                      ]"
                                      clearable
                                      dense
                                    ></v-text-field>
                                  </v-col>

                                  <v-col cols="12">
                                    <v-text-field
                                      v-model="editedServicePartItem.price"
                                      dense
                                      clearable
                                      :rules="[
                                        (v) => v !== null || 'Item is required',
                                        (v) =>
                                          /^(?:0|[1-9][0-9]*)\.?[0-9]+$/.test(
                                            v
                                          ) || 'Only numbers',
                                      ]"
                                      label="Price"
                                    ></v-text-field>
                                  </v-col>
                                </v-row>
                              </v-container>
                            </v-card-text>

                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                elevation="10"
                                color="red darken-1"
                                text
                                @click="closeServicePart"
                              >
                                Cancel
                              </v-btn>
                              <v-btn
                                elevation="10"
                                color="green darken-1"
                                text
                                :disabled="!servicePartItemFormValid"
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
                              elevation="10"
                              color="red darken-1"
                              text
                              @click="closeServicePartDelete"
                              >Cancel
                            </v-btn>
                            <v-btn
                              elevation="10"
                              color="green darken-1"
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
                      disabled
                      dense
                    ></v-simple-checkbox>
                  </template>

                  <template v-slot:[`item.actions`]="{ item }">
                    <v-icon
                      :disabled="item.disabled === true"
                      color="green"
                      class="mr-2"
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
                  elevation="10"
                  class="mr-4 mt-5"
                  type="submit"
                  color="success"
                  :disabled="invalid"
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
        this.setSnackbarSuccess(!this.snackbarSuccess);
        console.log(res);
        this.clear();
        await this.$router.push({
          name: "Car details",
          params: { carId: this.carId.toString() },
        });
        this.$router.go(1);
      } catch (error) {
        // todo распарсить детаилс
        console.log(error.response);
        this.overlay = false;
        this.message = `${error.response.data.message}`;
        this.setSnackbarError(!this.snackbarError);
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
