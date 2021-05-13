<template>
  <v-main>
    <router-view :key="$route.fullPath"/>
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
