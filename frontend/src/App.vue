<template>
  <div id="app">
    <NavBar/>
    <AddNewCarModal/>
    <Main/>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar'
import AddNewCarModal from "@/components/AddNewCarModal"
import Main from "@/components/Main"

export default {
  name: 'App',
  components: {
    NavBar,
    AddNewCarModal,
    Main
  },
  metaInfo: {
    title: 'Car Page'
  }
}
</script>

<style>
</style>
