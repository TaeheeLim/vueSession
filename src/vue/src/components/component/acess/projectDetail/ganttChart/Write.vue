<template>
  <div class="write-container">
    <div class="write-header">
      <span class="write-title">일감 등록</span>
      <span
        ><button @click="addTask()" class="write-btn btn">작성</button></span
      >
    </div>
    <hr class="write-line" />

    <table class="write-table">
      <tr>
        <th>상태</th>
        <td><input type="text" :value="inputState" /></td>
        <th>진척도</th>
        <td>
          <input type="text" :value="inputProgress" />
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td class="select">
          <span @click="showSelectBox">{{ inputPriority }}</span>
          <ul
            @click="selectPriority"
            class="select-list hide"
            :class="{ show: isActive }"
          >
            <li>낮음</li>
            <!-- #4caf50 -->
            <li>보통</li>
            <!-- #0091ff -->
            <li>높음</li>
            <!-- #ffbf00-->
            <li>긴급</li>
            <!--#ff6f00 -->
            <li>즉시</li>
            <!-- #f44336 -->
          </ul>
        </td>
        <th>시작일</th>
        <td @click="isPick(`start`)">{{ inputStart }}</td>
      </tr>
      <tr>
        <th>담장자</th>
        <td>{{ userId }}</td>
        <th>종료일</th>
        <td @click="isPick(`end`)">{{ inputEnd }}</td>
      </tr>
      <tr>
        <th>제목</th>
      </tr>
      <tr>
        <td colspan="4"><input type="text" v-model="inputTitle" /></td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4"><input type="text" v-model="inputContent" /></td>
      </tr>
    </table>
    <vue-cal
      locale="ko"
      class="vuecal--date-picker"
      xsmall
      hide-view-selector
      :time="false"
      :transitions="true"
      active-view="month"
      :disable-views="['years', 'year', 'week', 'day']"
      @cell-click="pickDate($event)"
      v-if="showCal"
    >
    </vue-cal>
  </div>
</template>

<script>
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import { mapMutations, mapState } from "vuex";
import moment from "moment";

export default {
  data() {
    return {
      inputPriority: "낮음",
      inputState: "신규",
      inputProgress: "",
      isActive: false,
      showCal: false,
      startOrEnd: "",
      inputContent: "",
      inputTitle: "",
      inputStart: "",
      inputEnd: "",
    };
  },
  components: {
    VueCal,
  },
  computed: mapState({
    userId: () => sessionStorage.getItem("memId"),
  }),
  methods: {
    ...mapMutations({
      insert: "gantt/insert",
    }),
    pickDate(data) {
      let today = moment().format("YYYY-MM-DD");
      let nowTime = moment().format("HH:mm:ss");

      let todayWithOutTime = moment().format("YYYY-MM-DD");
      let selectDate = moment(data.format("YYYY-MM-DD"))._i;

      let temp = `${selectDate} ${nowTime}`;

      selectDate = moment(temp, "YYYY-MM-DD");

      if (
        selectDate.from(today).split(" ")[0] !== "in" &&
        selectDate._i !== todayWithOutTime
      ) {
        let target = document.querySelector(
          ".vuecal__cell--selected .vuecal__cell-content"
        );
        target.style.background = "red";
        setTimeout(() => {
          target.style.background = "none";
        }, 1000);
        clearTimeout();
        return;
      } else {
        selectDate = selectDate._i.split(" ")[0];
        this.showCal = !this.showCal;

        switch (this.startOrEnd) {
          case "start":
            this.inputStart = selectDate;
            break;
          case "end":
            this.inputEnd = selectDate;
            break;
        }
      }
    },
    isPick(position) {
      this.showCal = true;
      switch (position) {
        case "start":
          this.startOrEnd = "start";
          break;
        case "end":
          this.startOrEnd = "end";
          break;
      }
    },
    addTask() {
      let today = moment().format("YYYY-MM-DD").split("-");

      let payload = [];

      let start = this.inputStart.split("-");
      let end = this.inputEnd.split("-");

      payload.push(today[1]);
      payload.push({
        memId: this.userId,
        title: this.inputTitle,
        content: this.inputContent,
        start: start[2],
        end: end[2],
        state: this.inputState,
        priority: this.inputPriority,
        progress: this.inputProgress,
      });
      payload.push({
        start: {
          year: start[0],
          month: start[1],
        },
        end: {
          year: end[0],
          month: end[1],
        },
      });

      this.insert(payload);
    },
    selectPriority(e) {
      this.inputPriority = e.target.innerHTML;
      this.isActive = false;
    },
    showSelectBox() {
      this.isActive = !this.isActive;
    },
  },
};
</script>

<style scoped>
.write-container {
  border-radius: 25px;
  height: calc(30vh - 40px);
  width: 95%;
  margin: 20px 0 0 20px;
  padding: 20px;
  background: #2c2f3b;
  position: relative;
}

.vuecal--date-picker {
  position: absolute;
  top: 5%;
  right: 10%;
  box-shadow: 0px 3px 35px rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  width: 210px;
  height: 230px;
  overflow: hidden;
}

.vuecal--date-picker ::-webkit-scrollbar {
  width: 0px;
}

.write-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.write-title {
  font-size: 22px;
}

.write-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.write-btn {
  background: #ff8906;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  -webkit-filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
}

.write-table {
  border: none;
  width: 90%;
  text-align: left;
  height: 80%;
}

.write-table input {
  width: 110px;
  background: none;
  border: none;
  outline: none;
  color: #fff;
}

.write-table th,
td {
  width: 30px;
}

.write-des-text {
}

.write-item-progress {
  right: 0;
  display: inline-block;
  height: 20px;
  max-width: 100%;
  background: #fff;
}

.write-item-progress-fill {
  height: 20px;
  width: 50%;
  background: #3f80a9;
}

.select {
  position: relative;
  cursor: pointer;
}

.select-list {
  position: absolute;
  top: 45%;
  bottom: -55%;
  transition: all 1s ease-out;
  background: #2c2f3b;
}

.select-list :nth-child(1) {
  color: #4caf50;
}
.select-list :nth-child(2) {
  color: #0091ff;
}
.select-list :nth-child(3) {
  color: #ffbf00;
}
.select-list :nth-child(4) {
  color: #ff6f00;
}
.select-list :nth-child(5) {
  color: #f44336;
}

.hide {
  display: none;
}

.show {
  display: block;
}
</style>