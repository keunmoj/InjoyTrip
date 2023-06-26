<template>
  <div>
    <div class="hero page-inner overlay" style="background-image: url('images/trip_bg_image_2.jpg')">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">내 여행</h1>
          </div>
        </div>
      </div>
    </div>

    <div class="section section-properties pb-1">
      <div class="container">
        <div class="row justify-content-center mb-4" align="center">
          <router-link to="/userinfo" class="col btn btn-primary" style="margin-right: 10px"> 내 정보 </router-link>
          <router-link to="/mypage" class="col btn btn-primary" style="margin-right: 10px"> 내 여행 </router-link>
          <router-link to="/userpwdupdate" class="col btn btn-primary" style="margin-right: 10px"> 비밀번호 변경 </router-link>
        </div>
      </div>
    </div>

    <hr />
    <!-- start section -->
    <div class="section section-properties pt-0">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <div class="property-slider-wrap">
              <div class="property-slider">
                <!-- item start -->
                <div v-for="(card, index) in cardList" :key="index">
                  <div class="property-item" style="margin-top: 150px">
                    <!-- 사진, 버튼 클릭 시 상세 페이지로 이동 -->
                    <!-- <router-link to="#" class="img">
                      <img v-bind:src="requireImg" alt="Image" class="img-fluid card" />
                    </router-link> -->

                    <div class="property-content card">
                      <div class="price mb-2">
                        <span> {{ card.cardTitle }} </span>
                      </div>
                      <div>
                        <div>
                          <span class="d-block text-black-50"> {{ ListDate(card.cardCreatedate.date) }}</span>
                          <span class="city d-block mb-3">{{ card.cardComment }}</span>
                        </div>

                        <button @click="showCardDetailModal(card)" class="btn btn-primary py-2 px-3">See details</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- .item -->
              </div>

              <div id="property-nav" class="controls" tabindex="0" aria-label="Carousel Navigation">
                <span class="prev" data-controls="prev" aria-controls="property" tabindex="-1">Prev</span>
                <span class="next" data-controls="next" aria-controls="property" tabindex="-1">Next</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- end section -->

    <card-detail-modal v-bind:card="card" v-bind:tripList="tripList" v-on:call-parent-delete="closeAfterDelete" />
  </div>
</template>
<script>
import http from "@/common/axios";
import util from "@/common/util";
import CardDetailModal from "../modals/CardDetailModal.vue";

import { Modal } from "bootstrap";

export default {
  components: { CardDetailModal },

  data() {
    return {
      test: false,
      //card
      card: {
        cardComment: "",
        cardCreateDate: { date: "{ year: 0, month: 0, day: 0 }", time: "{ hour: 0, minute: 0, second: 0, nano: 0 }" },
        cardSeq: 0,
        cardTitle: "",
        userSeq: 0,
      },

      //modal
      cardDetailModal: null,

      userInfo: null,
      // cardlist
      List: [
        { cardThumbnailImgUrl: "images/img_1.jpg", title: "첫번째 계획", cardCreatedate: "2023.05.19", comments: "여름휴가" },
        { cardThumbnailImgUrl: "images/img_2.jpg", title: "두번째 계획", regDate: "2023.10.06", comments: "가을휴가" },
        { cardThumbnailImgUrl: "images/img_3.jpg", title: "세번째 계획", regDate: "2023.02.15", comments: "겨울휴가" },
        { cardThumbnailImgUrl: "images/img_4.jpg", title: "네번째 계획", regDate: "2023.02.15", comments: "겨울휴가" },
        { cardThumbnailImgUrl: "images/img_5.jpg", title: "다섯번째 계획", regDate: "2023.02.15", comments: "겨울휴가" },
        { cardThumbnailImgUrl: "images/img_6.jpg", title: "여섯번째 계획", regDate: "2023.02.15", comments: "겨울휴가" },
      ],
      cardList: [],
      tripList: [],
    };
  },
  computed: {
    requireImg: function () {
      if (this.userInfo.userProfileImageUrl == undefined || this.userInfo.userProfileImageUrl == "") {
        // require 은 웹 패킹.. 서버쪽으로 프론트 반영하기직전까지 개발단계에서 패킹할때 쓰는 편..? require을 최종 선택을 하면안됨
        return require("@/assets/img/santa.png");
      } else {
        return require("@/assets" + this.userInfo.userProfileImageUrl);
      }
    },
  },
  methods: {
    //modal
    async showCardDetailModal(item) {
      this.card = item;
      console.log(item.userSeq, item.cardSeq);
      let params = {
        userSeq: item.userSeq,
        cardSeq: item.cardSeq,
      };
      let response = await http.get("/card", { params });
      let { data } = response;

      if (data.result == "login") {
        this.$router.push("/login");
      } else {
        this.tripList = data.tripList;
      }

      console.log(this.card);
      console.log(this.tripList);
      this.cardDetailModal.show();
    },

    closeAfterDelete() {
      this.cardDetailModal.hide();
      this.getCardList();
    },

    async getCardList() {
      let params = {
        userSeq: this.userInfo.userSeq,
      };
      let response = await http.get("/cards", { params });
      let { data } = response;

      console.log(data);
      if (data.result == "login") {
        this.$router.push("/login");
      } else {
        this.cardList = data.list;
        // this.totalListItemCount = data.count;
      }
    },
    ListDate: function (date) {
      return util.makeDateStr(date.year, date.month, date.day, ".");
    },
  },

  created() {
    this.userInfo = JSON.parse(localStorage.getItem("userInfo"));
    console.log(this.userInfo.userProfileImageUrl);
    const scripts = ["/js/tiny-slider.js", "/js/custom.js"];

    const loadScript = (index) => {
      if (index >= scripts.length) {
        this.getCardList();
        return;
      }

      const script = document.createElement("script");
      script.setAttribute("src", scripts[index]);
      script.onload = () => {
        if (index === scripts.length - 1) {
          this.getCardList();
        } else {
          loadScript(index + 1);
        }
      };

      document.head.appendChild(script);
    };

    loadScript(0);
  },

  mounted() {
    this.cardDetailModal = new Modal(document.querySelector("#cardDetailModal"));
  },
};
</script>

<style></style>
