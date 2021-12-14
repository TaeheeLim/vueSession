<template>
  <div class="side-bar-container">
    <div class="back-tab">
      <h2 class="title">
        <img src="../../../../../assets/documentIcon.png" alt="/">
        <router-link to="/pdtail/compiler/backend">JAVA</router-link>
        <button class="add-java-btn" type="button">+</button>
      </h2>
      <div class="directory">
        <Tree :nodes="dataObj"/>
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
import compileData from "../../../../../assets/compiler.json"
import arrayToTree from 'array-to-tree'

export default {
  name: "sideBar",
  components: {
    Tree
  },
  data() {
    return {
      dataObj : arrayToTree(compileData, {childrenProperty: 'nodes'}),
    }
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