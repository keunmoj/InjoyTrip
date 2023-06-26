<template>
  <div>
    <div class="hero page-inner overlay" style="background-image: url('images/trip_bg_image_2.jpg')">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">사용자 정보</h1>
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
          <!-- <div class="row">
            <div class="col-lg-4 mb-lg-0" data-aos="fade-up" data-aos-delay="100">
              <div class="d-flex justify-content-center">
                <img src="images/person_2-min.jpg" alt="Image" class="img-fluid rounded-circle w-50 mb-4" />
              </div>
              <div class="justify-content-center">
                <label for="formFile" class="form-label">사용자 프로필 변경</label>
                <input class="form-control" type="file" id="formFile" />
              </div>
              <h3 class="h5 text-primary mb-4 text-center">Mike Houston</h3>
            </div>

            <div class="col-lg-8 mt-3" data-aos="fade-up" data-aos-delay="200">
              <div class="row">
                <div class="col-12 mx-auto card">
                  here is user info here is user info here is user info here is user info here is user info here is user info here is user info here is user
                  info here is user info here is user info here is user info here is user info here is user info here is user info here is user info here is
                  user info here is user info here is user info here is user info here is user info here is user info here is user info here is user info here
                  is user info here is user info here is user info here is user info here is user info here is user info here is user info here is user info
                  here is user info here is user info here is user infohere is user info here is user info here is user info here is user info here is user info
                  here is user info here is user info here is user info here is user info here is user info here is user info here is user info here is user
                  info here is user info here is user info here is user info here is user info
                </div>
              </div>
            </div>
          </div> -->
          <div class="mb-3">
            <label for="emailid" class="form-label">*이메일</label>
            <div class="input-group">
              <input type="text" class="form-control" id="userEmail" name="userEmail" :value="userInfo.userEmail" placeholder="" disabled />
            </div>
          </div>
          <div class="mb-3">
            <label for="username" class="form-label">*사용자 이름</label>
            <input type="text" class="form-control" id="userName" name="userName" :value="userInfo.userName" disabled />
          </div>

          <div class="mb-3">
            <label for="userid" class="form-label">*사용자 닉네임</label>
            <input type="text" class="form-control" id="userid" name="userId" v-model="userCurrentNickname" disabled />
          </div>

          <div class="mb-3">
            <label for="userid" class="form-label">사용자 닉네임 변경</label>
            <input type="text" v-model="userNewNickname" class="form-control" id="userid" name="userId" placeholder="새로운 사용자 닉네임 입력.." />
          </div>

          <!-- 현재 세션에는 사용자의 이메일이 들어있지 않음. -->

          <div class="" align="right">
            <a @click="modify" class="btn btn-success mb-3" style="margin-right: 10px">수정</a>
            <a @click="withdraw" class="btn btn-danger mb-3" style="margin-right: 10px">회원 탈퇴</a>
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
      userNewNickname: "",
      userPassword: "",
      userCurrentNickname: "",
    };
  },

  methods: {
    async modify() {
      // axios 비동기 통신하는 부분
      try {
        // json 형식으로 날라간다.!!!!
        let response = await http.put("/users/info", {
          userSeq: this.userInfo.userSeq,
          userNewNickname: this.userNewNickname,
        });

        let { data } = response; // destructuring

        console.log(data);

        if (data.result == "success") {
          const userInfoString = localStorage.getItem("userInfo");
          const userInfo = JSON.parse(userInfoString);
          userInfo.userNickname = this.userNewNickname;
          localStorage.setItem("userInfo", JSON.stringify(userInfo));
          this.userCurrentNickname = this.userNewNickname;
          this.userNewNickname = "";
          this.$alertify.success("개인정보 수정이 완료되었습니다.");
          this.$router.go("/login");
        } else if (data.result == "fail") {
          this.$alertify.error("개인정보 수정에 실패했습니다.");
        }
      } catch (error) {
        console.error(error);
        this.$alertify.error("개인정보 수정 과정에서 오류가 발생했습니다.");
      }
    },

    async withdraw() {
      if (!confirm("정말 회원탈퇴를 진행하겠습니까?")) {
        // 취소(아니오) 버튼 클릭 시 이벤트
        alert("취소 하셨습니다.");
      } else {
        // 확인(예) 버튼 클릭 시 이벤트
        try {
          // json 형식으로 날라간다.!!!!
          let response = await http.delete("/users/" + this.userInfo.userEmail);

          let { data } = response; // destructuring

          console.log(data);

          if (data.result == "success") {
            this.$emit("call-parent-withdrawSuccess");
            // 로그인 성공후 board 이동
            this.$alertify.success("회원탈퇴 성공");
            this.$router.push("/");
          } else if (data.result == "fail") {
            this.$alertify.error("회원탈퇴 실패!");
          }
        } catch (error) {
          console.error(error);
          this.$alertify.error("회원탈퇴 과정에서 오류가 발생했습니다.");
        }
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
    this.userCurrentImageUrl = this.userInfo.userProfileImageUrl;
  },
};
</script>

<style scoped></style>
