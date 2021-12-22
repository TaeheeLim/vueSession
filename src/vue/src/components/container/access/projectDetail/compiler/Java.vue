<template>
  <div class="java-container">
    <Editor/>
    <Result/>
  </div>
</template>

<script>
import Editor from '../../../../component/acess/projectDetail/compiler/java/Editor.vue'
import Result from '../../../../component/acess/projectDetail/compiler/java/Result.vue'
import {mapActions, mapMutations, mapState} from "vuex";

export default {
  name: "javaCompiler",
  computed: {
    ...mapState({
      projectIdx: state => state.global.projectIdx
    })
  },
  data() {
    return {
    }
  },
  components: {
    Editor,
    Result
  },
  methods: {
    ...mapMutations({}),
    ...mapActions({}),
    getJavaCompiler(projectIdx) {
      this.axios({
        url: '/compiler/getData',
        method: 'post',
        data: {
          projectIdx: projectIdx
        }
      }).then(res => {
        console.log(res)
      }).catch(err => {
        console.log(err)
      })
    }
  },
  mounted() {
    this.getJavaCompiler(this.projectIdx)
  }
}
</script>

<style scoped>
.java-container {
  width: 85%;
  display: flex;
  justify-content: space-between;
}

.java-container > * {
  width: 50%;
  border: 1px solid #999;
}
</style>