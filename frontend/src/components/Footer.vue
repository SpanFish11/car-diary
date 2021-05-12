<template>
  <v-footer dark>
    <v-container>
      <v-row flat align="center" no-gutters>
        <v-spacer class="hidden-sm-and-down" />
        <v-col cols="12" md="auto">
          <div class="body-1 font-weight-light pt-6 pt-md-0 text-center">
            &copy; {{ new Date().getFullYear() }}, made with
            <v-icon size="18" color="red"> mdi-heart </v-icon>
            by Java Mastery Team for a better web.
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script>
export default {
  data: () => ({}),
};
</script>

<style scoped></style>
