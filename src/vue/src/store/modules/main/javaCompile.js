import axios from "axios"
import arrayToTree from "array-to-tree";

const javaCompile = {
  namespaced: true,
  state: {
    isOpen: false,
    name: "",
    type: ['Directory', 'File'],
    path: [],
    classification: ['JAVA', 'HTML'],
    requestData: {},
    dataObj: [],
    code: ``,
    clickedFile: {},
    result: ""
  },
  mutations: {
    openAddModal(state) {
      state.requestData = {
        name: "",
        type: 'Directory',
        path: null,
        classification: 'JAVA'
      }


      state.isOpen = !state.isOpen
    },
    addPath(state, arr) {
      const result = []
      arr.forEach(item => {
        if(item.classification === 'directory') {
          result.push(item)
        }
      })
      state.path = result
    },
    getJavaSideBar(state) {
      axios({
        url: '/compiler/getData',
        method: 'post',
        data: {
          prjctIdx: sessionStorage.getItem("project")
        }
      }).then(res => {
        state.dataObj.splice(0, state.dataObj.length)
        const arr = []
        res.data.forEach(node => {
          const obj = {
            id: node.comIdx,
            classification: node.comSe === "d" ? "directory" : "file",
            label: node.comNm,
            parent_id: node.parentComIdx
          }
          arr.push(obj)
        })

        arrayToTree(arr, {childrenProperty: 'nodes'}).forEach(item => {
          state.dataObj.push(item)
        })


        this.commit("javaCompile/addPath", arr)
      })
    },
    setCode(state, item) {
      state.code = item
    },
    setClickedFile(state, item) {
      state.clickedFile = item
    },
  },
  actions: {
    createDirOrFile(context) {
      console.log(context.state.requestData)
      const obj = context.state.requestData
      const params = new URLSearchParams();
      params.append("name", obj.name)
      params.append("path", obj.path.label)
      params.append("type", obj.type)
      params.append("classification", obj.classification)
      params.append("projectIdx", sessionStorage.getItem("project"))
      params.append("comIdx", obj.path.id)

      const url = "/pdtail/addDirOrFile"
      axios.post(url, params)
          .then(() => {
            context.commit("getJavaSideBar")
            context.commit("openAddModal")
          })
    },
    getFileDetail(context, item) {
      if(item.classification === "file") {
        const url = '/compiler/getFile'

        const params = {
          comIdx: item.id,
          project: {
            prjctIdx: sessionStorage.getItem("project")
          }
        }

        axios.post(url, params).then(res => {
          context.commit("setClickedFile", res)
          context.commit('setCode', res.data.comFileCn)
        })
      }
    },
    updateCode(context, item) {
      const url = "/compiler/updateFile"
      const obj = {
        comFileCn: item,
        comFileIdx: context.state.clickedFile.data.comFileIdx,
        compiler: {
          comIdx: context.state.clickedFile.data.compiler.comIdx,
          comNm: context.state.clickedFile.data.compiler.comNm,
          comSe: context.state.clickedFile.data.compiler.comSe,
          parentComIdx: context.state.clickedFile.data.compiler.parentComIdx,
          project: {
            prjctIdx: context.state.clickedFile.data.compiler.project.prjctIdx
          }
        }
      }


      axios.post(url, obj).then(res => {
        res
      })
    },
    runCompile(context) {
      const url = "/compiler/runCompile"
      const obj = {
        prjctIdx: sessionStorage.getItem("project")
      }
      axios.post(url, obj).then(res => {
        context.state.result = res.data
      })
    }
  },
}

export default javaCompile