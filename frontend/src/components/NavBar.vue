<template>
  <v-app-bar
    app
    absolute
    color="#fcb69f"
    dark
    shrink-on-scroll
    src="@/assets/Logo.jpg"
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
      <v-tabs align-with-title>
        <router-link to="/" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Main</v-tab
          >
        </router-link>

        <router-link to="/login" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Login</v-tab
          >
        </router-link>

        <v-tab @click="logOut()">Sign out</v-tab>

        <router-link to="/client" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >client</v-tab
          >
        </router-link>

        <router-link to="/manager" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Manager</v-tab
          >
        </router-link>

        <router-link to="/manager/client" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Manager client</v-tab
          >
        </router-link>

        <router-link to="/manager/soldcar" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Manager Sold Car</v-tab
          >
        </router-link>

        <router-link to="/service" custom v-slot="{ navigate }">
          <v-tab @click="navigate" @keypress.enter="navigate" role="link"
            >Service Record</v-tab
          >
        </router-link>
      </v-tabs>
    </template>
  </v-app-bar>
</template>

<script>
export default {
  name: "NavBar",
  data: () => ({}),
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.go(0);
      this.$router.push("/");
    },
  },
};
</script>

<style scoped></style>
