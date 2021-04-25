<template>
  <div id="app">
    <NavBar/>
    <Section/>
    <Main/>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'
import Main from "@/components/Main";
import Section from "@/components/Section";

export default {
  name: 'App',
  components: {
    NavBar,
    Section,
    Main
  },
  metaInfo: {
    title: 'Car Page'
  }
}
</script>

<style>
</style>
