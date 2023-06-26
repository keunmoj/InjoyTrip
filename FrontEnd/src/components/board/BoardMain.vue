<template>
  <div>
    <div class="hero page-inner overlay" style="background-image: url('images/trip_bg_image_2.jpg')">
      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-9 text-center mt-5">
            <h1 class="heading" data-aos="fade-up">게시판</h1>
          </div>
        </div>
      </div>
    </div>

    <div class="section section-properties">
      <div class="container">
        <div class="input-group mb-3">
          <input v-model="searchWord" type="text" class="form-control" placeholder="Search" />
          <button @click="boardList" class="btn btn-primary">검색</button>
        </div>

        <table class="table table-hover">
          <thead>
            <tr>
              <th>#</th>
              <th>제목</th>
              <th>작성자</th>
              <th>작성일시</th>
              <th>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(board, index) in list" :key="index" @click="boardDetail(board.boardId)">
              <td>{{ board.boardId }}</td>
              <td>{{ board.title }}</td>
              <td>{{ board.userName }}</td>
              <td>{{ ListDate(board.regDt.date) }}</td>
              <td>{{ board.readCount }}</td>
            </tr>
          </tbody>
        </table>
        <PaginationUI
          v-bind:listRowCount="listRowCount"
          v-bind:pageLinkCount="pageLinkCount"
          v-bind:currentPageIndex="currentPageIndex"
          v-bind:totalListItemCount="totalListItemCount"
          v-on:call-parent-move-page="movePage"
        ></PaginationUI>

        <button class="btn btn-sm btn-primary" @click="showInsertModal">글쓰기</button>

        <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
        <update-modal v-bind:board="board" v-on:call-parent-update="closeAfterUpdate"></update-modal>
        <detail-modal v-bind:board="board" v-on:call-parent-change-to-update="changeToUpdate" v-on:call-parent-change-to-delete="changeToDelete"></detail-modal>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/common/axios";
import util from "@/common/util";

import DetailModal from "../modals/DetailModal.vue";
import InsertModal from "../modals/InsertModal.vue";
import UpdateModal from "@/components/modals/UpdateModal.vue";
import PaginationUI from "@/components/common/PaginationUI.vue";

import { Modal } from "bootstrap";
// import routers from "@/routers/routers";
export default {
  components: { InsertModal, DetailModal, UpdateModal, PaginationUI },
  data() {
    return {
      // modal
      insertModal: null,
      detailModal: null,
      updateModal: null,

      //list
      list: [],
      limit: 10,
      offset: 0,
      searchWord: "",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,
      totalListItemCount: 0,

      // detail
      board: {
        boardId: 0,
        title: "",
        content: "",
        userName: "",
        regDate: "",
        regTime: "",
        readCount: 0,
        fileList: [],
        sameUser: false,
      },
    };
  },
  methods: {
    async boardList() {
      let params = {
        limit: this.limit,
        offset: this.offset,
        searchWord: this.searchWord,
      };
      let response = await http.get("/boards", { params });
      let { data } = response;

      console.log(data);

      this.list = data.list;
      this.totalListItemCount = data.count;
    },
    ListDate: function (date) {
      return util.makeDateStr(date.year, date.month, date.day, ".");
    },
    movePage(pageIndex) {
      this.offset = (pageIndex - 1) * this.listRowCount;
      this.currentPageIndex = pageIndex;
      this.boardList();
    },
    async boardDetail(boardId) {
      let response = await http.get("/boards/" + boardId);
      let { data } = response;

      console.log(data);
      if (data.result == "login") {
        this.$router.push("/login");
      } else {
        let { regDt } = data.dto;
        let boardNew = {
          regDate: util.makeDateStr(regDt.date.year, regDt.date.month, regDt.date.day, "."),
          regTime: util.makeTimeStr(regDt.time.hour, regDt.time.minute, regDt.time.second, ":"),
          ...data.dto,
        };

        this.board = boardNew;
        this.detailModal.show();
      }
    },
    showInsertModal() {
      this.insertModal.show();
    },

    closeAfterInsert() {
      this.insertModal.hide();
      this.boardList();
    },

    changeToUpdate() {
      this.detailModal.hide();
      this.updateModal.show();
    },

    changeToDelete() {
      this.detailModal.hide();

      var $this = this; // alertify.confirm-function() 에서 this 는 alertify 객체
      this.$alertify.confirm(
        // '삭제 확인', '이 글을 삭제하시겠습니까?', <- ???? title 추가하면 cancel 이 ok 처럼 동작
        "이 글을 삭제하시겠습니까?",
        function () {
          $this.boardDelete(); // $this 사용
        },
        function () {
          console.log("cancel");
        }
      );
    },
    async boardDelete() {
      try {
        let response = await http.delete("/boards/" + this.board.boardId);
        let { data } = response;
        console.log(data);

        if (data.result == "login") {
          this.$router.push("/login");
        } else {
          this.$alertify.success("글이 삭제되었습니다.");
          this.boardList();
        }
      } catch (error) {
        console.error(error);
      }
    },
    closeAfterUpdate() {
      this.updateModal.hide();
      this.boardList();
    },
  },
  created() {
    if (localStorage.getItem("userInfo") == null) {
      this.$alertify.error("로그인이 필요한 서비스입니다.");
      this.$router.push("/login");
    }

    let script = document.createElement("script");
    script.setAttribute("src", "/js/custom.js");
    document.head.append(script);
    script.setAttribute("src", "/js/tiny-slider.js");
    document.head.appendChild(script);

    this.boardList();
  },

  mounted() {
    this.insertModal = new Modal(document.querySelector("#insertModal"));
    this.detailModal = new Modal(document.querySelector("#detailModal"));
    this.updateModal = new Modal(document.querySelector("#updateModal"));
  },
};
</script>

<style></style>
