import axios from "axios";

const javaCompile = {
  namespaced: true,
  state: {
    isOpen: false,
    name: "",
    type: ['Directory', 'File'],
    path: [
      {
        name: "src",
        path: "/src"
      }
    ],
    classification: ['JAVA', 'HTML'],
    requestData: {
      name: "",
      type: "Directory",
      path: {
        name: "src",
        path: "/src"
      },
      classification: "JAVA",
      projectIdx: 1
    }
  },
  mutations: {
    openAddModal(state) {
      state.isOpen = !state.isOpen
    },
  },
  actions: {
    createDirOrFile(context) {
      context.commit("openAddModal")

      const obj = context.state.requestData
      const params = new URLSearchParams();
      params.append("name", obj.name)
      params.append("path", obj.path.path)
      params.append("type", obj.type)
      params.append("classification", obj.classification)
      params.append("projectIdx", obj.projectIdx)

      const url = "/pdtail/addDirOrFile"
      axios.post(url, params)
          .then(res => {
            console.log(res)
          })
    }

  }
}

export default javaCompile