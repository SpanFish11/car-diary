<template>
  <v-parallax dark height="755" src="@/assets/Background.jpg">
    <v-row align="center" justify="center">
      <v-col class="text-center" cols="12">
        <div class="text-h1">Page not found.</div>
        <div>
          The page you are trying to get to never existed in this reality, or
          has migrated to a parallel universe.
        </div>
        <div class="paragraph-text mt-2">
          <br />
          <br />Try going back to home page and repeating your action. Or,
          contact helpdesk for support.
        </div>
      </v-col>
    </v-row>
  </v-parallax>
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
