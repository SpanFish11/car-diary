<template>
  <main>
    <div class="album bg-light">
      <div class="container">
        <b-card-group columns>

          <b-card title="Title" lazy
                  img-src="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                  img-alt="Image" img-top>
            <b-card-text>
            </b-card-text>
            <b-button-toolbar>
              <b-button-group size="sm">
                <b-button variant="outline-secondary">Details</b-button>
                <b-button variant="outline-secondary">Edit</b-button>
              </b-button-group>
              <b-button-group size="sm" right class="mx-3">
                <b-button variant="outline-secondary">записаться</b-button>
              </b-button-group>
            </b-button-toolbar>
            <template #footer>
              <small class="text-muted">100000 km</small>
            </template>
          </b-card>

          <b-card title="Title"
                  img-src="https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg"
                  img-alt="Image" lazy img-top>
            <b-card-text>
              This card has supporting text below as a natural lead-in to additional content.
            </b-card-text>
            <b-button-toolbar>
              <b-button-group size="sm">
                <b-button variant="outline-secondary">Details</b-button>
                <b-button variant="outline-secondary">Edit</b-button>
              </b-button-group>
              <b-button-group size="sm" right class="mx-3">
                <b-button variant="outline-secondary">записаться</b-button>
              </b-button-group>
            </b-button-toolbar>
            <template #footer>
              <small class="text-muted">100000 km</small>
            </template>
          </b-card>

          <b-card title="Title"
                  img-src="https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg"
                  img-alt="Image" lazy img-top>
            <b-card-text>
              This is a wider card with supporting text below as a natural lead-in to additional content.
              This card has even longer content than the first to show that equal height action.
            </b-card-text>
            <b-button-toolbar>
              <b-button-group size="sm">
                <b-button variant="outline-secondary">Details</b-button>
                <b-button variant="outline-secondary">Edit</b-button>
              </b-button-group>
              <b-button-group size="sm" right class="mx-3">
                <b-button variant="outline-secondary">записаться</b-button>
              </b-button-group>
            </b-button-toolbar>
            <template #footer>
              <small class="text-muted">100000 km</small>
            </template>
          </b-card>

        </b-card-group>
      </div>
    </div>
  </main>
</template>

<script>
import {AXIOS} from "@/backend-api"

export default {
  name: "Main",
  mounted() {
    this.loadAllCars()
  },
  methods: {
    loadAllCars() {
      AXIOS.get('cars').then(response => {
        this.cars = response.data;
      }).catch(error => {
        console.log('ERROR: ' + error.response.data)
      })
    }
  }
}
</script>

<style scoped>
</style>