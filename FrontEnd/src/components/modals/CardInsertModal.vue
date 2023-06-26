<template>
  <!-- Modal insert end -->

  <!-- Modal insert start -->
  <div class="modal" id="cardInsertModal" >
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">내 계획 추가</h4>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="titleInsert" class="form-label">제목</label>
            <input type="text" class="form-control" id="titleInsert" v-model="cardTitle" />
          </div>
          <div class="mb-3">
            <label for="commentsInsert" class="form-label">설명</label>
            <input type="text" class="form-control" id="commentsInsert" v-model="cardComment" />
          </div>

          <button id="btnBoardInsert" class="btn btn-sm btn-primary btn-outline float-end" data-bs-dismiss="modal" type="button" @click="cardInsert">
            만들기
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
import http from "@/common/axios";
Vue.use(VueAlertify);

export default {
  props: ["selectTourList"],
  data() {
    return {
      cardTitle: "",
      fileList: [],
    };
  },
  methods: {
    initUI() {
      this.cardTitle = "";
      this.cardComment = "";
    },

    async cardInsert() {
      if (localStorage.getItem("userInfo") == null) {
      this.$alertify.error("로그인이 필요한 서비스입니다.");
      this.$router.push("/login");
    }
      const list = this.selectTourList;
      console.log(list);
      // console.log("제발 우려하는 그게 아니길 ===>", list);

      let params = {
        cardTitle: this.cardTitle,
        cardComment: this.cardComment,
        list: this.selectTourList,
      };

      let response = await http.post("/cards", params);
      let { data } = response;

      console.log(data);

      this.cardList = data.list;
      this.$alertify.success("여행계획이 생성되었습니다.");
      this.closeModal();
    },

    closeModal() {
      this.$emit("call-parent-insert");
    },
  },
  async mounted() {
    // 부트스트랩 모달 이벤트 중 show이벤트를 등록
    let $this = this;
    this.$el.addEventListener("show.bs.modal", function () {
      $this.initUI();
    });
  },
};
</script>

<style scoped></style>
