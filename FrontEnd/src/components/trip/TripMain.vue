<template>
  <div>
    <div class="map_wrap row m-0">
      <!-- Right SideBar -->
      <div class="col-2 site-nav" style="width: 16%; background-color: rgba(255, 255, 255, 0.5); box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.8)">
        <div class="">
          <h1 class="btn btn-primary" style="margin-left: 20%; color: white; font-size: 20px; padding: 15px">SEARCH LIST</h1>
          <hr />
          <div class="row d-flex justify-content-center scroll mt-3" id="cardEl" style="height: 75vh; overflow: scroll">
            <!-- item start -->
            <div v-for="(item, index) in tourList" :key="index">
              <div class="property-item mb-10">
                <div class="property-content p-1 m-1 card">
                  <div class="price mb-2">
                    <span>{{ item.title }}</span>
                  </div>
                  <div>
                    <span class="d-block mb-1" style="color: black; font-weight: bold">분류: {{ item.contentType }}</span>
                    <span class="d-block mb-1" style="color: black; font-weight: bold">{{ item.addr1 }} {{ item.addr2 }}</span>
                    <div class="d-flex justify-content-end">
                      <button class="btn btn-warning py-2 px-3 me-1" style="font-size: 12px; font-weight: bold" disabled>조회수 : {{ item.readCount }}</button>
                      <a @click="makerSet(item)" class="btn btn-primary py-2 px-3" style="font-size: 12px">찾기</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
              <!-- .item -->
          </div>
          <hr />
          <div class="d-flex justify-content-end mt-3">
            <PaginationUI
              v-bind:listRowCount="listRowCount"
              v-bind:pageLinkCount="pageLinkCount"
              v-bind:currentPageIndex="currentPageIndex"
              v-bind:totalListItemCount="totalListItemCount"
              v-on:call-parent-move-page="movePage"
            ></PaginationUI>
          </div>
        </div>
      </div>
      <div id="map" class="col-10" style="width: 100%; height: 100%">
        <trip-search v-on:call-parent-search="getTripList" v-on:call-parent-init-pagination="initPagination"></trip-search>
      </div>
      <!-- right SideBar -->
      <div class="col-2 site-nav" style="width: 16%; background-color: rgba(255, 255, 255, 0.5); box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.8); margin-left: 84%">
        <div class="">
          <h1 class="btn btn-primary" style="margin-left: 30%; color: white; font-size: 20px; padding: 15px">WISH LIST</h1>
          <hr />
          <div class="row d-flex justify-content-center scroll mt-3" id="cardEl" style="height: 80vh; overflow: scroll">
            <!-- item start -->
            <div>
              <div v-for="(item, index) in selectTourList" :key="index" class="property-item mb-10">
                <div class="property-content p-1 m-1 card">
                  <div class="price mb-2">
                    <span>{{ item.title }}</span>
                  </div>
                  <div class="btn-wrapper">
                    <span class="d-block" style="color: black; font-weight: bold">{{ item.contentType }}</span>
                    <span class="d-block" style="color: black; font-weight: bold">{{ item.addr1 }} {{ item.addr2 }}</span>
                    <div class="d-flex justify-content-end">
                      <a @click="removeItem(item)" class="btn btn-warning py-2 px-3">삭제</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <hr />
          <div class="d-flex justify-content-end mt-3">
            <a @click="showCardInsertModal" class="btn btn-primary py-2 px-3" style="margin-right: 10px">등록</a>
            <a @click="clearTourList" class="btn btn-warning py-2 px-3">초기화</a>
          </div>
        </div>
      </div>
    </div>

    <card-insert-modal v-bind:selectTourList="selectTourList" v-on:call-parent-insert="closeAfterInsert" />
  </div>
</template>

<script>
import http from "@/common/axios.js";
import Vue from "vue";
import VueAlertify from "vue-alertify";

import TripSearch from "@/components/trip/TripSearch";
import PaginationUI from "@/components/common/PaginationUI.vue";
import CardInsertModal from "../modals/CardInsertModal.vue";

import { Modal } from "bootstrap";

Vue.use(VueAlertify);

