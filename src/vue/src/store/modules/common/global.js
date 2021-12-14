const global = {
  namespaced: true,
  state: {
    isLogin: false,
    chatOn: true
  },
  mutations: {
    setIsLogin(state, value) {
      state.isLogin = value
    }
  },
  actions: {
    
  }
}

export default global