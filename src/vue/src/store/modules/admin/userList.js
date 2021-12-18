import moment from "moment";
import axios from "axios";

const userList = {
  namespaced: true,
  state: {
    userListData: [
      {
        tag: "1",
        id: "kade",
        start: "2021-12-01",
        end: "2021-12-08",
      },
      {
        tag: "2",
        id: "zerochae",
        start: "",
        end: "",
      },
      {
        tag: "3",
        id: "juwon",
        start: "2021-12-05",
        end: "2022-01-05",
      },
      {
        tag: "4",
        id: "daegeun",
        start: "2021-12-01",
        end: "2099-01-03",
      },
      {
        tag: "5",
        id: "changk",
        start: "2021-12-01",
        end: "2021-12-08",
      },
      {
        tag: "6",
        id: "taehee",
        start: "2021-12-01",
        end: "2022-01-01",
      },
    ],
  },
  mutations: {
    update(state, payload) {
      let today = moment().format("YYYY-MM-DD");

      let target = state.userListData.find((data) => data.idx == payload[0]);

      if (payload[1] == "정상") {
        target.start = "";
        target.end = "";
      } else {
        target.start = today;

        switch (payload[1]) {
          case "7일 정지":
            target.end = moment().add(1, "week").format("YYYY-MM-DD");
            break;
          case "30일 정지":
            target.end = moment().add(30, "days").format("YYYY-MM-DD");
            break;
          case "영구 정지":
            target.end = moment().add(300, "years").format("YYYY-MM-DD");
            break;
        }
      }
    },
    setMembers(state, payload) {
      state.userListData = payload;
    },
  },
  actions: {
    setMemberList(context) {
      axios.get("http://localhost:8099/admin/getAllMember").then((result) => {
        let data = [];

        for (let item of result.data) {
          data.push({
            tag: item.memTag,
            id: item.memId,
            memIdx: item.memIdx,
            start: item.ban != null ? item.ban.banStartDate.split("T")[0] : "" ,
            end: item.ban != null ? item.ban.banEndDate.split("T")[0] : "",
          });
        }
        context.commit("setMembers", data);
      });
    },
  },
};

export default userList;
