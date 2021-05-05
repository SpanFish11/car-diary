<template>
  <v-app-bar
    app
    absolute
    color="#fcb69f"
    dark
    shrink-on-scroll
    src="https://i.ibb.co/0B6VGBj/wallhaven-5w33q3ff-1.jpg"
  >
    <v-app-bar-nav-icon></v-app-bar-nav-icon>

    <v-app-bar-title>THIS IS CAR DIARY</v-app-bar-title>

    <v-spacer></v-spacer>

    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-dots-vertical</v-icon>
    </v-btn>

    <template v-slot:extension>
      <v-tabs align-with-title> </v-tabs>
    </template>
  </v-app-bar>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped></style>
