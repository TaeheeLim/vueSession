<template>
  <div class="issueDiv">
    <h1>Issue</h1>
    <div class="addIssue">
      <input type="text" class="issueTitle" v-model="this.$store.state.git.selectedFileName" readonly size="8">
      <input type="text" class="issueText" size="30">
      <input type="button"  @click=" useAxiosGetData" value="등록">
    </div>
    <hr>
    <div class="Issues">
      <div class="getIssue" v-for="a in $store.state.git.realIssue" :key="a">
        {{a.fileName}} &nbsp; {{a.issueCn}} <br>
        {{a.member.memNick}}&nbsp;{{a.issueDate}}&nbsp;{{a.currentTime}}
        <select v-model="a.issueState">
          <option>버그</option>
          <option>수정중</option>
          <option>완료</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import moment from "moment"

export default {
  data() {
    return {
    }
  },
  mounted() {
  },
  methods : {
    ...mapMutations({
      addIssueData : 'git/addIssueData',
      setInsertedContent : 'git/setInsertedContent',
      getFileList : 'git/getFileList',
    }),
    useAxiosGetData(){
      let issueText = document.querySelector('.issueText').value;
      // let issueTitle = document.querySelector('.issueTitle').value;
      
      if( issueText === '' || issueText === null) return

      const url = '/gitAndIssue/insert'

      this.axios.post( url, null, {
        params: {
          issueIdx: null,
          "project.prjctIdx": 1,
          "member.memIdx" : 1,
          issueCn: issueText,
          issueDate: moment().format('YYYY-MM-DD HH:mm:ss'),
          issueState: '버그'
        }
      })
      .then( (r)=>{
        console.log(r.data)
        this.$store.state.git.realIssue.unshift(r.data)
        this.getFileList(),
        document.querySelector('.issueText').value = ''
        }
      )
      .catch()
    },
  },
}
</script>

<style scoped>
.addIssue{
  height: 7vh;
  background-color: #2C2F3B;
  display: flex;
  justify-content: space-between;
  color : #eee;
}
.addIssue > input:nth-child(1),
.addIssue > input:nth-child(2){
  background: none;
  border: none;
  color: #eee;
  font-size: 18px;
}
.addIssue > input:nth-child(2){
  width: 100%
}
.addIssue > input:nth-child(3){
  background-color: chocolate;
  border-radius: 15px;
  width: 5vw;
  height: 40px;
  color: #eee;
  margin-top: 18px;
  outline: none;
  border: none;
  cursor: pointer;
}
.Issues{
  height: 55vh;
  overflow: scroll;
}
.Issues::-webkit-scrollbar {
  display: none;
}
.getIssue{
  background-color: #2C2F3B;
  height: 7vh;
  overflow: hidden;
  color : #eee;
  margin-top: 5px;
  box-shadow: 5px 5px 5px 5px white;
}
</style>