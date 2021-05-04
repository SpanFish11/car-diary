<template>
  <header>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="/">CAR DIARY</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <router-link to="/client" tag="b-nav-item">Client Application</router-link>
          <b-nav-item-dropdown text="Manager Application" right>
            <router-link to="/manager" tag="b-dropdown-item">List of all cars</router-link>
            <router-link to="/manager/client" tag="b-dropdown-item">Add new client</router-link>
            <router-link to="/manager/soldcar" tag="b-dropdown-item">Sold car</router-link>
          </b-nav-item-dropdown>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>User</em>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </header>
</template>

<script>
export default {}
</script>

<style scoped>
</style>
