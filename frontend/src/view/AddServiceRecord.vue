<template>
  <validation-observer ref="observer" v-slot="{ invalid }">
    <v-container>
      <v-card elevation="24">
        <v-card-title>Add new dervice record</v-card-title>

        <v-container fluid>
          <form @submit.prevent="submit">
            <v-row align="center">
              <v-col cols="12">
                <v-autocomplete
                  clearable
                  v-model="serviceOperationName"
                  :items="items"
                  dense
                  filled
                  label="Maintenance"
                  hint="Select maintenance if it is, otherwise, leave it blank"
                  persistent-hint
                ></v-autocomplete>
              </v-col>

              <v-col cols="12">
                <validation-provider
                  v-slot="{ errors }"
                  name="Mileage"
                  :rules="{ required: true, numeric: true, max: 7 }"
                >
                  <v-text-field
                    min="0"
                    :counter="7"
                    v-model="mileage"
                    label="Mileage"
                    type="number"
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

                          <validation-observer
                            ref="observer"
                            v-slot="{ invalid }"
                          >
                            <form @submit.prevent="saveServiceOperation">
                              <v-card-text>
                                <v-container>
                                  <v-row align="center">
                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Guarantee"
                                        :rules="{ required: true }"
                                      >
                                        <v-select
                                          v-model="
                                            editedServiceOperationItem.guarantee
                                          "
                                          :items="serviceVariable"
                                          label="Guarantee"
                                          :error-messages="errors"
                                          clearable
                                          dense
                                        ></v-select>
                                      </validation-provider>
                                    </v-col>
                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Name"
                                        :rules="{ required: true }"
                                      >
                                        <v-text-field
                                          v-model="
                                            editedServiceOperationItem.name
                                          "
                                          label="Name"
                                          type="text"
                                          clearable
                                          :error-messages="errors"
                                          dense
                                        ></v-text-field>
                                      </validation-provider>
                                    </v-col>

                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Price"
                                        :rules="{
                                          required: true,
                                          numeric: true,
                                        }"
                                      >
                                        <v-text-field
                                          v-model="
                                            editedServiceOperationItem.price
                                          "
                                          dense
                                          clearable
                                          label="Price"
                                          :error-messages="errors"
                                        ></v-text-field>
                                      </validation-provider>
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
                                  :disabled="invalid"
                                  elevation="10"
                                  color="green darken-1"
                                  text
                                  @click="saveServiceOperation"
                                >
                                  Save
                                </v-btn>
                              </v-card-actions>
                            </form>
                          </validation-observer>
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
                      color="green"
                      class="mr-2"
                      @click="editServiceOperationItem(item)"
                    >
                      mdi-pencil
                    </v-icon>
                    <v-icon
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

                          <validation-observer
                            ref="observer"
                            v-slot="{ invalid }"
                          >
                            <form @submit.prevent="saveServicePart">
                              <v-card-text>
                                <v-container>
                                  <v-row align="center">
                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Replaced"
                                        :rules="{ required: true }"
                                      >
                                        <v-select
                                          v-model="
                                            editedServicePartItem.replaced
                                          "
                                          :items="serviceVariable"
                                          label="Replaced"
                                          clearable
                                          :error-messages="errors"
                                          dense
                                        ></v-select>
                                      </validation-provider>
                                    </v-col>
                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Model"
                                        :rules="{ required: true }"
                                      >
                                        <v-text-field
                                          v-model="editedServicePartItem.name"
                                          label="Model"
                                          type="text"
                                          :error-messages="errors"
                                          clearable
                                          dense
                                        ></v-text-field>
                                      </validation-provider>
                                    </v-col>

                                    <v-col cols="12">
                                      <validation-provider
                                        v-slot="{ errors }"
                                        name="Price"
                                        :rules="{
                                          required: true,
                                          numeric: true,
                                        }"
                                      >
                                        <v-text-field
                                          v-model="editedServicePartItem.price"
                                          dense
                                          :error-messages="errors"
                                          clearable
                                          label="Price"
                                        ></v-text-field>
                                      </validation-provider>
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
                                  :disabled="invalid"
                                  @click="saveServicePart"
                                >
                                  Save
                                </v-btn>
                              </v-card-actions>
                            </form>
                          </validation-observer>
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
                      color="green"
                      class="mr-2"
                      @click="editServicePartItem(item)"
                    >
                      mdi-pencil
                    </v-icon>
                    <v-icon color="red" @click="deleteServicePartItem(item)"
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
              <v-overlay :value="overlay">
                <v-progress-circular
                  indeterminate
                  size="64"
                ></v-progress-circular>
              </v-overlay>
            </v-container>
          </form>
        </v-container>
      </v-card>
    </v-container>
  </validation-observer>
</template>

<script>
import CarDiaryDataService from "@/services/CarDiaryDataService";

export default {
  name: "AddServiceRecord",
  metaInfo: {
    title: "Add Service Record",
    titleTemplate: "%s | Manager Application",
  },
  data: () => ({
    overlay: false,
    dialogServiceOperation: false,
    dialogServiceOperationDelete: false,
    dialogServicePart: false,
    dialogServicePartDelete: false,
    date: null,
    carId: 1,
    items: [
      { id: "1", text: "TO-0" },
      { id: "2", text: "TO-1 (промежуточное)" },
      { id: "3", text: "TO-2 (полное)" },
      { id: "4", text: "Доп. TO-1" },
      { id: "5", text: "Доп. TO-2" },
      { id: "6", text: "Доп. TO-3" },
    ],
    serviceOperationName: null,
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
  }),
  computed: {
    formServiceOperationTitle() {
      return this.editedServiceOperationIndex === -1 ? "New Item" : "Edit Item";
    },
    formServiceOperationPart() {
      return this.editedServicePartIndex === -1 ? "New Item" : "Edit Item";
    },
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
    submit() {
      this.validate();
      this.saveServiceRecord();
      // todo перекинуть ли обратно на машину
    },
    clear() {
      this.serviceOperationName = null;
      this.mileage = null;
      this.date = null;
      this.resetValidation();
    },
    async saveServiceRecord() {
      // todo overlay
      const data = {
        serviceOperationNumber: this.serviceOperationName,
        date: this.date,
        mileage: this.mileage,
        serviceWorks: this.serviceWorks,
        changableParts: this.serviceParts,
      };
      try {
        const res = await CarDiaryDataService.createServiceRecord(
          this.carId,
          data
        );
        const { status } = res.data;
        console.log(status);
      } catch (error) {
        console.log(error.response);
        // handle error
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
      });
    },
    saveServiceOperation() {
      this.validate();
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
      });
    },
    saveServicePart() {
      this.validate();
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
    // todo сбрасывается валидация после использоваания диалоговых окон !!!!!!
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

<style scoped></style>
