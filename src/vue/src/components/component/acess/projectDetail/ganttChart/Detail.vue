<template>
  <div class="detail-container">
    <div class="detail-header">
      <span v-if="!isUpdate" class="detail-title">일감 상세</span>
      <span v-else-if="isUpdate" class="detail-title">일감 수정</span>
      <span class="button-zone" v-if="detail != '' && !isUpdate">
        <button @click="isUpdate = true" class="update-btn btn">수정</button>
        <button @click="deleteLine" class="delete-btn btn">삭제</button>
      </span>
      <span class="button-zone" v-if="isUpdate">
        <button @click="completeModify" class="update-btn btn">완료</button>
        <button @click="cancelModify" class="delete-btn btn">취소</button>
      </span>
    </div>
    <hr class="detail-line" />

    <!-- 선택 안했을때 (detail == null) 보여질 Element 시작 -->
    <table v-if="detail == ''" class="detail-table">
      <tr>
        <td>상단에서 일감을 선택해주세요</td>
      </tr>
    </table>
    <!-- 선택 안했을때 (detail == null) 보여질 Element 끝 -->

    <!-- 선택 했을때 (detail != null) 보여질 Element 시작 -->
    <table v-else-if="detail != '' && !isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td>{{ detail.state }}</td>
        <th>진척도</th>
        <td class="detail-progress">
          <div class="detail-item-progress">
            <div
              :style="{
                background: setColor(detail.priority),
                width: `${detail.progress}%`,
              }"
              class="detail-item-progress-fill"
            ></div>
          </div>
          <span class="detail-item-progress-percent"
            >{{ detail.progress }}%</span
          >
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td>{{ detail.priority }}</td>
        <th>시작일</th>
        <td>{{ openYear }}-{{ openMonth }}-{{ detail.start }}</td>
      </tr>
      <tr>
        <th>담장자</th>
        <td>{{ detail.memId }}</td>
        <th>마감임</th>
        <td>{{ openYear }}-{{ openMonth }}-{{ detail.end }}</td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4">{{ detail.content }}</td>
      </tr>
    </table>
    <!-- 선택 했을때 (detail != null) 보여질 Element 끝 -->

    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 시작 -->
    <table v-else-if="isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td>
          <input
            type="text"
            :v-model="inputState"
            :placeholder="inputState == '' ? detail.state : inputState"
          />
        </td>
        <th>진척도</th>
        <td>
          <input
            type="text"
            :v-model="inputProgress"
            :placeholder="inputProgress == '' ? detail.progress : inputProgress"
          />
        </td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td class="select">
          <span @click="showSelectBox">{{
            inputPriority == "" ? detail.priority : inputPriority
          }}</span>
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
        <td @click="isPick(`start`)">
          {{
            inputStart == ""
              ? `${openYear}-${openMonth}-${detail.start}`
              : inputStart
          }}
        </td>
      </tr>
      <tr>
        <th>담장자</th>
        <td>{{ detail.memId }}</td>
        <th>종료일</th>
        <td @click="isPick(`end`)">
          {{
            inputEnd == ""
              ? `${openYear}-${openMonth}-${detail.end}`
              : inputEnd
          }}
        </td>
      </tr>
      <tr>
        <th>제목</th>
      </tr>
      <tr>
        <td colspan="4">
          <input
            type="text"
            :v-model="inputTitle"
            :placeholder="inputTitle == '' ? detail.title : inputTitle"
          />
        </td>
      </tr>
      <tr>
        <th>설명</th>
      </tr>
      <tr>
        <td colspan="4">
          <input
            type="text"
            :v-model="inputContent"
            :placeholder="inputContent == '' ? detail.content : inputContent"
          />
        </td>
      </tr>
    </table>
    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 끝 -->
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
import { mapState, mapMutations } from "vuex";
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import moment from "moment";

export default {
  data() {
    return {
      isUpdate: false,
      inputPriority: "",
      inputState: "",
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
    detail: (state) => state.gantt.detail,
    openYear: (state) => state.gantt.openYear,
    openMonth: (state) => state.gantt.openMonth,
    userId: () => sessionStorage.getItem("memId"),
  }),
  methods: {
    ...mapMutations({
      update: "gantt/update",
      delete: "gantt/delete",
    }),
    deleteLine() {
      this.delete();
    },
    completeModify() {
      this.isUpdate = true;

      let start,end;

      if(this.inputStart !== ''){
        start = this.inputStart.split("-");
        payload.push({
          start :{
            year : start[0],
            month : start[1]
          }
        })
      }

      if(this.inputEnd !== ''){
        end = this.inputEnd.split("-");
        payload.push({
          end : {
            year : end[0],
            month : end[1],
          }
        })
      }


      let payload = [];

      payload.push(
        {
        memId: this.userId,
        title: this.inputTitle == "" ? this.detail.title : this.inputTitle,
        content: this.inputContent == "" ? this.detail.content : this.inputContent,
        start: this.inputStart == "" ? this.detail.start : start[2],
        end: this.inputEnd == "" ? this.detail.end : end[2],
        state: this.inputState == "" ? this.detail.state : this.inputState,
        priority: this.inputPriority == "" ? this.detail.priority : this.inputPriority,
        progress: this.inputProgress == "" ? this.detail.progress : this.inputProgress,
      }
      );

      this.update(payload);
    },
    cancelModify() {
      this.isUpdate = false;
    },
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
    showSelectBox() {
      this.isActive = !this.isActive;
    },
    selectPriority(e) {
      this.inputPriority = e.target.innerHTML;
      this.isActive = false;
    },
    setColor(str) {
      switch (str) {
        case "낮음":
          return "#4caf50";
        case "보통":
          return "#0091ff";
        case "높음":
          return "#ffbf00";
        case "긴급":
          return "#ff6f00";
        case "즉시":
          return "#f44336";
      }
    },
  },
};
</script>

<style scoped>
.detail-container {
  border-radius: 25px;
  height: calc(30vh - 40px);
  width: 95%;
  margin: 20px 20px 0 0;
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


.detail-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.detail-title {
  font-size: 22px;
}
.detail-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.update-btn {
  background: #ff8906;
}

.delete-btn {
  background: #ff5c5c;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  -webkit-filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
}

.detail-table {
  border: none;
  width: 90%;
  text-align: left;
  height: 80%;
  transition: all 0.6s linear 0.2s;
  animation: show;
}

.detail-table th,
td {
  width: 30px;
}

.detail-table input {
  border: none;
  outline: none;
  background: none;
  color: #ff8906;
  width: 100px;
}

.detail-progress {
  position: relative;
}

.detail-item-progress {
  height: 20px;
  max-width: 60%;
  background: #414556;
}

.detail-item-progress-fill {
  height: 20px;
  width: 50%;
  background: #3f80a9;
}

.detail-item-progress-percent {
  position: absolute;
  top: 0;
  right: 10%;
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

@keyframes show {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>