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
              <form>
                <div class="form-floating mb-3 text-start">
                  <input type="text" class="form-control" placeholder="VIN number" id="VIN-number">
                  <label for="VIN-number">VIN number</label>
                </div>
                <div class="mb-3 text-start">
                  <label class="col-form-label">Brand:</label>
                  <select class="form-select" aria-label="Default select example">
                    <option selected>Choose...</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                  </select>
                </div>
                <div class="mb-3 text-start">
                  <label for="model" class="col-form-label">Model:</label>
                  <input type="text" class="form-control" id="model">
                </div>
                <div class="mb-3 text-start">
                  <label for="manufacture-year" class="col-form-label">Manufacture year:</label>
                  <input type="text" class="form-control" id="manufacture-year">
                </div>
                <div class="form-check mb-3 form-switch text-start">
                  <input class="form-check-input" type="checkbox" id="flexSwitchCheckChecked" checked>
                  <label class="form-check-label" for="flexSwitchCheckChecked">New car</label>
                </div>
                <div class="input-group mb-3 text-start">
                  <span class="input-group-text">Mileage:</span>
                  <input type="text" placeholder="20000" class="form-control" id="mileage">
                  <span class="input-group-text">km</span>
                </div>
                <div class="input-group mb-3">
                  <input type="file" class="form-control" id="inputGroupFile02">
                  <label class="input-group-text" for="inputGroupFile02">Upload</label>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
          </div>
        </div>
      </div>

    </section>


    <div class="album bg-light">
      <div class="container">

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
                </div>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
                </div>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
                </div>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
                </div>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
                </div>
              </div>
            </div>
          </div>

          <div class="col">
            <div class="card shadow-sm">
              <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg"
                   role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice"
                   focusable="false"><title>Car image</title>
                <rect width="100%" height="100%" fill="#55595c"/>
                <image
                    href="https://i.pinimg.com/originals/ee/ae/23/eeae23049a5afa038b8405e864a1ad52.png"
                    height="100%" width="100%"/>
              </svg>
              <div class="card-body">
                <h5 class="card-title">Brand model, manufacture year</h5>
                <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional
                  content. This content is a little bit longer.</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="button" class="btn btn-sm btn-outline-secondary">Details</button>
                    <button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
                  </div>
                  <small class="text-muted">mileage km</small>
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
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
