<template>
  <div class="chart-container">
    <button @click="prevMonth">이전달</button>
    <button @click="nextMonth"> 다음달 </button>
    {{month}}
    <ul class="chart-date">
      <li v-for="day in date" :key="day">
        {{ day }}
      </li>
    </ul>
    <ul class="chart-bars">
      <li
        v-for="(task, index) in chart.tasks[month]"
        :key="index"
        @click="showInfo(index)"
      >
        <span
          :style="{ background: task.priority, width: `${task.progress}%` }"
        >
        </span>
        <p>{{ task.title }}</p>
      </li>
    </ul>
    <div
      class="todayLine"
      :style="{
        left: `${todayLineOffsetLeft}px`,
        top: `${todayLineOffsetTop}px`,
      }"
      v-if="isToday"
    ></div>
  </div>
</template>

<script>
import moment from "moment";
import { mapMutations , mapState } from "vuex";

export default {
  name: "chart",
  data() {
    return {
      ganttData: "",
      date: [],
      month: "",
      isToday: "true",
      todayLineOffsetLeft: 0,
      todayLineOffsetTop: 0,
    };
  },
  computed: mapState({
    chart : state => state.gantt.chart
  }),
  created() {
    this.dateRender();
  },
  mounted() {
    this.createChart();
  },
  updated() {
    this.createChart();
  },
  methods: {
    ...mapMutations({
      select: "gantt/select",
    }),
    dateRender() {
      this.date = [];

      let today = moment().format("YYYY-MM-DD").split("-");
      if (this.month === "") {
        this.month = today[1];
      }

      let lastDay = new Date(today[0], this.month, 0).getDate();

      for (let day = 1; day < lastDay + 1; day++) {
        if (day < 10) {
          day = `0${day}`;
        }
        this.date.push(`${day}`);
      }
    },
    createChart() {
      let today = moment().format("YYYY-MM-DD").split("-");

      if(today[1] == this.month){
        this.isToday = true;
      } else {
        this.isToday = false;
      }

      this.chart.tasks[this.month];

      let days = document.querySelectorAll(".chart-date li");
      let tasks = document.querySelectorAll(".chart-bars li");
      days = Array.from(days);
      tasks = Array.from(tasks);

      let left = 0,
        width = 0,
        f_arr = [];

      f_arr = days.filter((day) => day.textContent === today[2]);

      this.todayLineOffsetLeft = f_arr[0].offsetLeft + 28;
      this.todayLineOffsetTop = f_arr[0].offsetTop + 30;

      tasks.forEach((el, index) => {
        console.log(el,index)
        let start = this.chart.tasks[this.month][index].start;

        f_arr = days.filter((day) => day.textContent === start);
        left = f_arr[0].offsetLeft - 19;

        let end = this.chart.tasks[this.month][index].end;

        f_arr = days.filter((day) => day.textContent === end);
        width = f_arr[0].offsetLeft + f_arr[0].offsetWidth - left - 19;

        el.style.left = `${left}px`;
        el.style.width = `${width}px`;

        el.style.opacity = 1;
      });
    },
    showInfo(index) {

      let payload = []
      payload.push(this.month,index,this.month)

      this.select(payload);
    },
    prevMonth() {
      for (let key of Object.keys(this.chart.tasks)) {
        if(this.month-1 == key){
          this.month--;
          this.dateRender();
          return;
        } 
      }
    },
    nextMonth() {
      for (let key of Object.keys(this.chart.tasks)) {
        if(this.month+1 == key){
          this.month++;
          this.dateRender();
          return;
        } 
      }
    },
  },
};
</script>

<style scoped>
.chart-container {
  color: white;
  height: calc(60vh - 70px);
  border-radius: 25px;
  background: #2c2f3b;
  padding: 20px;
  position: relative;
  overflow: scroll;
  overflow-x: hidden;
  margin: 20px;
}
.chart-date {
  display: flex;
  margin: 0 0 20px 0;
  font-weight: bold;
  font-size: 1.2rem;
}

.chart-date li {
  flex: 1;
  min-width: 19px;
  text-align: center;
}

.chart-date li:not(:last-child) {
  position: relative;
}
.chart-date li:not(:last-child):before {
  position: absolute;
  content: "";
  right: 0;
  height: 100%;
  border-right: 1px solid #fff;
}

.chart-bars li {
  z-index: 2;
  position: relative;
  color: black;
  margin-bottom: 15px;
  font-size: 16px;
  border-radius: 20px;
  width: 0;
  opacity: 0;
  height: 30px;
  background: #eee;
  transition: all 0.6s linear 0.2s;
  overflow: hidden;
  display: flex;
  -webkit-filter: drop-shadow(0px 10px 10px rgba(10, 10, 10, 0.8));
}

.chart-bars li img {
  border-radius: 50%;
  width: 20px;
  margin-bottom: 7px;
  z-index: 1;
}

.chart-bars li span {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}

.chart-bars li p {
  position: absolute;
  top: 15%;
  margin-left: 15px;
  left: 0;
}

.todayLine {
  top: 0;
  position: absolute;
  height: 100%;
  border-right: 1px solid red;
  opacity: 0.5;
}

::-webkit-scrollbar {
  width: 0px;
}
</style>