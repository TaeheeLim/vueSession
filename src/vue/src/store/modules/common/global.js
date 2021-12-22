import router from "@/router";

const global = {
  namespaced: true,
  state: {
    chatOn: true,
    projectIdx: 0,
  },
  mutations: {
    moveToDashBoard(state, project) {
      state.projectIdx = project.prjctIdx
      router.push('/pdtail/dashboard')
    },
    moveToIssue(state, issue) {
      state.projctIdx = issue.project.prjctIdx
      router.push('/pdtail/issue')
    },
    moveToCalendar(state, calendar) {
      state.projctIdx = calendar.project.prjctIdx
      router.push('/pdtail/calendar')
    },
  },
  actions: {
    
  }
}

export default global