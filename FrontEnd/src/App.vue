<template>
  <div id="app">
    <router-view v-bind:isLogin="isLogin" v-on:call-parent-logout="logout" v-on:call-parent-refresh="refresh" name="NavBar"></router-view>
    <router-view v-bind:userInfo="userInfo" v-on:call-parent-loginSuccess="loginSuccess" v-on:call-parent-withdrawSuccess="withdrawSuccess"></router-view>
    <router-view name="Footer"></router-view>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogin: false,
      userInfo: {},
    };
  },
  methods: {
    // 자식에서 보내온 신호를 통해 해당 메소드가 호출됨
    loginSuccess(userInfo) {
      this.$alertify.success("로그인되었습니다.");
      this.isLogin = true;
      this.userInfo = userInfo;
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
    },

    logout() {
      console.log("로그아웃 메서드 호출");
      this.isLogin = false;
      this.userInfo = {};
      localStorage.removeItem("userInfo");
    },

    withdrawSuccess() {
      console.log("회원탈퇴 메서드 호출");
      this.logout();
    },

    refresh() {
      console.log("새로고침 메서드 호출");
      this.isLogin = true;
    },
  },
};
</script>

<style></style>
