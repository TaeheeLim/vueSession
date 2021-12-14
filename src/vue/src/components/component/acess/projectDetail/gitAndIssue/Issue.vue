<template>
<div class="issueDiv">
    <h1>Issue</h1>
    <div class="addIssue">
        <input type="text" v-model="this.$store.state.git.selectedFileName" readonly size="8"> 
        <input type="text" v-model="this.$store.state.git.origin.content" size="30">
        <input type="button"  @click="addIssueData" value="등록">
    </div>
    <hr>
    <div class="Issues">
        <div class="getIssue" v-for="a in $store.state.git.issues" :key="a">
            {{a.fileName}} &nbsp; {{a.content}} <br>
            {{a.nickname}}&nbsp;{{a.startDate}}&nbsp;{{a.currentTime}}
            <select v-model="a.state">
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

export default {
    data() {
        return {
        }
    },
    methods : {
        ...mapMutations({
            addIssueData : 'git/addIssueData',
        })
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
}
</style>