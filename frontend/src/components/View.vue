<template>
  <v-main>
    <router-view />

    <Footer />
  </v-main>
</template>

<script>
export default {
  name: "CarDiaryView",
  components: {
    Footer: () => import("./Footer"),
  },
};
</script>
