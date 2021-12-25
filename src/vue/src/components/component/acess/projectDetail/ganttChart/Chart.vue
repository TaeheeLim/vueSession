<template>
  <div class="chart-container">
    <div class="date-container">
      <div class="chart-header">
        <ChevronLeftIcon class="icons" @click="prevMonth" />
        <span class="monthName">{{ monthName() }}</span>
        <ChevronRightIcon class="icons" @click="nextMonth" />
      </div>
      <ul class="chart-date">
        <li v-for="day in date" :key="day">
          {{ day }}
        </li>
      </ul>
    </div>
    <ul class="chart-bars">
      <li
          v-for="(task, index) in chart.tasks[year][month]"
          :key="index"
          @click="showInfo(index)"
      >
        <span
            :style="{
            background: setColor(task.priority),
            width: `${task.progress}%`,
          }"
        >
        </span>
        <img src="@/assets/con1.jpg" />
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
import { mapActions, mapMutations, mapState } from "vuex";
import { ChevronLeftIcon, ChevronRightIcon } from "@heroicons/vue/outline";

export default {
  name: "chart",
  data() {
    return {
      date: [],
      year: "",
      month: "",
      isToday: "true",
      first: true,
      todayLineOffsetLeft: 0,
      todayLineOffsetTop: 0,
    };
  },
  components: {
    ChevronLeftIcon,
    ChevronRightIcon,
  },
  computed: {
    ...mapState({
      chart: (state) => state.gantt.chart,
    }),
  },
  created() {
    this.getGanttData()
    this.renderDate();
    this.month = new Date().getMonth() + 1;

  },
  mounted() {
    if (this.first) {
      this.renderChart();
      this.renderTodayLine();
    }


    window.onresize = () => {
      this.renderChart();
    };
  },
  updated() {
    !this.first ? this.renderChart() : (this.first = false);
  },
  methods: {
    ...mapMutations({
      select: "gantt/select",
    }),
    ...mapActions({
      getGanttData: "gantt/getGanttData"
    }),
    renderTodayLine() {
      let today = moment().format("YYYY-MM-DD").split("-");

      if (today[1] == this.month) {
        this.isToday = true;
      } else {
        this.isToday = false;
      }

      let days = document.querySelectorAll(".chart-date li");
      days = Array.from(days);

      let f_arr = days.filter((day) => day.textContent === today[2]);

      this.todayLineOffsetLeft = f_arr[0].offsetLeft;
      this.todayLineOffsetTop = f_arr[0].offsetTop + 50;
    },
    renderDate() {
      this.date = [];

      let today = moment().format("YYYY-MM-DD").split("-");

      if (this.year === "") {
        this.year = today[0];
      }

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
    renderChart() {
      console.log("차트돈다");

      let days = document.querySelectorAll(".chart-date li");
      let tasks = document.querySelectorAll(".chart-bars li");
      days = Array.from(days);
      tasks = Array.from(tasks);

      let left = 0,
          width = 0,
          f_arr = [];

      tasks.forEach((el, index) => {
        let start = this.chart.tasks[this.year][this.month][index].start;

        f_arr = days.filter((day) => day.textContent === start);
        left = f_arr[0].offsetLeft - 35;

        let end = this.chart.tasks[this.year][this.month][index].end;

        f_arr = days.filter((day) => day.textContent === end);
        width = f_arr[0].offsetLeft + f_arr[0].offsetWidth - left - 35;

        el.style.left = `${left}px`;
        el.style.width = `${width}px`;

        el.style.opacity = 1;
      });
    },
    showInfo(index) {
      let payload = [];
      payload.push(this.month, index, this.month, this.year);
      this.select(payload);
    },
    prevMonth() {
      for (let key of Object.keys(this.chart.tasks[this.year])) {
        if (this.month - 1 == key) {
          this.month--;
          this.renderDate();
          return;
        }
      }
    },
    nextMonth() {
      for (let key of Object.keys(this.chart.tasks[this.year])) {
        if (this.month + 1 == key) {
          this.month++;
          this.renderDate();
          return;
        }
      }
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
    monthName() {
      switch (this.month) {
        case 1:
          return "January";
        case 2:
          return "February";
        case 3:
          return "March";
        case 4:
          return "April";
        case 5:
          return "May";
        case 6:
          return "June";
        case 7:
          return "July";
        case 8:
          return "August";
        case 9:
          return "September";
        case 10:
          return "October";
        case 11:
          return "November";
        case 12:
          return "December";
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
  overflow: hidden;
  width: 100%;
  box-shadow: 0px 10px 25px rgba(255, 255, 255, 0.2) inset;
}

.chart-header {
  height: 60px;
  width: 100%;
  display: flex;
  justify-content: space-around;
}

.monthName {
  align-self: center;
  font-size: 26px;
}

.icons {
  width: 40px;
}

.date-container {
  position: absolute;
  width: 100%;
  left: 0;
  top: 5px;
  padding: 0 20px;
}

.chart-date {
  display: flex;
  margin: 0 0 20px 0;
  font-weight: bold;
  font-size: 1.2rem;
  margin-top: 10px;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
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

.chart-bars {
  position: relative;
  top: 20%;
  height: 85%;
  overflow: scroll;
  padding: 0 20px;
}

.chart-bars li {
  z-index: 2;
  position: relative;
  color: black;
  margin-bottom: 15px;
  font-size: 12px;
  border-radius: 20px;
  width: 0;
  opacity: 0;
  height: 50px;
  background: #eee;
  transition: all 0.6s linear 0.2s;
  overflow: hidden;
  display: flex;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
  font-weight: bold;
}

.chart-bars li img {
  border-radius: 50%;
  z-index: 1;
  padding: 5px;
}

.chart-bars li span {
  box-shadow: 3px 6px 25px rgba(255, 255, 255, 0.5) inset;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  transition: all 0.6s linear 0.2s;
}

.chart-bars li p {
  position: absolute;
  top: 35%;
  margin-left: 50px;
  left: 0;
}

.todayLine {
  top: 0;
  position: absolute;
  height: 100vh;
  border-right: 1px solid red;
  opacity: 0.5;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
}

::-webkit-scrollbar {
  width: 0px;
}
</style>