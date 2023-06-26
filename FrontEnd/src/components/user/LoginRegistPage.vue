<template>
  <div>
    <div class="hero page-inner overlay" style="background-image: url('images/trip_bg_image_2.jpg')">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">로그인 & 회원가입</h1>
          </div>
        </div>
      </div>
    </div>
    <div class="container mb-5" style="padding-top: 3%">
      <div class="login-wrap" data-aos="fade-up">
        <div class="login-html mb-3 pt-5" >
          <input id="tab-1" type="radio" @click="init" name="tab" class="sign-in" checked /><label for="tab-1" class="tab">로그인</label>
          <input id="tab-2" type="radio" @click="init" name="tab" class="sign-up" /><label for="tab-2" class="tab">회원가입</label>
          <div class="login-form" >
            <div class="sign-in-htm">
              <div class="group">
                <label for="user" class="label">UserEmail</label>
                <input id="user" v-model="loginUserEmail" type="text" class="input" />
              </div>
              <div class="group">
                <label for="pass" class="label">Password</label>
                <input id="pass" v-model="loginUserPwd" type="password" class="input" data-type="password" />
              </div>
              
              <div class="group">
                <input @click="login" type="submit" class="btn btn-light" value="로그인" />
              </div>
              <div class="hr"></div>
              <div class="foot-lnk">
              </div>
            </div>
            <div class="sign-up-htm">
              <div class="group">
                <label for="pass" class="label">Email Address</label>
                <div class="row">
                  <div class="col">
                    <input id="userRegistEmail" v-model="registUserEmail" type="text" class="input" @change="validateEmail" />
                    <div class="badge badge-danger pt-1 m-0" style="color: red" v-if="registEmailInValid">이메일 형식이 다르거나 이미 존재하는 이메일입니다.</div>
                    <div class="badge badge-danger pt-1 m-0" style="color: blue" v-if="registEmailValid">사용 가능한 이메일입니다.</div>
                  </div>
                </div>
              </div>
              <div class="group">
                <label for="pass" class="label">Password</label>
                <input id="pass" v-model="registUserPwd" type="password" class="input" data-type="password" @change="validatePassword" />
                <span class="badge badge-danger pt-1 m-0" style="color: red" v-if="registPwdInValid">사용 불가능한 비밀번호 형식입니다.</span>
                <span class="badge badge-danger pt-1 m-0" style="color: blue" v-if="registPwdValid">사용 가능한 패스워드</span>
              </div>
              <div class="group">
                <label for="pass" class="label">Repeat Password</label>
                <input id="pass" v-model="registUserPwd2" type="password" class="input" data-type="password" />
              </div>
              <div class="group">
                <label for="user" class="label">Username</label>
                <input id="user" v-model="registUserName" type="text" class="input" @change="validateUserName" />
                <span class="badge badge-danger pt-1 m-0" style="color: red" v-if="registNameInValid">사용자 이름은 2자리 이상입니다.</span>
                <span class="badge badge-danger pt-1 m-0" style="color: blue" v-if="registNameValid">사용 가능한 이름입니다.</span>
              </div>
              <div class="group">
                <label for="user" class="label">Nickname</label>
                <div class="row">
                  <div class="col-8">
                    <input id="user" v-model="registUserNickname" type="text" class="input col-6" />
                    <span class="badge badge-danger pt-1 m-0" style="color: red" v-if="registNicknameInValid">이미 존재하는 닉네임입니다.</span>
                    <span class="badge badge-danger pt-1 m-0" style="color: blue" v-if="registNicknameValid">사용 가능한 닉네임입니다.</span>
                  </div>
                  <div class="col">
                    <button @click="duplicateNickname" class="btn btn-primary col">중복검사</button>
                  </div>
                </div>
              </div>

              <div class="group">
                <button @click="regist" class="btn btn-primary">회원가입</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// axios 통신을 위한 import
