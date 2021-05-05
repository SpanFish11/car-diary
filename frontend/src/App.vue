<template>
  <div id="app">
    <NavBar />
    <div>
      <b-jumbotron
        id="jumbo"
        header="THIS IS CAR DIARY"
        class="text-center container-fluid"
      >
        <template #lead> The unique and free car management system </template>
      </b-jumbotron>
    </div>
    <b-overlay :show="show" rounded="sm">
      <router-view :key="$route.fullPath" />
    </b-overlay>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar";

export default {
  name: "App",
  components: {
    NavBar,
  },
  metaInfo: {
    title: "Hello",
    titleTemplate: "%s | Car Diary",
  },
  data: () => ({
    show: false,
  }),
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#jumbo {
  color: white;
  text-shadow: 1px 1px 2px black, 0 0 1em black;
  background-image: url("https://i.ibb.co/0B6VGBj/wallhaven-5w33q3ff-1.jpg");
  -webkit-background-size: cover;
}
</style>
