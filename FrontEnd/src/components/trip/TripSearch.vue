<template>
  <div>
    <div class="section section-properties pb-0 pt-0 site-nav" id="search" style="top: 85%; z-index: 8">
      <div class="container" style="">
        <div class="row m-3 d-flex align-items-center">
          <!-- select -->
          <select class="col form-select mx-1" id="sido" v-model="sidoCode" @change="gugunList(sidoCode)">
            <option selected disabled>시/도</option>
            <option value="0">시/도</option>
            <option v-for="(value, index) in sido" :key="index" :value="value.sidoCode">{{ value.sidoName }}</option>
          </select>

          <select class="col form-select mx-1" id="gugun" v-model="gugunCode">
            <option selected disabled>구/군</option>
            <option value="0">구/군</option>
            <option v-for="(value, index) in gugun" :key="index" :value="value.gugunCode">{{ value.gugunName }}</option>
          </select>
          <!-- 
          <select class="col form-select mx-1" id="cat1" v-model="cat1" @change="getCat2List($event.target.value)">
            <option selected disabled>cat1</option>
            <option value="0">X</option>
          </select>

          <select class="col form-select mx-1" id="cat2" v-model="cat2" @change="getCat3List($event.target.value)">
            <option selected disabled>cat2</option>
            <option value="0">X</option>
          </select> -->
        </div>
        <div class="row m-3 d-flex align-items-center justify-content-end search-wrapper" :class="{ active: isActive }">
          <div class="col d-flex p-0">
            <input type="text" class="form-control mx-1" placeholder="Type to search" v-model="searchWord" />
          </div>
          <button class="btn btn-primary text-light mx-1 col-2" @click="search">
            <span>Search</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/common/axios";

export default {
  data() {
    return {
      // sido, gugun, search
      sido: [],
      sidoCode: 0,
      gugun: [],
      gugunCode: 0,
      searchWord: "",

      // 검색창 토글
      isActive: false,
    };
  },
  methods: {
    // 시도 리스트 조회 메소드
    async sidoList() {
      let response = await http.get("/sido");
      let { data } = response;

      console.log("sido =>", data);
      this.sido = data;
    },

    // 구군 리스트 조회 메소드
    async gugunList(sidoCode) {
      let params = {
        sidoCode: sidoCode,
      };

      let response = await http.get("/gugun", { params });
      let { data } = response;

      console.log("gugun => ", data);

      this.gugun = data;
      this.gugunCode = 0;
    },

    // // 부모 컴포넌트로 데이터를 보내기 위한 메소드들
    // sidoGugun(sidoCode, gugunCode) {
    //   this.$emit("call-parent-sido-gugun", sidoCode, gugunCode);
    // },

    search() {
      console.log("search() => ", this.sidoCode, " ", this.gugunCode, " ", this.searchWord);
      this.$emit("call-parent-search", this.searchWord, this.sidoCode, this.gugunCode);
      this.$emit("call-parent-init-pagination");
    },
  },
  mounted() {
    console.log("sido-gugun mounted()");
    this.sidoList();
  },
};
</script>

<style></style>
