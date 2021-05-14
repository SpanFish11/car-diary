<template>
  <v-app>
    <AppBar />
    <Drawer />
    <Snackbar />
    <CarDiaryView />
  </v-app>
</template>

<script>
export default {
  name: "CarDiaryIndex",
  metaInfo: {
    title: "Car Diary",
    titleTemplate: "%s | Car Diary",
  },
  components: {
    AppBar: () => import("../components/AppBar"),
    Drawer: () => import("../components/Drawer"),
    CarDiaryView: () => import("../components/View"),
    Snackbar: () => import("../components/Snackbar"),
  },
};
</script>
