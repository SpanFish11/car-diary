<template>
  <router-view :key="$route.fullPath" />
</template>

<script>
export default {
  name: "App",
  metaInfo: {
    title: "Car Diary",
    titleTemplate: "%s | Car Diary",
  },
};
</script>
