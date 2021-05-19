<template>
  <v-footer app class="justify-center pt-0" dark inset>
    <v-container>
      <v-row align="center" flat no-gutters>
        <v-spacer class="hidden-sm-and-down" />
        <v-col cols="12" md="auto">
          <div class="body-1 font-weight-light pt-6 pt-md-0 text-center">
            &copy; {{ new Date().getFullYear() }}, made with
            <v-icon color="red" size="18"> mdi-heart</v-icon>
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
