<template>
  <div class="container">
    <h2>README.md<span> - {{lastModifyBy}}</span></h2>
    <v-md-editor v-model="contents" 
                  height="480px"/>
  </div>
</template>

<script>
export default {
  name: "readme",
  data() {
    return {
      lastModifyBy: "kade",
      contents: "Hello",
    }
  }
}
</script>

<style scoped>
.container {
  border-radius: 5px;
  background: #2C2F3B;
  padding: 20px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

h2 span {
  font-size: 24px;
}

</style>