const moment = require("moment");

const kanban = {
  namespaced: true,
  state: {
    kanban: {
      type: "container",
      props: {
        orientation: "horizontal",
      },
      columns: [
        {
          id: 0,
          type: "container",
          name: "화면 미 구현",
          showInput: false,
          props: {
            orientation: "vertical",
          },
          cards: [
            {
              content: "프로젝트 리스트 퍼블리싱",
              id: "0-1",
              startDate: "2021-12-07 15:00:10",
              endDate: "2021-12-08 15:30:30",
              day: "",
              badgeText: "front-end",
              badgeColor: "#4caf50",
              showCardInMenu: false,
              user_name: "태희",
            },
          ],
        },
        {
          id: 1,
          type: "container",
          name: "화면 구현 완료",
          showInput: false,
          props: {
            orientation: "vertical",
          },
          cards: [
            {
              content: "간트차트 퍼블리싱",
              id: "1-1",
              startDate: "2021-12-06 15:30:30",
              endDate: "2021-12-13 15:30:30",
              day: "",
              badgeText: "implement",
              badgeColor: "#4caffd",
              showCardInMenu: false,
              user_name: "zerochae",
            },
          ],
        },
        {
          id: 2,
          type: "container",
          name: "API 개발 전",
          showInput: false,
          props: {
            orientation: "vertical",
          },
          cards: [
            {
              content: "컴파일러 기능 구현",
              id: "2-1",
              startDate: "2021-12-05 15:30:30",
              endDate: "2021-12-14 15:30:30",
              badgeText: "back-end",
              badgeColor: "#FFC107",
              day: "",
              showCardInMenu: false,
              user_name: "kade",
            },
          ],
        },
        {
          id: 3,
          type: "container",
          name: "API 개발 중",
          showInput: false,
          props: {
            orientation: "vertical",
          },
          cards: [
            {
              content: "스케줄러 퍼블리싱",
              id: "3-1",
              startDate: "2021-12-04 15:30:30",
              endDate: "2021-12-12 15:30:30",
              badgeText: "complete",
              badgeColor: "#FF9800",
              day: "",
              showCardInMenu: false,
              user_name: "주원",
            },
          ],
        },
        {
          id: 4,
          type: "container",
          name: "API 개발 완료",
          showInput: false,
          props: {
            orientation: "vertical",
          },
          cards: [
            {
              content: "프로젝트 세팅 퍼블리싱",
              id: "4-1",
              startDate: "2021-12-07 14:35:43",
              endDate: "2021-12-12 15:30:30",
              badgeText: "Emergency",
              badgeColor: "#F44336",
              day: "",
              showCardInMenu: false,
              user_name: "창균",
            },
          ],
        },
      ],
    },
    nowOpen: [-1, -1],
    showCal: false,
    showAddForm: false,
    addIndex: "",
    inputBadge: "",
    inputContent: "",
    inputDate: "",
  },
  mutations: {
    showCardMenu(state, payload) {
      let i = payload[0];
      let j = payload[1];

      // if (k > -1 && !(i == k && j == m)) {
      //   state.kanban.columns[k].cards[m].showCardInMenu = false;
      // }

      state.kanban.columns[i].cards[j].showCardInMenu =
        !state.kanban.columns[i].cards[j].showCardInMenu;
      state.nowOpen = [i, j];
    },
    showAdd(state, payload) {
      state.addIndex = payload;
      state.showAddForm = true;
    },
    showCalendar(state) {
      state.showCal = !state.showCal;
    },
    closeAdd(state) {
      state.showAddForm = false;
      state.showCal = false;
    },
    delete(state, payload) {
      let i = payload[0];
      let j = payload[1];
      let copyArr = [...state.kanban.columns[i].cards];
      copyArr.splice(j, 1);
      state.kanban.columns[i].cards = copyArr;
    },
    update(state, payload) {
      let i = payload[0];
      let j = payload[1];
      console.log(i, j);
      let target = state.kanban.columns[i].cards[j];
      console.log(target);
      state.showAddForm = true;
    },
    setDays(state, payload) {
      let day = payload[0];
      let i = payload[1];
      let j = payload[2];

      state.kanban.columns[i].cards[j].day = day;
    },

    add(state, payload) {
      let index = payload[0];
      let badge = payload[1];
      let color = payload[2];
      let endDay = payload[3];
      let content = payload[4];
      let userId = payload[5];

      if (userId === undefined) {
        userId = "zerochae";
      }

      let id = state.kanban.columns[index].cards.length;
      let today = moment().format("YYYY-MM-DD HH:mm:ss");
      let startDay = moment(today, "YYYY-MM-DD HH:mm:ss");

      let date = "";

      let registrationTime = startDay.from(today).split(" ");

      date +=
        registrationTime[0] === "a" || registrationTime[0] === "an"
          ? 1
          : registrationTime[0];

      switch (registrationTime[1]) {
        case "few":
          date = "now";
          break;
        case "minute":
        case "minutes":
          date += "분 전";
          break;
        case "hour":
        case "hours":
          date += "시간 전";
          break;
        case "day":
        case "days":
          date += "일 전";
          break;
        case "month":
        case "months":
          date += "달 전";
          break;
      }

      let d_day = "D";

      let day = startDay.from(endDay).split(" ");

      console.log(startDay, endDay);

      if (day[day.length - 1] === "ago") {
        if (day[1] === "day" || day[1] === "days") {
          if (day[0] === "a" || day[0] === "an") {
            d_day += "-1";
          } else {
            d_day += `-${day[0]}`;
          }
        } else {
          d_day += "-1"; // 내일인데 24시간 안지났으면 이거 나와
        }
      } else {
        if (day[2] === "day" || day[2] === "days") {
          if (day[1] === "a" || day[1] === "an") {
            d_day += "+1";
          } else {
            d_day += `+${day[1]}`;
          }
        } else {
          d_day = "D-Day"; // 오늘 날짜 선택하면 이거 나와
        }
      }

      state.kanban.columns[payload[0]].cards.push({
        content: content,
        id: `${payload[0]}-${id}`,
        startDate: today,
        endDate: endDay,
        day: `${date} (${d_day})`,
        badgeText: badge,
        badgeColor: color,
        showCardInMenu: false,
        user_name: userId,
      });
    },
    // updateCard(state, payload) {},
  },
  actions: {},
};

export default kanban;