<template>
  <v-app-bar
    absolute
    app
    color="transparent"
    dark
    flat
    shrink-on-scroll
    src="@/assets/Logo.jpg"
  >
    <v-app-bar-nav-icon @click="setDrawer(!drawer)"></v-app-bar-nav-icon>

    <v-spacer></v-spacer>

    <v-switch
      v-model="$vuetify.theme.dark"
      class="mt-2"
      color="primary"
      hide-details
    >
      <template v-slot:label> Dark Mode</template>
    </v-switch>

    <v-menu
      bottom
      left
      offset-y
      origin="top right"
      transition="scale-transition"
    >
      <template v-slot:activator="{ attrs, on }">
        <v-btn class="ml-2" min-width="0" text v-bind="attrs" v-on="on">
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>

      <v-list :tile="false" nav dense>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title @click="logout"> Log Out </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  name: "AppBar",
  data: () => ({}),
  props: {
    value: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    ...mapState(["drawer"]),
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout", this.user).then(() => {
        this.$router.push("/login");
      });
    },
    ...mapMutations({
      setDrawer: "SET_DRAWER",
    }),
  },
  watch: {
    color(val) {
      this.$vuetify.theme.themes[this.isDark ? "dark" : "light"].primary = val;
    },
  },
};
</script>
