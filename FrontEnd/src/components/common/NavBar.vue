<template>
  <div>
    <div class="site-mobile-menu site-navbar-target">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close">
          <span class="icofont-close js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div>

    <nav class="site-nav">
      <div class="container">
        <div class="menu-bg-wrap">
          <div class="site-navigation">
            <router-link to="/" class="logo m-0 float-start">InjoyTrip</router-link>
            <ul class="js-clone-nav d-none d-lg-inline-block text-start site-menu float-end">
              <li class="active">
                <router-link to="/board">커뮤니티</router-link>
              </li>
              <li class="active">
                <router-link to="/trip">여행지 검색</router-link>
              </li>
              <li v-show="!isLogin" class="active">
                <router-link to="/login">로그인</router-link>
              </li>
              <!-- 로그아웃 로직 순서 : axios() -> 백에서 session invaildate -> response 보고 front 초기화 -> /으로 push -->
              <li v-show="isLogin" class="active">
                <a type="button" @click="logout">로그아웃</a>
              </li>
              <!-- isLogin=true일 때 보여진다-->
              <li v-show="isLogin" class="active">
                <router-link to="/mytrip" class="nav-link">마이페이지</router-link>
              </li>
            </ul>

            <a
              href="#"
              class="burger light me-auto float-end mt-1 site-menu-toggle js-menu-toggle d-inline-block d-lg-none"
              data-toggle="collapse"
              data-target="#main-navbar"
            >
              <span></span>
            </a>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import http from "@/common/axios";
export default {
  props: ["isLogin"],

  methods: {
    async logout() {
      let response = await http.get("/logout");
      let { data } = response;

      console.log(data);
      if (data.result == "success") {
        this.$emit("call-parent-logout");
        localStorage.removeItem("userInfo");
        this.$alertify.success("로그아웃되었습니다.");
        this.$router.push("/main");
      } else {
        this.$alertify.error("로그아웃에 문제가 발생했습니다.");
      }
    },
  },

  created() {
    let script = document.createElement("script");
    script.setAttribute("src", "/js/navbar.js");
    document.head.appendChild(script);

    const userInfoJson = localStorage.getItem("userInfo");
    if (JSON.parse(userInfoJson) != null) {
      this.$emit("call-parent-refresh");
    }
  },
};
</script>

<style></style>
