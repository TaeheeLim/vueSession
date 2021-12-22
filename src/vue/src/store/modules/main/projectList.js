const projectList = {
  namespaced: true,
  state: {
    IsModalOpen: false,
    projectList: [],
    member: {}
  },
  mutations: {
    changeIsModalOpen(state){
      state.IsModalOpen = !state.IsModalOpen
    },
    closeModal(state, e){
      for(let i in e.target.classList){
        if(e.target.classList[i] === 'bg-container' || e.target.classList[i] === 'fas'){
          state.IsModalOpen = false
        } else {
          return
        }
      }
    },
    pushToProjectList(state, item) {
      state.member = item.projectMemberDtoList[0].member
      item.projectMemberDtoList.forEach(i => {
        state.projectList.push(i.project)
      })
    },
    addCreatedProject(state, item) {
      state.projectList.push(item)
    },
  },

  actions: {
    
  }
}

export default projectList