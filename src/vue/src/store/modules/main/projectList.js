const projectList = {
  namespaced: true,
  state: {
    IsModalOpen : false,
  },
  mutations: {
    changeIsModalOpen(state){
      state.IsModalOpen = !state.IsModalOpen
    },
    closeModal(state, e){
      console.log(e.target.classList)
      for(let i in e.target.classList){
        if(e.target.classList[i] === 'bg-container' || e.target.classList[i] === 'fas'){
          state.IsModalOpen = false
        } else {
          return
        }
      }
    },
  },

  actions: {
    
  }
}

export default projectList