export default {
  props: ["userInfo"],
  components: {
    TripSearch,
    PaginationUI,
    CardInsertModal,
  },
  data() {
    return {
      // modal
      cardInsertModal: null,

      // 추가된 부분
      selectTourList: [],
      //map
      map: null,
      tourMarkers: [],
      tourPositions: [],
      foodMarkers: [],
      foodPositions: [],
      lodgementMarkers: [],
      lodgementPositions: [],
      activityMarkers: [],
      activityPositions: [],
      // pagination
      listRowCount: 15,
      pageLinkCount: 3,
      currentPageIndex: 1,
      totalListItemCount: 0,

      // list
      tourList: [],
      foodList: [],
      lodgementList: [],
      activityList: [],
      limit: 15,
      offset: 0,
      searchWord: "",
      totalListCount: 0,
      sidoCode: 0,
      gugunCode: 0,

      // detail modal
      detailTrip: null,
      isModalOpen: false,
      kakao: {
        contentId: "",
        contentTypeId: "",
        title: "",
        addr1: "",
        addr2: "",
        tel: "",
        firstImage: "",
        fiestImage2: "",
        readCount: "",
        sidoCode: "",
        gugunCode: "",
        latitude: "",
        longitude: "",
      },
    };
  },

  methods: {
    //modal
    showCardInsertModal() {
      if (this.selectTourList.length == 0) {
        return this.$alertify.error("항목을 선택해주세요!");
      }
      this.cardInsertModal.show();
    },
    closeAfterInsert() {
      this.cardInsertModal.hide();
      this.clearTourList();
    },

    // trip
    async getTripList(searchWord, sidoCode, gugunCode) {
      let params = {};

      this.sidoCode = sidoCode;
      this.gugunCode = gugunCode;

      console.log("list", searchWord, sidoCode, gugunCode);

      if (sidoCode == 0 && gugunCode == 0) {
        console.log("시도구군 값이 0인 로직");
        params = {
          limit: this.limit,
          offset: this.offset,
          searchWord: searchWord,
          contentTypeId: 12,
        };
      } else if (sidoCode != 0 && gugunCode == 0) {
        console.log("구군 값이 0인 로직");
        params = {
          limit: this.limit,
          offset: this.offset,
          searchWord: searchWord,
          contentTypeId: 12,
          sidoCode: sidoCode,
        };
      } else {
        console.log("시도구군 값이 0이 아닌 로직");
        params = {
          limit: this.limit,
          offset: this.offset,
          searchWord: searchWord,
          contentTypeId: 12,
          sidoCode: sidoCode,
          gugunCode: gugunCode,
        };
        console.log(params.sidoCode, params.gugunCode);
      }

      let response = await http.get("/trips", { params });
      let { data } = response;
      // 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)
      let temp = data.list;
      for (var i = 0; i < temp.length; i++) {
        if (temp[i].contentTypeId == 12) {
          temp[i].contentType = "관광지";
        } else if (temp[i].contentTypeId == 14) {
          temp[i].contentType = "문화시설";
        } else if (temp[i].contentTypeId == 15) {
          temp[i].contentType = "축제공연행사";
        } else if (temp[i].contentTypeId == 25) {
          temp[i].contentType = "여행코스";
        } else if (temp[i].contentTypeId == 28) {
          temp[i].contentType = "레포츠";
        } else if (temp[i].contentTypeId == 32) {
          temp[i].contentType = "숙박";
        } else if (temp[i].contentTypeId == 38) {
          temp[i].contentType = "쇼핑";
        } else if (temp[i].contentTypeId == 39) {
          temp[i].contentType = "음식점";
        }
      }
      this.tourList = data.list;
      this.totalListItemCount = data.count;

      this.getFoodList(params);
      this.getLodgementList(params);
      this.getActivityList(params);

      // console
      if (this.tourList.length == 0) {
        this.$alertify.error("검색결과가 없습니다.");
      } else {
        console.log("trip tourlist =>", data);
        this.loadTourMaker();
      }
    },
    async getFoodList(params) {
      params.contentTypeId = 39;
      let response = await http.get("/foods", { params });
      let { data } = response;
      this.foodList = data.list;
      this.totalListItemCount = data.count;
      console.log("식당list===>", this.foodList);

      if (this.foodList.length == 0) {
        this.$alertify.error("검색결과가 없습니다.");
      } else {
        this.loadFoodMaker();
      }
    },
    async getLodgementList(params) {
      params.contentTypeId = 32;
      let response = await http.get("/lodgements", { params });
      let { data } = response;
      this.lodgementList = data.list;
      this.totalListItemCount = data.count;
      console.log("숙박list===>", this.lodgementList);

      if (this.lodgementList.length == 0) {
        this.$alertify.error("검색결과가 없습니다.");
      } else {
        this.loadLodgementMaker();
      }
    },
    async getActivityList(params) {
      params.contentTypeId = 28;
      let response = await http.get("/activities", { params });
      let { data } = response;
      this.activityList = data.list;
      this.totalListItemCount = data.count;
      console.log("액티비티list===>", this.activityList);
      if (this.activityList.length == 0) {
        this.$alertify.error("검색결과가 없습니다.");
      } else {
        this.loadActivityMaker();
      }
    },

    // 이미지 로드
    requireImage(trip) {
      return trip.firstImage || trip.firstImage2 || require("@/assets/img/noThumbnail.jpg");
    },

    initPagination() {
      this.listRowCount = 15;
      this.pageLinkCount = 3;
      this.currentPageIndex = 1;
      this.totalListItemCount = 0;
    },
    movePage(pageIndex) {
      this.offset = (pageIndex - 1) * this.listRowCount;
      this.currentPageIndex = pageIndex;
      console.log("mogePage ===> ", this.sidoCode, this.gugunCode);
      this.getTripList(this.searchWord, this.sidoCode, this.gugunCode);
    },

    //////////////////////////////map
    loadScript() {
      const script = document.createElement("script");
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=1579dd9a5c6973b455d6ae853855bcd5&autoload=false"; // &autoload=false api를 로드한 후 맵을 그리는 함수가 실행되도록 구현
      script.onload = () => window.kakao.maps.load(this.loadMap); // 스크립트 로드가 끝나면 지도를 실행될 준비가 되어 있다면 지도가 실행되도록 구현

      document.head.appendChild(script); // html>head 안에 스크립트 소스를 추가
    },

    loadMap() {
      const mapContainer = document.getElementById("map");

      // 카카오 지도 생성 및 초기화
      this.map = new window.kakao.maps.Map(mapContainer, {
        center: new window.kakao.maps.LatLng(37.5665, 126.978),
        level: 5,
      });
    },

    loadTourMaker() {
      this.removeTourMaker();
      this.tourMarkers = [];
      this.tourPositions = [];

      //마커 이미지
      var imageSrc = "https://ifh.cc/g/XvRAVf.png";
      var imageSize = new window.kakao.maps.Size(24, 24);
      var imageOption = { offset: new window.kakao.maps.Point(27, 69) };
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

      this.tourList.forEach((el) => {
        let position = new window.kakao.maps.LatLng(el.latitude, el.longitude);
        var marker = new window.kakao.maps.Marker({
          map: this.map,
          position: position,
          image: markerImage,
        });

        var infowindow = new window.kakao.maps.InfoWindow({
          content: `
          <div class="property-item" style="width:300px;">
            <div style="width:300px; height:300px;">
              <img src=${el.firstImage} class="img-fluid card" alt="이미지" style="width: 100%; height: 100%; object-fit: cover;">
            </div>
            <div class="property-content card">
              <div class="price mb-2">
                 <span> ${el.title} </span>
              </div>
            <div>
            <span class="city d-block mb-3">${el.addr1} ${el.addr2}</span>
          </div>
          `, // 인포윈도우에 표시할 내용
        });
        this.tourMarkers.push(marker);
        this.tourPositions.push(position);

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        window.kakao.maps.event.addListener(marker, "mouseover", this.makeOverListener(this.map, marker, infowindow));
        window.kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));
        window.kakao.maps.event.addListener(marker, "click", this.makeClickListener(el));
      });
      const bounds = this.tourPositions.reduce((bounds, position) => bounds.extend(position), new window.kakao.maps.LatLngBounds());

      this.map.setBounds(bounds);
      this.map.setLevel(this.map.getLevel() + 1);
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    removeTourMaker() {
      // console.log("removeMaker ==>", this.tourMarkers.length);
      this.tourMarkers.forEach((el) => {
        console.log(el);
        if (el != null) {
          el.setMap(null); // 지도에 있는 마커 모두 제거
        }
      });
    },

    loadFoodMaker() {
      this.removeFoodMaker();
      this.foodMarkers = [];
      this.foodPositions = [];

      //마커 이미지
      var imageSrc = "https://ifh.cc/g/RG4FCM.png";
      var imageSize = new window.kakao.maps.Size(24, 24);
      var imageOption = { offset: new window.kakao.maps.Point(27, 69) };
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

      this.foodList.forEach((el) => {
        let position = new window.kakao.maps.LatLng(el.latitude, el.longitude);
        var marker = new window.kakao.maps.Marker({
          map: this.map,
          position: position,
          image: markerImage,
        });

        var infowindow = new window.kakao.maps.InfoWindow({
          content: `
          <div class="property-item" style="width:300px;">
            <div style="width:300px; height:300px;">
              <img src=${el.firstImage} class="img-fluid card" alt="이미지" style="width: 100%; height: 100%; object-fit: cover;">
            </div>
            <div class="property-content card">
              <div class="price mb-2">
                 <span> ${el.title} </span>
              </div>
            <div>
            <span class="city d-block mb-3">${el.addr1} ${el.addr2}</span>
          </div>
          `, // 인포윈도우에 표시할 내용
        });
        this.foodMarkers.push(marker);
        this.foodPositions.push(position);

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        window.kakao.maps.event.addListener(marker, "mouseover", this.makeOverListener(this.map, marker, infowindow));
        window.kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));
        window.kakao.maps.event.addListener(marker, "click", this.makeClickListener(el));
      });
      // const bounds = this.foodPositions.reduce((bounds, position) => bounds.extend(position), new window.kakao.maps.LatLngBounds());

      // this.map.setBounds(bounds);
      // this.map.setLevel(this.map.getLevel() + 1);
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    removeFoodMaker() {
      this.foodMarkers.forEach((el) => {
        console.log(el);
        if (el != null) {
          el.setMap(null); // 지도에 있는 마커 모두 제거
        }
      });
    },
    loadLodgementMaker() {
      this.removeLodgementMaker();
      this.lodgementMarkers = [];
      this.lodgementPositions = [];

      //마커 이미지
      var imageSrc = "https://ifh.cc/g/lxTHx6.png";
      var imageSize = new window.kakao.maps.Size(24, 24);
      var imageOption = { offset: new window.kakao.maps.Point(27, 69) };
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

      this.lodgementList.forEach((el) => {
        let position = new window.kakao.maps.LatLng(el.latitude, el.longitude);
        var marker = new window.kakao.maps.Marker({
          map: this.map,
          position: position,
          image: markerImage,
        });

        var infowindow = new window.kakao.maps.InfoWindow({
          content: `
          <div class="property-item" style="width:300px;">
            <div style="width:300px; height:300px;">
              <img src=${el.firstImage} class="img-fluid card" alt="이미지" style="width: 100%; height: 100%; object-fit: cover;">
            </div>
            <div class="property-content card">
              <div class="price mb-2">
                 <span> ${el.title} </span>
              </div>
            <div>
            <span class="city d-block mb-3">${el.addr1} ${el.addr2}</span>
          </div>
          `, // 인포윈도우에 표시할 내용
        });
        this.lodgementMarkers.push(marker);
        this.lodgementPositions.push(position);

        window.kakao.maps.event.addListener(marker, "mouseover", this.makeOverListener(this.map, marker, infowindow));
        window.kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));
        window.kakao.maps.event.addListener(marker, "click", this.makeClickListener(el));
      });
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    removeLodgementMaker() {
      this.lodgementMarkers.forEach((el) => {
        if (el != null) {
          el.setMap(null); // 지도에 있는 마커 모두 제거
        }
      });
    },
    loadActivityMaker() {
      this.removeActivityMaker();
      this.activityMarkers = [];
      this.activityPositions = [];

      //마커 이미지
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";
      var imageSize = new window.kakao.maps.Size(32, 35);
      var imageOption = { offset: new window.kakao.maps.Point(27, 69) };
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

      this.activityList.forEach((el) => {
        let position = new window.kakao.maps.LatLng(el.latitude, el.longitude);
        var marker = new window.kakao.maps.Marker({
          map: this.map,
          position: position,
          image: markerImage,
        });

        var infowindow = new window.kakao.maps.InfoWindow({
          content: `
          <div class="property-item" style="width:300px;">
            <div style="width:300px; height:300px;">
              <img src=${el.firstImage} class="img-fluid card" alt="이미지" style="width: 100%; height: 100%; object-fit: cover;">
            </div>
            <div class="property-content card">
              <div class="price mb-2">
                 <span> ${el.title} </span>
              </div>
            <div>
            <span class="city d-block mb-3">${el.addr1} ${el.addr2}</span>
          </div>
          `, // 인포윈도우에 표시할 내용
        });
        this.activityMarkers.push(marker);
        this.activityPositions.push(position);

        window.kakao.maps.event.addListener(marker, "mouseover", this.makeOverListener(this.map, marker, infowindow));
        window.kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow));
        window.kakao.maps.event.addListener(marker, "click", this.makeClickListener(el));
      });
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    removeActivityMaker() {
      this.activityMarkers.forEach((el) => {
        if (el != null) {
          el.setMap(null); // 지도에 있는 마커 모두 제거
        }
      });
    },

    makeOverListener(map, marker, infowindow) {
      return function () {
        infowindow.open(map, marker);
      };
    },

    // 인포윈도우를 닫는 클로저를 만드는 함수입니다
    makeOutListener(infowindow) {
      return function () {
        infowindow.close();
      };
    },

    makeClickListener(item) {
      return () => {
        console.log("call click listener!!");
        // 문제 상황 : return function() {} 의 형태로 사용할 경우 this는 vue 인스턴스 객체를 가져오지 못한다.
        // 해결 : arrow function을 사용하면 this로 vue 인스턴스 객체를 가져올 수 있다.

        if (this.selectTourList.length >= 10) {
          this.$alertify.error("최대 10개까지 선택할 수 있습니다.");
        } else {
          let check = false;
          for (var i = 0; i < this.selectTourList.length; i++) {
            if (this.selectTourList[i].contentId == item.contentId) {
              check = true;
              break;
            }
          }

          if (check) {
            this.$alertify.error("중복 선택할 수 없습니다.");
          } else {
            // 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)
            if (item.contentTypeId == 12) {
              item.contentType = "관광지";
            } else if (item.contentTypeId == 14) {
              item.contentType = "문화시설";
            } else if (item.contentTypeId == 15) {
              item.contentType = "축제공연행사";
            } else if (item.contentTypeId == 25) {
              item.contentType = "여행코스";
            } else if (item.contentTypeId == 28) {
              item.contentType = "레포츠";
            } else if (item.contentTypeId == 32) {
              item.contentType = "숙박";
            } else if (item.contentTypeId == 38) {
              item.contentType = "쇼핑";
            } else if (item.contentTypeId == 39) {
              item.contentType = "음식점";
            }
            this.selectTourList.push(item);
            console.log(this.selectTourList);
          }
        }
      };
    },

    removeItem(item) {
      console.log("call - removeItem");
      for (var i = 0; i < this.selectTourList.length; i++) {
        if (this.selectTourList[i].contentId == item.contentId) {
          this.selectTourList.splice(i, 1);
          i--;
        }
      }
      console.log(this.selectTourList);
    },

    makerSet(item) {
      // console.log(item);
      this.drawPicker1(item.latitude, item.longitude);
    },
    drawPicker1(lat, lng) {
      const self = this; // Vue 컴포넌트의 this를 다른 변수에 할당
      // console.log(lat + " " + lng);
      this.map.setLevel(3);

      let position = new window.kakao.maps.LatLng(lat, lng);

      self.map.panTo(position);
    },

    // show info
    itemInfo(item) {
      console.log(item);
    },

    // 선택된 아이템 초기화
    clearTourList() {
      this.selectTourList = [];
    },

    // 선택된 아이템들을 카드로 등록
    async registTourList() {
      const list = this.selectTourList;
      console.log("제발 우려하는 그게 아니길 ===>", list);

      let response = await http.post("/cards", list);
      let { data } = response;

      console.log(data);

      this.cardList = data.list;
      this.clearTourList();
      this.$alertify.success("여행계획이 생성되었습니다.");
    },
  },

  created() {
    let script = document.createElement("script");
    script.setAttribute("src", "/js/custom.js");
    document.head.appendChild(script);

    if (localStorage.getItem("userInfo") == null) {
      this.$alertify.error("로그인이 필요한 서비스입니다.");
      this.$router.push("/login");
    }
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      // 카카오 객체가 있고, 카카오 맵그릴 준비가 되어 있다면 맵 실행
      this.loadMap();
    } else {
      // 없다면 카카오 스크립트 추가 후 맵 실행
      this.loadScript();
    }

    this.cardInsertModal = new Modal(document.querySelector("#cardInsertModal"));
  },
};
</script>

<style scoped>
.map_wrap {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 100vh;
}

.scroll::-webkit-scrollbar {
  display: none;
}

.btn-wrapper >>> .btn {
  font-size: 12px;
  padding: 6px !important;
  width: 80px;
}
</style>
