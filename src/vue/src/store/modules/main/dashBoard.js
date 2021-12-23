import axios from "axios"

axios.defaults.baseURL = "http://localhost:8099"

const dashBoard = {
  namespaced: true,
  state: {
    gantt: [],
    progress: 0,
    memberList: [],
    gitCommit: [],
    issue: [],
    finishDay: 0,
    boardList: [],
    scheduleList: [],
    readMeContent: "",
    projectIdx: 1
  },
  mutations: {
    setReadMeContent(state, contents) {
      state.readMeContent = contents
    }
  },
  actions: {
    saveReadMe(context, contents) {
      const url = "/pdtail/saveReadMe"
      axios.post(url, null,{
        params: {
          prjctIdx: 1,
          prjctReadMe: contents
        }
      })
          .then(res => {
            console.log(res)
            if(res) {
              context.commit("setReadMeContent", contents)
            }
          })
    },
    mountGetData(context) {
      const url = "/pdtail/getData"
      axios.get(url, {
        params: {
          projectIdx: context.state.projectIdx,
          startDate: "2021-01-01 00:11:11",
          endDate: "2021-01-07 00:11:11"
        }
      })
          .then(res => {
            console.log(res)
          })
    }
  }
}

export default dashBoard