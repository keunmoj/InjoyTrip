<template>
  <div>
    <div class="hero page-inner overlay" style="background-image: url('images/trip_bg_image_2.jpg')">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">비밀번호 찾기</h1>
          </div>
        </div>
      </div>
    </div>

    <div class="section section-properties pb-1">
      <div class="container">
        <div class="row justify-content-center mb-4" align="center">
          <router-link to="/userinfo" class="col btn btn-primary" style="margin-right: 10px"> 내 정보 </router-link>
          <router-link to="/mytrip" class="col btn btn-primary" style="margin-right: 10px"> 내 여행 </router-link>
          <router-link to="/userpwdupdate" class="col btn btn-primary" style="margin-right: 10px"> 비밀번호 변경 </router-link>
        </div>
        <form id="form-modify" method="POST" action="">
          <div class="mb-3">
            <label for="emailid" class="form-label">기존 비밀번호</label>
            <div class="input-group">
              <input type="password" v-model="userPassword" class="form-control" id="userPassword" name="userPassword" placeholder="기존 비밀번호.." />
            </div>
          </div>
          <div class="mb-3">
            <label for="emailid" class="form-label">새 비밀번호</label>
            <div class="input-group">
              <input type="password" v-model="userNewPassword1" class="form-control" id="newPassword1" name="newPassword1" placeholder="새 비밀번호 입력.." />
            </div>
          </div>
          <div class="mb-3">
            <label for="emailid" class="form-label">새 비밀번호 확인</label>
            <div class="input-group">
              <input type="password" v-model="userNewPassword2" class="form-control" id="newPassword2" name="newPassword2" placeholder="새 비밀번호 확인.." />
            </div>
          </div>
          <div class="" align="right">
            <a @click="pwdUpdate" class="btn btn-success mb-3" style="margin-right: 10px">변경</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import http from "@/common/axios.js";
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);
export default {
  data() {
    return {
      userInfo: {},
      userPassword: "",
      userNewPassword1: "",
      userNewPassword2: "",
    };
  },

  methods: {
    async pwdUpdate() {
      try {
        // json 형식으로 날라간다.!!!!
        const params = {
          userEmail: this.userInfo.userEmail,
          userPassword: this.userPassword,
          userNewPassword: this.userNewPassword1,
        };
        let response = await http.put("/users/password", params);

        let { data } = response; // destructuring

        console.log(data);

        if (data.result == "success") {
          this.$alertify.success("비밀번호가 변경되었습니다.");
          this.$router.push("/");
        } else if (data.result == "fail") {
          this.$alertify.error("비밀번호가 일치하지 않습니다.");
        }
      } catch (error) {
        console.error(error);
        this.$alertify.error("비밀번호 변경 과정에서 오류가 발생했습니다.");
      }
    },
  },

  created() {
    let script = document.createElement("script");
    script.setAttribute("src", "/js/custom.js");
    document.head.appendChild(script);
  },

  mounted() {
    const userInfoJson = localStorage.getItem("userInfo");
    this.userInfo = JSON.parse(userInfoJson);
  },
};
</script>

<style scoped></style>
