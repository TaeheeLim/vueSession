import axios from "axios";
import moment from "moment";

const gantt = {
  namespaced: true,
  state: {
    chart: {
      id: "",
      projName: "",
      projStart: "",
      projEnd: "",
      progress: "",
      showCalWrite: false,
      showCalDetail: false,
      tasks: {
        2021: {
          12: [
            {
              memId: "zerochae",
              title: "task1",
              content: "내용1",
              start: "03",
              end: "04",
              state: "Y",
              priority: "낮음",
              progress: "30",
              img: "con1.jpg",
            },
            {
              memId: "juwon",
              title: "task2",
              content: "내용2",
              start: "04",
              end: "05",
              state: "Y",
              priority: "보통",
              progress: "70",
              img: "con1.jpg",
            },
            {
              memId: "daeg",
              title: "task3",
              content: "내용3",
              start: "05",
              end: "06",
              state: "Y",
              priority: "높음",
              progress: "50",
              img: "con1.jpg",
            },
            {
              memId: "th",
              title: "task4",
              content: "내용4",
              start: "07",
              end: "08",
              state: "Y",
              priority: "긴급",
              progress: "10",
              img: "con1.jpg",
            },
            {
              memId: "chang",
              title: "task5",
              content: "내용5",
              start: "10",
              end: "13",
              state: "N",
              priority: "즉시",
              progress: "100",
              img: "con1.jpg",
            },
          ],
          11: [
            {
              memId: "zerochae",
              title: "task1",
              content: "내용1",
              start: "07",
              end: "09",
              state: "Y",
              priority: "낮음",
              progress: "30",
              img: "con1.jpg",
            },
            {
              memId: "juwon",
              title: "task2",
              content: "내용2",
              start: "10",
              end: "11",
              state: "Y",
              priority: "보통",
              progress: "70",
              img: "con1.jpg",
            },
            {
              memId: "daeg",
              title: "task3",
              content: "내용3",
              start: "13",
              end: "15",
              state: "Y",
              priority: "높음",
              progress: "50",
              img: "con1.jpg",
            },
            {
              memId: "th",
              title: "task4",
              content: "내용4",
              start: "18",
              end: "19",
              state: "Y",
              priority: "긴급",
              progress: "10",
              img: "con1.jpg",
            },
            {
              memId: "chang",
              title: "task5",
              content: "내용5",
              start: "21",
              end: "22",
              state: "N",
              priority: "즉시",
              progress: "100",
              img: "con1.jpg",
            },
          ],
          10: [
            {
              memId: "zerochae",
              title: "task1",
              content: "내용1",
              start: "01",
              end: "03",
              state: "Y",
              priority: "낮음",
              progress: "30",
              img: "con1.jpg",
            },
            {
              memId: "juwon",
              title: "task2",
              content: "내용2",
              start: "05",
              end: "08",
              state: "Y",
              priority: "보통",
              progress: "70",
              img: "con1.jpg",
            },
            {
              memId: "daeg",
              title: "task3",
              content: "내용3",
              start: "11",
              end: "14",
              state: "Y",
              priority: "높음",
              progress: "50",
              img: "con1.jpg",
            },
            {
              memId: "th",
              title: "task4",
              content: "내용4",
              start: "11",
              end: "15",
              state: "Y",
              priority: "긴급",
              progress: "10",
              img: "con1.jpg",
            },
            {
              memId: "chang",
              title: "task5",
              content: "내용5",
              start: "13",
              end: "29",
              state: "N",
              priority: "즉시",
              progress: "100",
              img: "con1.jpg",
            },
          ],
        },
      },
    },
    detail: "",
    openYear: "",
    openMonth: "",
    openIndex: "",
  },
  mutations: {
    insert(state, payload) {
      state.chart.tasks[payload[2].start.year][payload[0]].push(payload[1]);


      let today = moment();

      /*

      ganttChart/write.vue

      payload[1] = {

        memId: this.userId,
        title: this.inputTitle,
        content: this.inputContent,
        start: start[2],
        end: end[2],
        state: this.inputState,
        priority: this.inputPriority,
        progress: this.inputProgress == "" ? 0 : this.inputProgress,

      }

      */

      let start = `${today.format("YYYY")}-${today.format("MM")}-${payload[1].start} 00:00:00`;
      let end = `${today.format("YYYY")}-${today.format("MM")}-${payload[1].end} 00:00:00`;

      let ganttDTO = {
        "project.prjctIdx": "1",
        "member.memIdx": "3",
        gtIdx: "",
        gtExplanation: payload[1].content,
        gtPriority: payload[1].priority,
        gtProgress: payload[1].progress,
        gtStartDate: start,
        gtEndDate: end,
        gtState: payload[1].state,
        gtTitle: payload[1].title,
      };

      axios
          .post("/gantt/insertGantt", null, {
            params: ganttDTO,
          })
          .then((result) => {
            console.log(result);
          });
    },
    select(state, payload) {
      state.detail = state.chart.tasks[payload[3]][payload[0]][payload[1]];
      state.openIndex = payload[1];
      state.openMonth = payload[2];
      state.openYear = payload[3];
    },
    update(state, payload) {
      state.chart.tasks[state.openYear][state.openMonth][state.openIndex] =
          state.detail = payload;
    },
    delete(state) {
      state.chart.tasks[state.openYear][state.openMonth].splice(
          state.openIndex,
          1
      );
    },
    calWriteOpen(state) {
      state.showCalWrite = true;
      state.showCalDetail = false;
    },
    calDetailOpen(state) {
      state.showCalWrite = false;
      state.showCalDetail = true;
    },
    calClose(state) {
      state.showCalWrite = false;
      state.showCalDetail = false;
    },
    setGanttData(state, payload) {
      let year = payload[0];
      let month = payload[1];
      state.chart.tasks[year][month].push(payload[2]);
    },
  },
  actions: {
    getGanttData(context) {
      axios
          .get("/gantt/selectGantt", {
            params: {
              projectIdx: "1",
            },
          })
          .then((result) => {
            console.log(result.data);

            result.data.map((item) => {
              let payload = [];
              let date = item.gtStartDate.split("T")[0].split("-");
              let year = date[0];
              let month = date[1];
              let start = date[2];
              let end = item.gtEndDate.split("T")[0].split("-")[2];
              let obj = {
                memId: "zz",
                title: item.gtTitle,
                content: item.gtExplanation,
                start: start,
                end: end,
                state: item.gtState,
                priority: item.gtPriority,
                progress: item.gtProgress,
              };

              payload.push(year, month, obj);

              return context.commit("setGanttData", payload);
            });
          });
    },
  },
};

export default gantt;
