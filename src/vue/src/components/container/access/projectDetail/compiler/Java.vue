<template>
  <div class="java-container">
    <Editor/>
    <Result/>
  </div>
</template>

<script>
import Editor from '../../../../component/acess/projectDetail/compiler/java/Editor.vue'
import Result from '../../../../component/acess/projectDetail/compiler/java/Result.vue'

export default {
  name: "javaCompiler",
  components: {
    Editor,
    Result
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