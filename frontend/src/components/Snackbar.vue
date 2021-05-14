<template>
  <div>
    <v-snackbar
      v-model="snackbarSuccess"
      color="success"
      :multi-line="true"
      :timeout="10000"
      top
    >
      {{snackbarSuccessMessage}}

      <template v-slot:action="{ attrs }">
        <v-icon v-bind="attrs" @click="setSnackbarSuccess(false)"
          >mdi-close-circle
        </v-icon>
      </template>
    </v-snackbar>

    <v-snackbar
      v-model="snackbarError"
      color="error"
      :multi-line="true"
      :timeout="10000"
      top
    >
      {{snackbarErrorMessage}}
      <template v-slot:action="{ attrs }">
        <v-icon v-bind="attrs" @click="setSnackbarError(false)"
          >mdi-close-circle
        </v-icon>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  name: "Snackbar",
  data: () => ({}),
  computed: {
    snackbarSuccess: {
      get() {
        return this.$store.state.snackbarSuccess;
      },
      set(val) {
        this.$store.commit("SET_SNACKBARSUCCESS", val);
      },
    },
    snackbarSuccessMessage: {
      get() {
        return this.$store.state.snackbarSuccessMessage;
      }
    },
    snackbarError: {
      get() {
        return this.$store.state.snackbarError;
      },
      set(val) {
        this.$store.commit("SET_SNACKBARERROR", val);
      },
    },
    snackbarErrorMessage: {
      get() {
        return this.$store.state.snackbarErrorMessage;
      }
    }
  },
  methods: {
    ...mapMutations({
      setSnackbarSuccess: "SET_SNACKBARSUCCESS",
      setSnackbarError: "SET_SNACKBARERROR",
    }),
  },
};
</script>
