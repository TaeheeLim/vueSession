<template>
  <div class="result-container">
    <div class="result" v-text="result" style="color: #fff;"></div>
  </div>
</template>

<script>
export default {
  name: "Result",
  data() {
    return {
      result: ""
    }
  },
  watch: {
    "$store.state.javaCompile.result"(e) {
      this.result = e
    }
  }
}
</script>

<style scoped>
.result-container {
  padding: 10px;
}
</style>