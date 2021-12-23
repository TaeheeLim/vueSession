<template>
  <div class="side-bar-container">
    <div class="back-tab">
      <h2 class="title">
        <img src="../../../../../assets/documentIcon.png" alt="/">
        <router-link to="/pdtail/compiler/backend">JAVA</router-link>
        <button class="add-java-btn" type="button" @click="openAddModal">+</button>
      </h2>
      <div class="directory">
        <Tree :nodes='dataObj'/>
      </div>
    </div>

    <div class="front-tab">
      <h2 class="title">
        <img src="../../../../../assets/documentIcon.png" alt="/">
        <router-link to="/pdtail/compiler/frontend">Front</router-link>
        <button class="add-front-btn" type="button">+</button>
      </h2>
      <ul class="directory">
        <li>Test.html</li>
      </ul>
    </div>
  </div>
</template>

<script>
import Tree from 'vue3-tree'
import "vue3-tree/dist/style.css";
import {mapState, mapMutations} from "vuex";
import axios from "axios";
import arrayToTree from "array-to-tree";
import {ref} from 'vue'

export default {
  name: "sideBar",
  components: {
    Tree
  },
  computed: {
    ...mapState({
      projectIdx: state => state.global.projectIdx,
    })
  },
  data() {
    return {
      dataObj: ref([])
    }
  },
  methods: {
    ...mapMutations({
      openAddModal: 'javaCompile/openAddModal'
    })
  },
  mounted() {
    axios({
      url: '/compiler/getData',
      method: 'post',
      data: {
        prjctIdx: this.projectIdx
      }
    }).then(res => {

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
        this.dataObj.push(item)
      })
    })
  },
}
</script>

<style scoped>
.side-bar-container {
  width: 15%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.side-bar-container > * {
  height: 50%;
}

button {
  color: #fff;
}

.title {
  padding: 10px 10px 10px 20px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #999;
  border-top: 1px solid #999;
}

.title img {
  margin-right: 10px;
}

.title button {
  margin-left: auto;
}

.directory {
  height: calc(100% - 46px);
  overflow: scroll;
}

</style>