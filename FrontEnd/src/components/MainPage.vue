<template>
  <div class="hero">
    <div class="hero-slide">
      <div class="img overlay" style="background-image: url('images/trip_bg_image_1.jpg')"></div>
      <div class="img overlay" style="background-image: url('images/trip_bg_image_2.jpg')"></div>
      <div class="img overlay" style="background-image: url('images/trip_bg_image_3.jpg')"></div>
    </div>

    <div class="container">
      <div class="row justify-content-center align-items-center">
        <div class="col-lg-9 text-center">
          <h1 class="heading" data-aos="fade-up">Easiest way to find your Trip</h1>
          <router-link to="/trip" class="btn btn-primary" data-aos="fade-up" data-aos-delay="200">여행지 검색</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    let script = document.createElement("script");
    script.setAttribute("src", "/js/custom.js");
    document.head.appendChild(script);
  },
};
</script>

<style></style>