import http from "@/common/axios.js";
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);
export default {
  data() {
    return {
      registUserName: "",
      registUserPwd: "",
      registUserPwd2: "",
      registUserEmail: "",
      registUserNickname: "",

      // 유효성검사 코드
      registNameValid: false,
      registNameInValid: false,

      registPwdValid: false,
      registPwdInValid: false,

      registEmailValid: false,
      registEmailInValid: false,

      registNicknameValid: false,
      registNicknameInValid: false,

      loginUserEmail: "",
      loginUserPwd: "",
    };
  },

  methods: {
    async login() {
      // axios 비동기 통신하는 부분
      try {
        // json 형식으로 날라간다.!!!!
        let response = await http.post("/login", {
          userEmail: this.loginUserEmail,
          userPassword: this.loginUserPwd,
        }); // baseURL에 붙어서 간다.

        let { data } = response; // destructuring

        console.log(data);

        if (data.result == "success") {
          this.$emit("call-parent-loginSuccess", {
            userSeq: data.userSeq,
            userName: data.userName,
            userEmail: data.userEmail,
            userNickname: data.userNickname,
            userProfileImageUrl: data.userProfileImageUrl,
          });
          // 로그인 성공후 board 이동
          this.$router.push("/");
        } else if (data.result == "fail") {
          this.$alertify.error("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
      } catch (error) {
        console.error(error);
        this.$alertify.error("로그인 과정에서 오류가 발생했습니다.");
      }
    },
    async regist() {
      if (!this.registUserEmail) {
        alert("이메일은 필수 입력 사항입니다.");
      } else if (!this.registUserPwd) {
        alert("비밀번호를 입력해주세요");
      } else if (!this.registUserPwd2) {
        alert("비밀번호 확인을 입력해주세요");
      } else if (!this.registUserNickname) {
        alert("닉네임을 입력해주세요");
      } else if (this.registUserPwd != this.registUserPwd2) {
        alert("비밀번호를 확인해주세요");
      } else if (!this.registEmailValid) {
        alert("이메일이 유효하지 않습니다.");
      } else if (!this.registNameValid) {
        alert("이름이 유효하지 않습니다.");
      } else if (!this.registNicknameValid) {
        alert("닉네임이 유효하지 않습니다.");
      } else {
        try {
          // json 형식으로 날라간다.!!!!
          console.log(this.registUserName);

          let response = await http.post("/users", {
            userName: this.registUserName,
            userEmail: this.registUserEmail,
            userPassword: this.registUserPwd,
            userNickname: this.registUserNickname,
          }); // baseURL에 붙어서 간다.

          let { data } = response; // destructuring

          console.log(data);

          if (data.result == "success") {
            console.log("쉽게 나와선 안되는 문장");
            this.$alertify.success("회원가입 성공");
            this.$router.push("/");
          } else if (data.result == "fail") {
            this.$alertify.error("이메일 또는 비밀번호가 올바르지 않습니다.");
          }
        } catch (error) {
          console.error(error);
          this.$alertify.error("로그인 과정에서 오류가 발생했습니다.");
        }
      }
    },
    // 폼 내용 초기화
    init() {
      console.log("call init");
      this.loginUserEmail = "";
      this.loginUserPwd = "";
      this.registUserName = "";
      this.registUserPwd = "";
      this.registUserPwd2 = "";
      this.registUserEmail = "";
      this.registUserNickname = "";

      this.registNameValid = false;
      this.registNameInValid = false;

      this.registPwdValid = false;
      this.registPwdInValid = false;

      this.registEmailValid = false;
      this.registEmailInValid = false;

      this.registNicknameValid = false;
      this.registNicknameInValid = false;
    },

    checkEmail() {
      // if(validateEmail(this.registUserEmail)){
      // }else{
      // }
    },

    async duplicateEmail() {
      try {
        let response = await http.get("/valid/email/" + this.registUserEmail);
        let { data } = response; // destructuring

        console.log("dupEmail", data);

        if (data.result == "notdup") {
          this.$alertify.success("사용가능한 이메일입니다.");
          this.registEmailValid = true;
          this.registEmailInValid = false;
        } else if (data.result == "dup") {
          this.registEmailValid = false;
          this.registEmailInValid = true;
          this.$alertify.error("사용할 수 없는 이메일입니다.");
        }
      } catch (error) {
        console.error(error);
        this.$alertify.error("로그인 과정에서 오류가 발생했습니다.");
      }
    },
    async duplicateNickname() {
      try {
        let response = await http.get("/valid/nickname/" + this.registUserNickname);
        let { data } = response; // destructuring

        console.log(data);

        if (data.result == "notdup") {
          this.$alertify.success("사용가능한 닉네임입니다.");
          this.registNicknameValid = true;
          this.registNicknameInValid = false;
        } else if (data.result == "dup") {
          this.$alertify.error("사용할 수 없는 닉네임입니다.");
          this.registNicknameValid = false;
          this.registNicknameInValid = true;
        }
      } catch (error) {
        console.error(error);
        this.$alertify.error("로그인 과정에서 오류가 발생했습니다.");
      }
    },

    async validateEmail() {
      console.log("call-@change");
      let regexp = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i);
      if (regexp.test(this.registUserEmail)) {
        this.duplicateEmail();
      } else {
        this.registEmailValid = false;
        this.registEmailInValid = true;
      }
    },

    validateUserName() {
      if (this.registUserName.length >= 2) {
        this.registNameValid = true;
        this.registNameInValid = false;
      } else {
        this.registNameValid = false;
        this.registNameInValid = true;
      }
    },

    validatePassword() {
      var patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
      var patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
      var patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

      if (patternEngAtListOne.test(this.registUserPwd) && patternSpeAtListOne.test(this.registUserPwd) && patternNumAtListOne.test(this.registUserPwd)) {
        this.registPwdValid = true;
        this.registPwdInValid = false;
      } else {
        this.registPwdValid = false;
        this.registPwdInValid = true;
      }
    },
  },

  created() {
    let script = document.createElement("script");
    script.setAttribute("src", "/js/custom.js");
    document.head.appendChild(script);
  },
};
</script>

<style lang="less" scoped>
body {
  margin: 0;
  color: #6a6f8c;
  background: #005555; // 두개 중 하나 선택
  background: url("@/assets/img/test2.jpg");
  background-size: cover;
  font: 600 16px/18px "Open Sans", sans-serif;
}
*,
:after,
:before {
  box-sizing: border-box;
}
.clearfix:after,
.clearfix:before {
  content: "";
  display: table;
}
.clearfix:after {
  clear: both;
  display: block;
}
a {
  color: white;
  text-decoration: none;
}

.login-wrap {
  width: 100%;
  margin: auto;
  max-width: 600px;
  min-height: 700px;
  position: relative;
  box-shadow: 0 12px 15px 0 rgba(0, 0, 0, 0.24), 0 17px 50px 0 rgba(0, 0, 0, 0.19);
}
.login-html {
  width: 100%;
  height: 100%;
  position: absolute;
  padding: 90px 70px 50px 70px;
  background: #005555c0;
}
.login-html .sign-in-htm{
  top: 15%;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  transform: rotateY(180deg);
  backface-visibility: hidden;
  transition: all 0.4s linear;
}
.login-html .sign-up-htm {
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  position: absolute;
  transform: rotateY(180deg);
  backface-visibility: hidden;
  transition: all 0.4s linear;
}
.login-html .sign-in,
.login-html .sign-up,
.login-form .group .check {
  display: none;
}
.login-html .tab,
.login-form .group .label,
.login-form .group .button {
  text-transform: uppercase;
}
.login-html .tab {
  font-size: 22px;
  margin-right: 15px;
  padding-bottom: 5px;
  margin: 0 15px 10px 0;
  display: inline-block;
  border-bottom: 2px solid transparent;
}
.login-html .sign-in:checked + .tab,
.login-html .sign-up:checked + .tab {
  color: #fff;
  border-color: #1161ee;
}
.login-form {
  min-height: 345px;
  position: relative;
  perspective: 1000px;
  transform-style: preserve-3d;
}
.login-form .group {
  margin-bottom: 15px;
}
.login-form .group .label,
.login-form .group .input,
.login-form .group .button {
  width: 100%;
  color: #fff;
  display: block;
}
.login-form .group .input,
.login-form .group .button {
  border: none;
  padding: 15px 20px;
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.1);
}
.login-form .group input[data-type="password"] {
  text-security: circle;
  -webkit-text-security: circle;
}
.login-form .group .label {
  color: #aaa;
  font-size: 12px;
}
.login-form .group .button {
  background: #1161ee;
}
.login-form .group label .icon {
  width: 15px;
  height: 15px;
  border-radius: 2px;
  position: relative;
  display: inline-block;
  background: rgba(255, 255, 255, 0.1);
}
.login-form .group label .icon:before,
.login-form .group label .icon:after {
  content: "";
  width: 10px;
  height: 2px;
  background: #fff;
  position: absolute;
  transition: all 0.2s ease-in-out 0s;
}
.login-form .group label .icon:before {
  left: 3px;
  width: 5px;
  bottom: 6px;
  transform: scale(0) rotate(0);
}
.login-form .group label .icon:after {
  top: 6px;
  right: 0;
  transform: scale(0) rotate(0);
}
.login-form .group .check:checked + label {
  color: #fff;
}
.login-form .group .check:checked + label .icon {
  background: #1161ee;
}
.login-form .group .check:checked + label .icon:before {
  transform: scale(1) rotate(45deg);
}
.login-form .group .check:checked + label .icon:after {
  transform: scale(1) rotate(-45deg);
}
.login-html .sign-in:checked + .tab + .sign-up + .tab + .login-form .sign-in-htm {
  transform: rotate(0);
}
.login-html .sign-up:checked + .tab + .login-form .sign-up-htm {
  transform: rotate(0);
}

.hr {
  height: 2px;
  margin: 60px 0 50px 0;
  background: rgba(255, 255, 255, 0.2);
}
.foot-lnk {
  text-align: center;
}
</style>
