<template>
  <div class="row align-items-center py-5">
    <div class="col-lg-12 text-center">
      <div class="custom-pagination">
        <a v-if="prev" href="#" aria-label="Previous" @click="paginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">&laquo;</span>
        </a>

        <a
          v-for="index in endPageIndex - startPageIndex + 1"
          :key="index"
          v-bind:class="{
            active: startPageIndex + index - 1 == currentPageIndex,
          }"
          @click="paginationChanged(startPageIndex + index - 1)"
          class="mx-1"
        >
          {{ startPageIndex + index - 1 }}
        </a>

        <a v-if="next" aria-label="Next" @click="paginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["listRowCount", "pageLinkCount", "currentPageIndex", "totalListItemCount"],
  computed: {
    pageCount: function () {
      return Math.ceil(this.totalListItemCount / this.listRowCount);
    },
    startPageIndex: function () {
      if (this.currentPageIndex % this.pageLinkCount == 0) {
        return this.currentPageIndex - this.pageLinkCount + 1;
      } else {
        return Math.floor(this.currentPageIndex / this.pageLinkCount) * this.pageLinkCount + 1;
      }
    },

    endPageIndex: function () {
      let ret = 0;
      if (this.currentPageIndex % this.pageLinkCount == 0) {
        //10, 20...맨마지막
        ret = this.currentPageIndex;
      } else {
        ret = Math.floor(this.currentPageIndex / this.pageLinkCount) * this.pageLinkCount + this.pageLinkCount;
      }
      return ret > this.pageCount ? this.pageCount : ret;
    },

    prev: function () {
      if (this.currentPageIndex <= this.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },

    next: function () {
      if (Math.floor(this.pageCount / this.pageLinkCount) * this.pageLinkCount < this.currentPageIndex) {
        console.log("next is false");
        return false;
      } else {
        console.log("next is true");
        return true;
      }
    },
  },

  methods: {
    paginationChanged(pageIndex) {
      this.$emit("call-parent-move-page", pageIndex);
    },
  },
};
</script>

<style scoped>
ul li {
  cursor: pointer;
}
</style>
