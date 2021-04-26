<template>
  <header>
    <div class="collapse bg-dark" id="navbarHeader">
      <div class="container">
        <div class="row">
          <div class="col-sm-8 col-md-7 py-4">
            <h4 class="text-white">About</h4>
            <p class="text-muted">Add some information about the album below, the author, or any other background
              context. Make it a few sentences long so folks can pick up some informative tidbits. Then, link them off
              to some social networking sites or contact information.</p>
          </div>
        </div>
      </div>
    </div>
    <div class="navbar navbar-dark bg-dark shadow-sm">
      <div class="container">
        <a href="#" class="navbar-brand d-flex align-items-center">
          <strong>Some name</strong>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarHeader"
                aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
    </div>
  </header>

  <main>

    <section class="py-5 text-center container">
      <!-- Button trigger modal -->
      <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Add car
      </button>

      <!-- Modal -->
      <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Add new car</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form class="needs-validation" novalidate>

                <div class="form-floating mb-3 text-start">
                  <input type="text" class="form-control" placeholder="VIN number" id="VIN-number"
                         v-model="newCar.vin" maxlength="17" required>
                  <label for="VIN-number">VIN number</label>
                  <div class="invalid-feedback">
                    Please input a vin.
                  </div>
                </div>

                <div class="mb-3 text-start" id="brands">
                  <label class="col-form-label">Brand:</label>
                  <select class="form-select" aria-label="Default select example" v-model="selectedBrand"
                          @change="getModels()" required>
                    <option v-for="brand in brands" v-bind:key="brand.id">{{ brand.name }}</option>
                  </select>
                  <div class="invalid-feedback">
                    Please select a brand.
                  </div>
                </div>

                <div class="mb-3 text-start">
                  <label class="col-form-label">Model:</label>
                  <select class="form-select" aria-label="Default select example" v-model="selectedModel"
                          @change="setBrandAndModel" required>
                    <option v-for="model in models" v-bind:key="model.id">{{ model.name }}</option>
                  </select>
                  <div class="invalid-feedback">
                    Please select a model.
                  </div>
                </div>
                <div class="mb-3 text-start">
                  <label for="manufacture-year" class="col-form-label">Manufacture year:</label>
                  <input type="number" class="form-control" id="manufacture-year" v-model="newCar.year" min="1900"
                         max="2021" required>
                  <div class="invalid-feedback">
                    Please input a year(between 1900 and 2021).
                  </div>
                </div>
                <div class="input-group mb-3 text-start">
                  <span class="input-group-text">Mileage:</span>
                  <input type="text" placeholder="20000" class="form-control" id="mileage" v-model="newCar.mileage"
                         pattern="[0-9]*">
                  <span class="input-group-text">km</span>
                  <div class="valid-feedback">
                    For new car mileage can be 0.
                  </div>
                </div>

                <div class="input-group mb-3">
                  <input type="file" class="form-control" id="inputGroupFile02" ref="file">
                  <label class="input-group-text" for="inputGroupFile02">Upload</label>
                  <div class="valid-feedback text-start">
                    It isn`t necessary to add a photo of the car.
                  </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button id="addCar" type="submit" class="btn btn-primary" v-on:click="addNewCar()">Save changes
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>

    </section>


    <div class="album bg-light">
      <div class="container">

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

          <div class="col" v-for="car in cars" v-bind:key="car.id">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    v-bind:href="car.photoUrl"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">{{ car.brand.name }} {{ car.model.name }}, {{ car.year }}</h5>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">{{ car.mileage }} km</small>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </main>

  <footer class="text-muted py-5">
    <div class="container">
      <p class="float-end mb-1">
        <a href="#">Back to top</a>
      </p>
    </div>
  </footer>
</template>

<script>
import axios from "axios";

export default {
  name: 'HelloWorld',
  el: '#main',
  data() {
    return {
      brands: [],
      selectedBrand: null,
      models: [],
      selectedModel: null,
      cars: [],
      newCar: {
        'brandId': 0,
        'modelId': 0,
        'year': '',
        'vin': '',
        'mileage': 0
      }
    }
  }, mounted() {
    this.getCars();
    this.getBrands();
    (function () {
      let forms = document.querySelectorAll('.needs-validation')
      Array.prototype.slice.call(forms)
          .forEach(function (form) {
            form.addEventListener('submit', function (event) {
              if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
              }
              form.classList.add('was-validated');
            }, false)
          })
    })();
  },
  methods: {
    getBrands: function () {
      axios
          .get('/api/v1/brands')
          .then(response => (this.brands = response.data))
          .catch(error => console.error(error));
    },
    getModels: function () {
      const brandId = this.brands.find(x => x.name === this.selectedBrand).id;
      axios
          .get('/api/v1/brands/' + brandId + '/models')
          .then(response => (this.models = response.data))
          .catch(error => console.error(error));
    },
    getCars: function () {
      axios
          .get('/api/v1/cars')
          .then(response => (this.cars = response.data))
          .catch(error => console.error(error))
    },
    setBrandAndModel: function () {
      this.newCar.brandId = this.brands.find(x => x.name === this.selectedBrand).id;
      this.newCar.modelId = this.models.find(x => x.name === this.selectedModel).id;
    },
    uploadPhoto: function (carId) {
      const photo = this.$refs.file.files[0];
      if (photo) {
        let formData = new FormData();
        formData.append('photo', photo);
        axios.patch('/api/v1/cars/' + carId + '/photos', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(function () {
          console.log('SUCCESS');
        }).catch(error => console.error(error));
      }
    },
    addNewCar: function () {
      const self = this;
      if (this.newCar.brandId !== 0 && this.newCar.modelId !== 0 && this.newCar.vin.trim().length !== 0
          && !(this.newCar.year < 1900 || this.newCar.year > 2021)) {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/api/v1/cars', false);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function () {
          if (xhr.status === 201) {
            self.uploadPhoto(xhr.response);
          }
        }
        xhr.send(JSON.stringify(this.newCar));
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
