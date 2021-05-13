<template>
  <v-navigation-drawer
      app
      v-model="drawer"
      dark
      flat
      :expand-on-hover="expandOnHover"
      mobile-breakpoint="960"
      width="260">
    <v-divider class="mb-1"/>

    <v-list dense nav>
      <v-list-item>
        <v-list-item-avatar class="align-self-center" color="white" contain>
          <v-img
            src="https://demos.creative-tim.com/vuetify-material-dashboard/favicon.ico"
            max-height="30"
          />
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>Car Diary</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider class="mb-2"/>

    <v-list expand nav>
      <div />
      <v-list-item-group v-model="model">
        <v-list-item v-for="(item, i) in items" :key="i">
          <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <router-link :to="item.to" custom v-slot="{ navigate }">
              <v-list-item-title
                  @click="navigate"
                  @keypress.enter="navigate"
                  role="link"
                  v-text="item.title"
              >
              </v-list-item-title>
            </router-link>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
      <div/>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "Drawer",
  props: {
    expandOnHover: {
      type: Boolean,
      default: false,
    },
  },
  data: () => ({
    model: 0,
    items: [],
    user_role: [
      {
        title: "Home",
        icon: "mdi-home-city",
        to: "/client",
      },
    ],
    other: [
      {
        title: "Home",
        icon: "mdi-home-city",
        to: "/manager",
      },
      {
        title: "Add Service Record",
        icon: "mdi-clipboard-outline",
        to: "/manager/service/add",
      },
      {
        title: "Sold Car",
        icon: "mdi-car",
        to: "/manager/car/add",
      },
      {
        title: "Add New Client",
        icon: "mdi-account",
        to: "/manager/client/add",
      },
    ],
  }),
  computed: {
    drawer: {
      get() {
        return this.$store.state.drawer;
      },
      set(val) {
        this.$store.commit("SET_DRAWER", val);
      },
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  // TODO костыль
  methods: {
    resolveRole() {
      const { roles } = this.currentUser;
      if (roles.includes("user") && roles.length === 1) {
        this.items = this.user_role;
      } else {
        this.items = this.other;
      }
    },
  },
  mounted() {
    this.resolveRole();
  },
};
</script>
