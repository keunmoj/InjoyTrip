<template>
  <!-- Modal insert end -->

  <!-- Modal insert start -->
  <div class="modal" id="cardDetailModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">{{ card.cardTitle }}</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb">
            <label for="commentsInsert" class="form-label">{{ createDate }}</label>
          </div>
          <!--  -->
          <div v-for="(item, index) in tripList" :key="index">
            <div class="property-item mb-1">
              <div class="property-content p-1 m-1 card">
                <div class="price">
                  <span>{{ item.title }}</span>
                </div>
                <div>
                  <span class="d-block mb-1" style="color: black; font-weight: bold">{{ item.addr1 }} {{ item.addr2 }}</span>
                  <div class="d-flex justify-content-end">
                    <button class="btn btn-warning py-2 px-3 me-1" style="font-size: 12px; font-weight: bold" disabled>조회수 : {{ item.readCount }}</button>
                    <a @click="makerSet(item)" class="btn btn-primary py-2 px-3" style="font-size: 12px">찾기</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--  -->
          <button id="btnBoardInsert" class="btn btn-sm btn-warning btn-outline float-end" data-bs-dismiss="modal" type="button" @click="changeToDelete">
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal insert end -->
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
import util from "@/common/util";
import http from "@/common/axios";

Vue.use(VueAlertify);

export default {
  props: ["card", "tripList"],
  data() {
    return {
      createDate: "",
      cardInfo: null,
      cardTripList: null,
    };
  },
  methods: {
    initData() {
      this.cardInfo = this.card;
      this.cardTripList = this.tripList;
      console.log(this.card);
      this.createDate = this.ListDate(this.card.cardCreatedate.date);
      console.log();
      console.log(this.tripList);
    },

    changeToDelete() {
      var $this = this;
      this.$alertify.confirm(
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.cardDelete();
        },
        function () {
          console.log("최소");
        }
      );
    },
    async cardDelete() {
      try {
        let response = await http.delete("/card/" + this.card.cardSeq);
        let { data } = response;
        console.log(data);

        if (data.result == "login") {
          this.$router.push("/login");
        } else {
          this.$alertify.success("글이 삭제되었습니다.");
          this.deleteModal();
        }
      } catch (error) {
        console.error(error);
      }
    },

    deleteModal() {
      this.$emit("call-parent-delete");
    },

    ListDate: function (date) {
      return util.makeDateStr(date.year, date.month, date.day, ".");
    },
  },

  created() {
    console.log("hello");
  },
  mounted() {
    // 부트스트랩 모달 이벤트 중 show이벤트를 등록
    let $this = this;
    this.$el.addEventListener("show.bs.modal", function () {
      $this.initData();
    });
  },
};
</script>

<style scoped></style>
