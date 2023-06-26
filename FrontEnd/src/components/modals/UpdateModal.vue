<template>
  <div class="modal fade" id="updateModal">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">글수정</h4>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="titleUpdate" class="form-label">제목</label>
            <input
              type="text"
              class="form-control"
              id="titleUpdate"
              v-model="title"
            />
          </div>
          <div class="mb-3">
            <!-- New for FileUpload, CKEditor -->
            <div id="divEditorUpdate"></div>
            <!-- New for FileUpload -->
          </div>
          <div class="mb-3">
            첨부파일 :
            <!-- props board 에서 이전 첨부된 file list -->
            <span v-for="(file, index) in board.fileList" :key="index">{{
              file.fileName
            }}</span>
          </div>
          <div class="mb-3">
            <div class="form-check">
              <input
                v-model="attachFile"
                class="form-check-input"
                type="checkbox"
                value=""
                id="chkFileUploadUpdate"
              />
              <label class="form-check-label" for="chkFileUploadUpdate"
                >파일 변경</label
              >
            </div>
          </div>
          <div v-show="attachFile" class="mb-3" id="imgFileUploadUpdateWrapper">
            <input
              type="file"
              @change="changeFile"
              id="inputFileUploadUpdate"
              multiple
            />
            <div id="imgFileUploadUpdateThumbnail" class="thumbnail-wrapper">
              <img
                v-for="(file, index) in fileList"
                v-bind:key="index"
                v-bind:src="file"
              />
            </div>
          </div>
          <button
            @click="boardUpdate"
            id="btnBoardUpdate"
            class="btn btn-sm btn-primary btn-outline float-end"
            data-bs-dismiss="modal"
            type="button"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import VueAlertify from "vue-alertify";
import http from "@/common/axios";
Vue.use(CKEditor).use(VueAlertify);

export default {
  props: ["board"],

  data() {
    return {
      boardId: "",
      title: "",
      content: "",
      CKEditor: "",
      attachFile: false,
      fileList: [],
    };
  },
  methods: {
    changeFile(fileEvent) {
      this.fileList = [];
      const fileArray = Array.from(fileEvent.target.files); // Array 로 변환 가능
      fileArray.forEach((file) => {
        this.fileList.push(URL.createObjectURL(file));
      });
    },
    async boardUpdate() {
      // file upload - multipart/form-data
      let formData = new FormData();
      formData.append("boardId", this.boardId);
      formData.append("title", this.title);
      formData.append("content", this.CKEditor.getData());
      let files = document.querySelector("#inputFileUploadUpdate").files;

      const fileArray = Array.from(files); // Array 로 변환 가능
      fileArray.forEach((file) => formData.append("file", file));

      let options = {
        headers: { "Content-Type": "multipart/form-data" },
      };

      let response = await http.post(
        "/boards/" + this.boardId,
        formData,
        options
      );
      let { data } = response;

      console.log(data);
      if (data.result == "login") {
        this.$router.push("/login");
      } else {
        this.$alertify.success("글이 수정되었습니다.");
        this.closeModal();
      }
    },
    closeModal() {
      this.$emit("call-parent-update");
    },
  },
  watch: {
    board: function () {
      // board props를 watch하면서 board가 변경되면 그 시점에 board props -> boardId, ... data()항목을 갱신
      this.boardId = this.board.boardId;
      this.title = this.board.title;
      this.CKEditor.setData(this.board.content);

      this.attachFile = false;
      this.fileList = this.board.fileList;
      document.querySelector("#inputFileUploadUpdate").value = "";
    },
  },

  async mounted() {
    try {
      this.CKEditor = await ClassicEditor.create(
        document.querySelector("#divEditorUpdate")
      );
    } catch (error) {
      console.error(error);
    }
  },
};
</script>
<style scoped>
/*CKEditor Height*/
.modal >>> .ck-editor__editable {
  width: 100%;
  height: 200px;
  overflow-y: scroll;
}

/*파일업로드 thumbnail*/
.modal >>> .thumbnail-wrapper {
  margin-top: 5px;
}

.modal >>> .thumbnail-wrapper img {
  width: 100px !important;
  margin-right: 5px;
  max-width: 100%;
}
</style>
