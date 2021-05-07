<template>
  <v-container style="text-align: center" fluid class="NotFound">
    <v-layout row wrap>
      <v-flex xs12>
        <v-card class="mt-16" height="400" color="transparent" flat>
          <div class="text-h1">Page not found.</div>
          <div class="grey--text lighten-5">
            The page you are trying to get to never existed in this reality, or
            has migrated to a parallel universe.
          </div>
          <div class="paragraph-text mt-2">
            <br />
            <br />Try going back to home page and repeating your action. Or,
            contact helpdesk for support.
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.paragraph-text {
  font-size: 18px;
}
</style>
