<template>
<div class="gitContainer">
  <div class="gitleftDiv">
      <GitRepository class="git"/>
      <Issue class="issue"/>
  </div>
  <div class="gitrightDiv">
      <GitCode/>
  </div>
</div>
</template>

<script>
import GitRepository from '@/components/component/acess/projectDetail/gitAndIssue/Git.vue'
import Issue from '@/components/component/acess/projectDetail/gitAndIssue/Issue.vue'
import GitCode from '@/components/component/acess/projectDetail/gitAndIssue/GitCode.vue'

export default {
    components:{
        GitRepository,
        Issue,
        GitCode,
    },
}
</script>

<style scoped>
.gitContainer{
    padding: 10px 40px;
    display: flex;
    height: 85%;
    justify-content: space-between;
}
.gitleftDiv{
    width: 45%;
}
.gitrightDiv{
    width: 50%;
    height: 100%;
}

h1{
    color: #eee;
}

</style>