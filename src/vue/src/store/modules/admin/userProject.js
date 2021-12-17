const userProject = {
  namespaced: true,
  state: {
    projectData: [
      {
        id: "1",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "50",
        isDel: "",
        isCom: "완료",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "2",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "35",
        isDel: "삭제",
        isCom: "완료",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "3",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "46",
        isDel: "",
        isCom: "진행",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "4",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "삭제",
        isCom: "완료",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "5",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "삭제",
        isCom: "완료",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "6",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "",
        isCom: "진행",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "7",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "",
        isCom: "진행",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "8",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "",
        isCom: "완료",
        start: "2021-01-01",
        end: "2021-01-02",
      },
      {
        id: "9",
        name: "깐부우우우",
        memId: "안대그은",
        progress: "15",
        isDel: "삭제",
        isCom: "진행",
        start: "2021-01-01",
        end: "2021-01-02",
      },
    ],
  },
  mutations: {
    update(state, payload) {
      let target = state.projectData.find( data => data.id == payload[0]);
      if (payload[1] == "삭제") {
        target.isDel = payload[1];
      } else {
        target.isDel = "";
        target.isCom = payload[1];
      }
    },
  },
  actions: {},
};

export default userProject;
