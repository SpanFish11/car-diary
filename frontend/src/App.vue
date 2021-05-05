<template>
  <v-app>
    <NavBar />

    <v-main>
      <v-container fluid>
        <router-view />
      </v-container>
    </v-main>

    <Footer />
  </v-app>
</template>

<script>
import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";

export default {
  name: "App",
  components: {
    NavBar,
    Footer,
  },
  metaInfo: {
    title: "Hello",
    titleTemplate: "%s | Car Diary",
  },
  data: () => ({}),
};
</script>
