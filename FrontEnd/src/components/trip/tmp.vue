// 지정한 위치에 마커 및 장소 불러오기
    loadMarker() {
      var listEl = document.getElementById("placesList");
      var fragment = document.createDocumentFragment();

      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      this.removeAllChildNods(listEl);

      // 마커를 생성합니다
      this.markers = [];
      this.positions.forEach((position) => {
        // information window 생성
        const infowindow = new kakao.maps.InfoWindow({
          removable: true,
          content: `
              <div class="card">
                <img src="${position.img}" class="card-img-top" style="height: 10rem" alt="...">
                <div class="card-body">
                  <h5 class="card-title fw-bold">${position.title}</h5>
                  <p class="card-text"> 주소 : ${position.addr}</p>
                  <p class="card-text"> 전화번호 : ${position.tel}</p>
                </div>
              </div>
                    `,
        });
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          //   image: markerImage, // 마커의 이미지
        });
        // 이벤트 등록
        //  kakao.maps.event.addListener(marker, "click", () => {infowindow.open(this.map, marker);});
        kakao.maps.event.addListener(marker, "mouseover", () => {
          infowindow.open(this.map, marker);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          infowindow.close(this.map, marker);
        });

        this.markers.push(marker);

        //=====================사이드 바에 관광지 리스트 만들기===================

        let obj = {
          title: position.title,
          addr: position.addr,
          tel: position.tel,
        };
        const itemEl = this.getListItem(obj);

        // 이벤트 등록
        itemEl.onmouseover = () => {
          infowindow.open(this.map, marker);
        };

        itemEl.onmouseout = () => {
          infowindow.close(this.map, marker);
        };

        fragment.appendChild(itemEl);
      });

      // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
      listEl.appendChild(fragment);

      console.log("마커수 :" + this.markers.length);

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },